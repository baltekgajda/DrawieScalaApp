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

  "urlTextField" should "be initialized properly" in {
    assert(mainMenuView.urlTextField.getLayoutX == 216.0)
    assert(mainMenuView.urlTextField.getLayoutY == 374.0)
    assert(mainMenuView.urlTextField.getPrefWidth == 365.0)
    assert(mainMenuView.urlTextField.getPrefHeight == 25.0)
    assert(mainMenuView.urlTextField.getPromptText == "type url of existing drawie room")
    assert(mainMenuView.urlTextField.getStyleClass == FXCollections.observableArrayList("text-field"))
  }

  "titleText" should "be initialized properly" in {
    assert(mainMenuView.titleText.getLayoutX == 257.0)
    assert(mainMenuView.titleText.getLayoutY == 250.0)
    assert(mainMenuView.titleText.getText == "DRAWIE")
    assert(mainMenuView.titleText.getStyleClass == FXCollections.observableArrayList("drawie-title"))
  }

  "scenePane" should "be initialized properly" in {
    assert(mainMenuView.scenePane.getPrefHeight == mainMenuView.sceneHeight)
    assert(mainMenuView.scenePane.getPrefWidth == mainMenuView.sceneWidth)
    assert(mainMenuView.scenePane.getStyleClass == FXCollections.observableArrayList("menu-background"))
    assert(mainMenuView.scenePane.children.size() == 5)
  }

  "changeURLTextFieldPromptText" should "change prompt text to given" in {
    val text = "TEST"
    mainMenuView.changeURLTextFieldPromptText(text)
    assert(mainMenuView.urlTextField.getPromptText == text)
  }

  "getURLTextFieldText" should "return typed text" in {
    val text = "typed text"
    mainMenuView.urlTextField.text = text
    assert(mainMenuView.getURLTextFieldText == text)
  }
}

