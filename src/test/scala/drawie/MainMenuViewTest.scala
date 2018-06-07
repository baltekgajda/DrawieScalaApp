package drawie

import javafx.collections.FXCollections
import org.scalatest.FlatSpec

class MainMenuViewTest extends  FlatSpec {

  def newRoomButtonTest(): Unit = {
    val mainMenuView = MainMenuView(200,200)
    assert(mainMenuView.newRoomButton.getLayoutX == 344.0)
    assert(mainMenuView.newRoomButton.getLayoutY == 300.0)
    assert(mainMenuView.newRoomButton.getPrefWidth == 108.0)
    assert(mainMenuView.newRoomButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }
}

