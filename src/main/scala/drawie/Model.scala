package drawie

import java.io.{ByteArrayInputStream, IOException}
import java.net.URISyntaxException
import java.util.UUID

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import org.json.{JSONArray, JSONException, JSONObject}
import sun.misc.BASE64Decoder

import scala.collection.mutable.ListBuffer


object Model {
  val hostURL = "https://drawie.herokuapp.com/"

  var socket: Socket = _

  var roomUrl:String = _

  def joinRoom(url: String): Boolean = {
    if (url.length == 0) return false
    try {
      socket = IO.socket(url)
    }
    catch {
      case e:URISyntaxException => return false
      case e:RuntimeException => return false
    }
    configureSocket();
    socket.connect()
    true
  }

  def newRoom(): Boolean = {
    joinRoom(hostURL + "?room=" + generateRandomUUID())
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
      //TODO przekazanie new Image(inputStream) do controllera
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
      println(strokeToDraw);
      // TODO drawStroke roomController.drawStrokeBCOnCanvas(color, lineCap, fillStyle, lineWidth, stroke);
    } catch {
      case je: JSONException => je.printStackTrace();
    }

  }
}
