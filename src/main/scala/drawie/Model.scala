package drawie

import java.io.{ByteArrayInputStream, IOException}
import java.util.UUID

import io.socket.client.{IO, Socket}
import io.socket.emitter.Emitter
import org.json.{JSONException, JSONObject}
import sun.misc.BASE64Decoder


case class Model(var socket: Socket) {
  val hostURL = "https://drawie.herokuapp.com/"


  def joinRoom(url: String): Unit = {
    socket = IO.socket(url)
    configureSocket();
    socket.connect()
  }

  def newRoom(): Unit = {
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
  }


  def manageReceivedDumpBC(dump: JSONObject): Unit = {
    println(dump)
    val imgInB64 = dump.getString("snapshot").split(",");
    val inputStream = new ByteArrayInputStream(new BASE64Decoder().decodeBuffer(imgInB64(imgInB64.length - 1)));
   // gc.drawImage(new Image(inputStream), 0,0) draw image
  }

  def manageReceivedStrokeBC(stroke: JSONObject): Unit = {

  }
}
