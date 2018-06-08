package drawie

import javafx.collections.FXCollections
import javafx.embed.swing.JFXPanel
import org.scalatest.{BeforeAndAfter, FlatSpec}

class MainMenuViewTest extends  FlatSpec {

    val jfxPanel = new JFXPanel
    val mainMenuView = MainMenuView(200,200)


  "newRoomButton" should "be initialized properly" in {
    assert(mainMenuView.newRoomButton.getLayoutX == 344.0)
    assert(mainMenuView.newRoomButton.getLayoutY == 300.0)
    assert(mainMenuView.newRoomButton.getPrefWidth == 108.0)
    assert(mainMenuView.newRoomButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }


}

