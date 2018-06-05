package drawie

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
  val hostURL = "https://drawie.herokuapp.com/"

  var socket: Socket = _

  var roomUrl: String = _

  var roomView: RoomView = _

  def newRoom(): Boolean = {
    joinRoom(hostURL + "?room=" + generateRandomUUID())
  }

  var mStroke: ListBuffer[List[Int]] = ListBuffer()

  def joinRoom(url: String): Boolean = {
    if (url.length == 0) return false
    try {
      socket = IO.socket(url)
    }
    catch {
      case e: URISyntaxException => return false
      case e: RuntimeException => return false
    }
    configureSocket();
    socket.connect()

    roomUrl = url;
    true
  }

  def generateRandomUUID(): String = {
    UUID.randomUUID().toString
  }

  def configureSocket(): Unit = {
    val dumpListener: Emitter.Listener = new Emitter.Listener {
      override def call(args: AnyRef*): Unit = {
        val dump = args(0).asInstanceOf[JSONObject]
        manageReceivedDumpBC(dump)
      }
    }

    val strokeListener: Emitter.Listener = new Emitter.Listener {
      override def call(args: AnyRef*): Unit = {
        val stroke = args(0).asInstanceOf[JSONObject]
        manageReceivedStrokeBC(stroke)
      }
    }

    socket.on("dumpBC", dumpListener)
    socket.on("strokeBC", strokeListener)
  }

  def manageReceivedDumpBC(dump: JSONObject): Unit = {
    println(dump)
    try {
      val imgInB64 = dump.getString("snapshot").split(",");
      val inputStream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(imgInB64(imgInB64.length - 1)));
      roomView.loadingStackPane.visible = false
      roomView.drawDump(new Image(inputStream))
    } catch {
      case ioe: IOException => println(ioe) // more specific cases first !
      case e: Exception => println(e)
    }

  }

  def manageReceivedStrokeBC(stroke: JSONObject): Unit = {
    try {
      val opt = stroke.getJSONObject("options");
      val color = opt.getString("strokeStyle");
      val lineCap = opt.getString("lineCap");
      val fillStyle = opt.getString("fillStyle");
      val lineWidth = opt.getInt("lineWidth");
      val jsonStroke: JSONArray = stroke.getJSONArray("stroke");

      var strToDraw = new ListBuffer[Int]()
      for (i <- 0 until jsonStroke.length()) {
        strToDraw += jsonStroke.getJSONArray(i).getInt(0)
        strToDraw += jsonStroke.getJSONArray(i).getInt(1)
      }
      val strokeToDraw = strToDraw.toList;
      roomView.drawStrokeOnCanvas(color, lineCap, fillStyle, lineWidth, strokeToDraw);
    } catch {
      case je: JSONException => je.printStackTrace();
    }

  }


  private def hexColorToHashFormat(color: Color) = "#" + color.toString.substring(2, 8)

  def bucketFill(x: Int, y: Int, color: Color): Unit = {
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


  def manageOnMousePressed(fillSelected: Boolean, x: Int, y: Int, color: Color): Unit = {
    if (fillSelected) {
      bucketFill(x, y, color)
      return
    }
    mStroke = ListBuffer()
    mStroke += List[Int](x, y)
    roomView.beginUserStroke(x, y)
  }

  def manageOnMouseDragged(fillSelected: Boolean, x: Int, y: Int): Unit = {
    if (fillSelected) return
    mStroke += List[Int](x, y)
    roomView.drawUserStroke(x, y)
  }

  def manageOnMouseReleased(fillSelected: Boolean): Unit = {
    if (fillSelected) return
    sendStroke(mStroke.toList)
  }

  def handleRedoClick(): Unit = {
    socket.emit("redo")
  }

  def handleUndoClick(): Unit = {
    socket.emit("undo")
  }

  def sendStroke(mStroke: List[List[Int]]): Unit = {
    //TODO tu bedzie do poprawki
    //val color = roomView.colorPicker.value.value
    val color = "#00ffff"
    val lineCap = "round"
    val fillStyle = "solid"
    val lineWidth = roomView.paintbrushWidthSlider.value.value
    val strokeObj = new JSONObject
    val options = new JSONObject
    val stroke = new JSONArray
    println(mStroke)
    try {
      mStroke.foreach(points => stroke.put(new JSONArray().put(points(0)).put(points(1))))
      options.put("strokeStyle", color)
        //hexColorToHashFormat(color)) TODO
      options.put("lineCap", lineCap)
      options.put("fillStyle", fillStyle)
      options.put("lineWidth", lineWidth)
      strokeObj.put("options", options)
      strokeObj.put("stroke", stroke)
    } catch {
      case e: JSONException =>
        e.printStackTrace()
    }
    println(strokeObj)
    socket.emit("stroke", strokeObj)
  }
}
