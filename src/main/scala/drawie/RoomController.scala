package drawie

import drawie.Start.startApp
import scalafx.Includes._
import scalafx.scene.input.MouseEvent

/**
  * Controller of the roomView
  * Sets handlers from the model to the room view controls
  * @param view view room to be controlled
  */
case class RoomController(view: RoomView) {
  view.mainMenuButton.onMouseClicked = (e: MouseEvent) => {
    val mainMenuView = MainMenuView(800, 600)
    val mainMenuController = MainMenuController(mainMenuView)
    startApp.stage.scene = mainMenuController.view
  }

  view.copyURLButton.onMouseClicked = (e: MouseEvent) => {
    Model.copyURLToClipboard()
  }

  view.undoButton.onMouseClicked = (e: MouseEvent) => {
    Model.handleUndoClick()
  }

  view.redoButton.onMouseClicked = (e: MouseEvent) => {
    Model.handleRedoClick()
  }

  view.roomCanvas.onMousePressed = (e: MouseEvent) => {
    Model.manageOnMousePressed(e.getX.toInt, e.getY.toInt, view.isBucketFillButtonPressed, view.getColorFromColorPicker)
  }

  view.roomCanvas.onMouseDragged = (e: MouseEvent) => {
    Model.manageOnMouseDragged(e.getX.toInt, e.getY.toInt, view.isBucketFillButtonPressed)
  }

  view.roomCanvas.onMouseReleased = (e: MouseEvent) => {
    Model.manageOnMouseReleased(view.isBucketFillButtonPressed)
  }
}


