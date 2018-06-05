package drawie

import drawie.Start.startApp
import scalafx.Includes._
import scalafx.scene.input.MouseEvent

case class RoomController(view: RoomView) {

  view.mainMenuButton.onMouseClicked = (e: MouseEvent) => {
    val mainMenuView = MainMenuView(800, 600)
    val menuController = MenuController(mainMenuView) //TODO menuController na mainMenu controller
    startApp.stage.scene = menuController.view
  }

  view.copyURLButton.onMouseClicked = (e: MouseEvent) => {
    println("Copy URL Button")
  }

  view.undoButton.onMouseClicked = (e: MouseEvent) => {
    Model.handleUndoClick()
  }

  view.redoButton.onMouseClicked = (e: MouseEvent) => {
    Model.handleRedoClick()
  }

  view.roomCanvas.onMousePressed = (e: MouseEvent) => {
    println("Canvas Pressed")
    //TODO zla wartosc w tym colorPickerze
    Model.manageOnMousePressed(view.bucketFillToggleButton.selected.value, e.sceneX.toInt, e.sceneY.toInt, view.colorPicker.value.value)
  }

  view.roomCanvas.onMouseDragged = (e: MouseEvent) => {
    println("Canvas Dragged")
    Model.manageOnMouseDragged(view.bucketFillToggleButton.selected.value, e.sceneX.toInt, e.sceneY.toInt)
  }

  view.roomCanvas.onMouseReleased = (e: MouseEvent) => {
    println("Canvas Released")
    //TODO
    Model.manageOnMouseReleased(view.bucketFillToggleButton.selected.value)
  }
}


