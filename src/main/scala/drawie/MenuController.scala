package drawie

import drawie.Start.startApp
import scalafx.Includes._
import scalafx.scene.input.MouseEvent

case class MenuController(view: MainMenuView) {

  view.newRoomButton.onMouseClicked = (e: MouseEvent) => {
    val roomView = RoomView(800, 600) //TODO dodac te wielkosci gdzies
    val roomController = RoomController(roomView)
    startApp.stage.scene = roomController.view
    Model.roomView = roomView
    Model.newRoom()
  }

  view.joinRoomButton.onMouseClicked = (e: MouseEvent) => {
    if (Model.joinRoom(view.urlTextField.text.value)) {
      val roomView = RoomView(800, 600) //TODO dodac te wielkosci gdzies
      Model.roomView = roomView
      val roomController = RoomController(roomView)
      startApp.stage.scene = roomController.view
    }
    else {
      view.urlTextField.clear()           //TODO a moze to w view?
      view.urlTextField.promptText = "wrong URL"
    }
  }

  view.exitButton.onMouseClicked = (e: MouseEvent) => {
    System.exit(0)
  }
}
