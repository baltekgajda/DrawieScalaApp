package drawie

import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.io.{ByteArrayInputStream, IOException}
import java.net.URISyntaxException
import java.util.UUID

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import org.json.{JSONArray, JSONException, JSONObject}
import scalafx.scene.image.Image
import scalafx.scene.paint.Color
import sun.misc.BASE64Decoder

import scala.collection.mutable.ListBuffer

/**
  * Model of the application
  */
object Model {


  /**
    * URL of the created room, generated if new room is created
    */
  var roomUrl: String = _

  /**
    * View which is displayed at the moment
    */
  var roomView: RoomView = _

  /**
    * Yrl of the "Drawie host
    */
  private val HOST_URL = "https://drawie.herokuapp.com/"

  /**
    * Socket connected to the application at the moment
    */
var socket: Socket = _

  /**
    * List of points that create a stroke
    */
  private var mStroke: ListBuffer[List[Int]] = ListBuffer()

  /**
    * Creating new room id and joining the room
    *
    * @return true if joined the room, false otherwise
    */
  def newRoom(): Boolean = {
    joinRoom(HOST_URL + "?room=" + generateRandomUUID())
  }

  /**
    * Joining the room with given url
    *
    * @param url url of the room
    * @return true if joined the room, false otherwise
    */
  def joinRoom(url: String): Boolean = {
    if (url.length != 0) {
      try
        socket = IO.socket(url)
      catch {
        case _: URISyntaxException | _: RuntimeException => return false
      }
      configureSocket()
      socket.connect()
      roomUrl = url
      true
    }
    else
      false
  }

  /**
    * Handling redo click and emitting event
    */
  def handleRedoClick(): Unit = {
    socket.emit("redo")
  }

  /**
    * Handling undo click and emitting event
    */
  def handleUndoClick(): Unit = {
    socket.emit("undo")
  }

  /**
    * Managing mouse pressed on the canvas
    *
    * @param x            x coordinate where the event of drawing was created
    * @param y            y coordinate where the event of drawing was created
    * @param fillSelected boolean if the fill is selected
    * @param color        color chosen by user
    */
  def manageOnMousePressed(x: Int, y: Int, fillSelected: Boolean, color: Color): Unit = {
    if (fillSelected)
      bucketFill(x, y, color)
    else {
      mStroke = ListBuffer()
      mStroke += List[Int](x, y)
      roomView.beginUserStroke(x, y)
    }
  }

  /**
    * Managing logic of the mouse dragged on canvas event
    *
    * @param x            x of the event
    * @param y            y of the event
    * @param fillSelected is the fill button selected
    */
  def manageOnMouseDragged(x: Int, y: Int, fillSelected: Boolean): Unit = {
    if (!fillSelected) {
      mStroke += List[Int](x, y)
      roomView.drawUserStroke(x, y)
    }
  }

  /**
    * Managing logic of the mouse released on canvas event
    *
    * @param fillSelected is the fill button selected
    */
  def manageOnMouseReleased(fillSelected: Boolean): Unit = {
    if (!fillSelected)
      sendStroke(mStroke.toList)
  }

  /**
    * Copying url to clipboard
    */
  def copyURLToClipboard(): Unit = {
    val selection = new StringSelection(roomUrl)
    val clipboard = Toolkit.getDefaultToolkit.getSystemClipboard
    clipboard.setContents(selection, selection)
  }

  /**
    * Configuring socket for receiving events
    */
  private def configureSocket(): Unit = {
    val dumpListener: Emitter.Listener = (args: Array[AnyRef]) => {
      val dump: JSONObject = args.head.asInstanceOf[JSONObject]
      manageReceivedDumpBC(dump)
    }

    val strokeListener: Emitter.Listener = (args: Array[AnyRef]) => {
      val stroke = args.head.asInstanceOf[JSONObject]
      manageReceivedStrokeBC(stroke)
    }

    socket.on("dumpBC", dumpListener)
    socket.on("strokeBC", strokeListener)
  }

  /**
    * Managing received dump on socket
    *
    * @param dump dump received by socket
    */
  private def manageReceivedDumpBC(dump: JSONObject): Unit = try {
    val imgInB64 = dump.getString("snapshot").split(",")
    val inputStream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(imgInB64(imgInB64.length - 1)))
    roomView.drawDump(new Image(inputStream))
    roomView.endLoading()
  } catch {
    case ioe: IOException => ioe.printStackTrace()
    case e: Exception => e.printStackTrace()
    //plus czy musimy w ogole to tryowac?
  }

  /**
    * Managing stroke received by socket
    *
    * @param stroke stroke received by socket
    */
  private def manageReceivedStrokeBC(stroke: JSONObject): Unit = try {
    val opt = stroke.getJSONObject("options")
    val color = opt.getString("strokeStyle")
    val lineCap = opt.getString("lineCap")
    val fillStyle = opt.getString("fillStyle")
    val lineWidth = opt.getInt("lineWidth")
    val jsonStroke: JSONArray = stroke.getJSONArray("stroke")
    val strokeToDraw: List[Int] = (for (i <- 0 until jsonStroke.length()) yield {
      List(jsonStroke.getJSONArray(i).getInt(0),
        jsonStroke.getJSONArray(i).getInt(1))
    }).flatten.toList

    roomView.drawStrokeOnCanvas(color, lineCap, fillStyle, lineWidth, strokeToDraw);
  } catch {
    case je: JSONException => je.printStackTrace();
  }

  /**
    * Generating random user id for the new room
    *
    * @return random user id
    */
  private def generateRandomUUID(): String = {
    UUID.randomUUID().toString //TODO co ze scalowa wersja?
  }

  /**
    * Sending user's stroke to the server
    *
    * @param mStroke user's stroke points
    */
  private def sendStroke(mStroke: List[List[Int]]): Unit = {
    val color = hexColorToHashFormat(roomView.getColorFromColorPicker)
    val lineCap = "round"
    val fillStyle = "solid"
    val lineWidth = roomView.getPaintbrushWidth
    val strokeObj = new JSONObject
    val options = new JSONObject
    val stroke = new JSONArray
    try {
      mStroke.foreach(points => stroke.put(new JSONArray().put(points.head).put(points(1))))
      options.put("strokeStyle", color)
      options.put("lineCap", lineCap)
      options.put("fillStyle", fillStyle)
      options.put("lineWidth", lineWidth)
      strokeObj.put("options", options)
      strokeObj.put("stroke", stroke)
    } catch {
      case e: JSONException =>
        e.printStackTrace()
    }
    socket.emit("stroke", strokeObj)
  }

  /**
    * Changing format of the color from Color class to hex
    *
    * @param color Color in Color class format
    * @return Color in hex format
    */
  private def hexColorToHashFormat(color: Color): String = {
    val colorToReturn = "#" + color.toString.substring(7, 13)
    if (colorToReturn == "#000000") "#050505"
    else colorToReturn
  }

  /**
    * Sending flood fill event to the server
    *
    * @param x     x coordinate where the mouse was pressed
    * @param y     y coordinate where the mouse was pressed
    * @param color color chosen by user
    */
  private def bucketFill(x: Int, y: Int, color: Color): Unit = {
    val floodFillObj = new JSONObject
    try {
      floodFillObj.put("x", x)
      floodFillObj.put("y", y)
      floodFillObj.put("color", hexColorToHashFormat(color))
    } catch {
      case e: JSONException =>
        e.printStackTrace()
        return
    }
    socket.emit("floodFill", floodFillObj)
  }
}
