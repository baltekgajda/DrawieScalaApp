package drawie

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.image.Image

object Start extends JFXApp {
  def startApp: JFXApp = this
  stage = new PrimaryStage {
    title = "Drawie"
    icons.add(new Image(getClass.getResourceAsStream("/images/DrawieIcon.bmp")))
    centerOnScreen()
    sizeToScene()
    resizable = false
    val mainMenuView = MainMenuView(800, 600)
    val menuController = MenuController(mainMenuView)
    scene = menuController.view

    // to moje takie notatki ponizej tej linii mozna usunac
    /*val roomView = RoomView(800, 600)
    val roomController = RoomController(roomView)
    scene = roomController.view
*/
    //model.joinRoom("https://drawie.herokuapp.com/?room=3332b08b-a9f5-4c40-941c-9ea26a3c5ef1");
  }
}