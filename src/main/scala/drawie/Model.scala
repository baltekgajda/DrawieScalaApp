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

object Model {

  //TODO moze jednak daloby sie cos zrobic z varami? i czy moze private?
  var roomUrl: String = _
  var roomView: RoomView = _
  private val HOST_URL = "https://drawie.herokuapp.com/"
  private var socket: Socket = _
  private var mStroke: ListBuffer[List[Int]] = ListBuffer() //TODO moze kolekcja co mozna zwiekszac ale val?

  def newRoom(): Boolean = {
    joinRoom(HOST_URL + "?room=" + generateRandomUUID())
  }

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

  def handleRedoClick(): Unit = {
    socket.emit("redo")
  }

  def handleUndoClick(): Unit = {
    socket.emit("undo")
  }

  def manageOnMousePressed(x: Int, y: Int, fillSelected: Boolean, color: Color): Unit = {
    if (fillSelected)
      bucketFill(x, y, color)
    else {
      mStroke = ListBuffer()
      mStroke += List[Int](x, y)
      roomView.beginUserStroke(x, y)
    }
  }

  def manageOnMouseDragged(x: Int, y: Int, fillSelected: Boolean): Unit = {
    if (!fillSelected) {
      mStroke += List[Int](x, y)
      roomView.drawUserStroke(x, y)
    }
  }

  def manageOnMouseReleased(fillSelected: Boolean): Unit = {
    if (!fillSelected)
      sendStroke(mStroke.toList)
  }

  def copyURLToClipboard(): Unit = {
    val selection = new StringSelection(roomUrl)
    val clipboard = Toolkit.getDefaultToolkit.getSystemClipboard
    clipboard.setContents(selection, selection)
  }

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

  private def manageReceivedDumpBC(dump: JSONObject): Unit = try {
    val imgInB64 = dump.getString("snapshot").split(",") //todo CZY TO ponizej jest tez scaolowe?
    val inputStream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(imgInB64(imgInB64.length - 1)))
    roomView.drawDump(new Image(inputStream))
    roomView.endLoading()
  } catch { // TODO co z tym? cos trzeba dodac czy nie more specific cases first ! bo printfy to tak średnio chyba
    case ioe: IOException => ioe.printStackTrace()
    case e: Exception => e.printStackTrace() //TODO nie wystarczy jeden do obsluzenia tego
    //plus czy musimy w ogole to tryowac?
  }

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

  private def generateRandomUUID(): String = {
    UUID.randomUUID().toString //TODO co ze scalowa wersja?
  }

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

  //TODO problemy z #000000 bo nie wypelnia kubełek
  private def hexColorToHashFormat(color: Color): String = "#" + color.toString.substring(7, 13)

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
