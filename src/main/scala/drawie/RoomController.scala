package drawie

import drawie.Start.startApp
import scalafx.Includes._
import scalafx.scene.input.MouseEvent

/**
  * Controller of the roomView
  * Sets handlers from the model to the room view controls
  *
  * @param view view room to be controlled
  */
case class RoomController(view: RoomView) {

  /**
    * Going back to main menu
    */
  view.mainMenuButton.onMouseClicked = (e: MouseEvent) => {
    val mainMenuView = MainMenuView(800, 600)
    val mainMenuController = MainMenuController(mainMenuView)
    startApp.stage.scene = mainMenuController.view
  }

  /**
    * Copying URL to clipboard
    */
  view.copyURLButton.onMouseClicked = (e: MouseEvent) => {
    Model.copyURLToClipboard()
  }

  /**
    * Handling undo click
    */
  view.undoButton.onMouseClicked = (e: MouseEvent) => {
    Model.handleUndoClick()
  }

  /**
    * Handling redo click
    */
  view.redoButton.onMouseClicked = (e: MouseEvent) => {
    Model.handleRedoClick()
  }

  /**
    * Handling pressing the mouse on canvas
    */
  view.roomCanvas.onMousePressed = (e: MouseEvent) => {
    Model.manageOnMousePressed(e.getX.toInt, e.getY.toInt, view.isBucketFillButtonPressed, view.getColorFromColorPicker)
  }

  /**
    * Handling dragging the mouse on canvas
    */
  view.roomCanvas.onMouseDragged = (e: MouseEvent) => {
    Model.manageOnMouseDragged(e.getX.toInt, e.getY.toInt, view.isBucketFillButtonPressed)
  }

  /**
    * Handling releasing the mouse from canvas
    */
  view.roomCanvas.onMouseReleased = (e: MouseEvent) => {
    Model.manageOnMouseReleased(view.isBucketFillButtonPressed)
  }
}


