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
    println("Undo Button")
  }

  view.redoButton.onMouseClicked = (e: MouseEvent) => {
    println("Redo Button")
  }

  view.roomCanvas.onMousePressed = (e: MouseEvent) => {
    println("Canvas Pressed")
  }

  view.roomCanvas.onMouseDragged = (e: MouseEvent) => {
    println("Canvas Dragged")
  }

  view.roomCanvas.onMouseReleased = (e: MouseEvent) => {
    println("Canvas Released")
  }
}


