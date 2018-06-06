package drawie

import drawie.Start.startApp
import scalafx.Includes._
import scalafx.scene.input.MouseEvent

/**
  * Controller for the main menu view
  * Sets handlers for main menu controlls
  * @param view main menu view to be controlled
  */
case class MainMenuController(view: MainMenuView) {

  view.newRoomButton.onMouseClicked = (e: MouseEvent) => {
    val roomView = RoomView(800, 600) //TODO dodac te wielkosci gdzies
    val roomController = RoomController(roomView)
    startApp.stage.scene = roomController.view
    Model.roomView = roomView
    Model.newRoom()
  }

  view.joinRoomButton.onMouseClicked = (e: MouseEvent) => {
    if (Model.joinRoom(view.getURLTextFieldText)) {
      val roomView = RoomView(800, 600) //TODO dodac te wielkosci gdzies
      val roomController = RoomController(roomView)
      startApp.stage.scene = roomController.view
      Model.roomView = roomView
    }
    else {
      view.changeURLTextFieldPromptText("wrong URL")
    }
  }

  view.exitButton.onMouseClicked = (e: MouseEvent) => {
    System.exit(0)
  }
}
