package drawie

import javafx.collections.FXCollections
import javafx.embed.swing.JFXPanel
import org.scalatest.FlatSpec

class RoomViewTest extends FlatSpec{

  val jfxPanel = new JFXPanel
  val roomView = RoomView(200,200)


  "roomViewCanvas" should "be initialized properly" in {
    assert(roomView.roomCanvas.getWidth == 500.0)
    assert(roomView.roomCanvas.getHeight == 500.0)

  }

}
