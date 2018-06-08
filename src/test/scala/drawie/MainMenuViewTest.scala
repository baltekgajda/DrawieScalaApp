package drawie

import javafx.collections.FXCollections
import javafx.embed.swing.JFXPanel
import org.scalatest.FlatSpec

class MainMenuViewTest extends  FlatSpec {

  "Test" should "do thsnck" in {

    val jfxPanel = new JFXPanel
    val mainMenuView = MainMenuView(200,200)
    assert(mainMenuView.newRoomButton.getLayoutX == 344.0)
    assert(mainMenuView.newRoomButton.getLayoutY == 300.0)
    assert(mainMenuView.newRoomButton.getPrefWidth == 108.0)
    assert(mainMenuView.newRoomButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }
}

