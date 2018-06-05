package drawie

import drawie.Start.startApp
import scalafx.Includes._
import scalafx.scene.input.MouseEvent

case class MenuController(view: MainMenuView) {

  view.newRoomButton.onMouseClicked = (e: MouseEvent) => {
    val roomView = RoomView(800, 600) //TODO dodac te wielkosci gdzies
    val roomController = RoomController(roomView)
    startApp.stage.scene = roomController.view
  }

  view.joinRoomButton.onMouseClicked = (e: MouseEvent) => {
    println("Join Room Button")
  }

  view.exitButton.onMouseClicked = (e: MouseEvent) => {
    System.exit(0)
  }
}
