package drawie

import javafx.collections.FXCollections
import javafx.embed.swing.JFXPanel
import org.scalatest.{BeforeAndAfter, FlatSpec}

class RoomViewTest extends  FlatSpec with BeforeAndAfter {

  val jfxPanel = new JFXPanel
  val roomView = RoomView(200, 200)

  "undoButton" should "be initialized properly" in {
    assert(roomView.undoButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }

  "redoButton" should "be initialized properly" in {
    assert(roomView.redoButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }

  "mainMenuButton" should "be initialized properly" in {
    assert(roomView.mainMenuButton.getLayoutX == 715.0)
    assert(roomView.mainMenuButton.getLayoutY == 14.0)
    assert(roomView.mainMenuButton.getPrefWidth == 70.0)
    assert(roomView.mainMenuButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }

  "copyURLButton" should "be initialized properly" in {
    assert(roomView.copyURLButton.getLayoutX == 715.0)
    assert(roomView.copyURLButton.getLayoutY == 50.0)
    assert(roomView.copyURLButton.getPrefWidth == 70.0)
    assert(roomView.copyURLButton.getStyleClass == FXCollections.observableArrayList("custom-button"))
  }

  "roomCanvas" should "be initialized properly" in {
    assert(roomView.roomCanvas.getWidth == 500.0)
    assert(roomView.roomCanvas.getHeight == 500.0)
  }

  "serverCanvas" should "be initialized properly" in {
    assert(roomView.serverCanvas.getWidth == 500.0)
    assert(roomView.serverCanvas.getHeight == 500.0)
  }

  "loadingStackPane" should "be initialized properly" in {
    assert(roomView.loadingStackPane.getPrefHeight == 500.0)
    assert(roomView.loadingStackPane.getPrefWidth == 500.0)
    assert(roomView.loadingStackPane.getChildren.size == 1)
    assert(roomView.loadingStackPane.getStyleClass == FXCollections.observableArrayList("loading-pane"))
  }

  "bucketFillToggleButton" should "be initialized properly" in {
    assert(roomView.bucketFillToggleButton.getStyleClass == FXCollections.observableArrayList("toggle-button"))
  }

  "paintbrushWidthSlider" should "be initialized properly" in {
    assert(roomView.paintbrushWidthSlider.getMin == 1.0)
    assert(roomView.paintbrushWidthSlider.getMax == 30.0)
    assert(roomView.paintbrushWidthSlider.getMinHeight == 26.0)
    assert(roomView.paintbrushWidthSlider.getStyleClass == FXCollections.observableArrayList("slider"))
  }
}

