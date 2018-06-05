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
    val mainMenu = RoomView(800, 600)
    //val menuController = MenuController(mainMenu)
    /*scene = menuController.view*/
    scene = mainMenu
  }
}