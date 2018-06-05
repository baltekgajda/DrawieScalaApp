package drawie

import drawie.Start.startApp
import scalafx.Includes._
import scalafx.scene.input.MouseEvent

case class MenuController(view: MainMenuView) {

  view.newRoomButton.onMouseClicked = (e: MouseEvent) => {
    val roomView = RoomView(800, 600) //TODO dodac te wielkosci gdzies
    val roomController = RoomController(roomView)
    startApp.stage.scene = roomController.view
    Model.newRoom()
  }

  view.joinRoomButton.onMouseClicked = (e: MouseEvent) => {
    println("Join Room Button")
    val roomView = RoomView(800, 600) //TODO dodac te wielkosci gdzies
    val roomController = RoomController(roomView)
    startApp.stage.scene = roomController.view
    Model.joinRoom("https://drawie.herokuapp.com/?room=3332b08b-a9f5-4c40-941c-9ea26a3c5ef1") //TODO wez text z
  }

  view.exitButton.onMouseClicked = (e: MouseEvent) => {
    System.exit(0)
  }
}
