package drawie

import javafx.embed.swing.JFXPanel
import org.scalatest.FlatSpec

class RoomControllerTest extends FlatSpec {

  val jfxPanel = new JFXPanel
  val roomView = RoomView(200,200)

  val roomController = RoomController(roomView);


  "roomController" should "initialize mainMenuButton listener properly" in {
    assert(roomView.mainMenuButton.onMouseClicked.isNotNull.getValue == true)
  }

  "roomController" should "initialize copyUrlButton listener properly" in {
    assert(roomView.copyURLButton.onMouseClicked.isNotNull.getValue == true)
  }

  "roomController" should "initialize undoButton listener properly" in {
    assert(roomView.undoButton.onMouseClicked.isNotNull.getValue == true)
  }

  "roomController" should "initialize redoButton listener properly" in {
    assert(roomView.redoButton.onMouseClicked.isNotNull.getValue == true)
  }

  "roomController" should "initialize canvas onMousePressed listener properly" in {
    assert(roomView.roomCanvas.onMousePressed.isNotNull.getValue == true)
  }

  "roomController" should "initialize canvas onMouseDragged listener properly" in {
    assert(roomView.roomCanvas.onMouseDragged.isNotNull.getValue == true)
  }

  "roomController" should "initialize canvas onMouseReleased listener properly" in {
    assert(roomView.roomCanvas.onMouseReleased.isNotNull.getValue == true)
  }
}
