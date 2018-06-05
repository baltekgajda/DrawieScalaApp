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
    val mainMenu = MainMenuView(800, 600)
    val menuController = MenuController(mainMenu)
    scene = menuController.view
    val model = Model(null, null)
    //model.joinRoom("https://drawie.herokuapp.com/?room=3332b08b-a9f5-4c40-941c-9ea26a3c5ef1");
  }
}