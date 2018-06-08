package drawie

import javafx.collections.FXCollections
import javafx.embed.swing.JFXPanel
import org.scalatest.{BeforeAndAfter, FlatSpec}

class MainMenuViewTest extends  FlatSpec with BeforeAndAfter{

    val jfxPanel = new JFXPanel
    val mainMenuView = MainMenuView(200,200)

  "newRoomButton" should "be initialized properly" in {
    assert(mainMenuView.newRoomButton.getLayoutX == 344.0)
    assert(mainMenuView.newRoomButton.getLayoutY == 300.0)
    assert(mainMenuView.newRoomButton.getPrefWidth == 108.0)
    assert(mainMenuView.newRoomButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }

  "joinRoomButton" should "be initialized properly" in {
    assert(mainMenuView.joinRoomButton.getLayoutX == 344.0)
    assert(mainMenuView.joinRoomButton.getLayoutY == 337.0)
    assert(mainMenuView.joinRoomButton.getPrefWidth == 108.0)
    assert(mainMenuView.joinRoomButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }

  "exitButton" should "be initialized properly" in {
    assert(mainMenuView.exitButton.getLayoutX == 344.0)
    assert(mainMenuView.exitButton.getLayoutY == 410.0)
    assert(mainMenuView.exitButton.getPrefWidth == 108.0)
    assert(mainMenuView.exitButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }


}

