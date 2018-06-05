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
    height = 600
    width = 800
    resizable = false
    val mainMenu = MainMenu(height.getValue, width.getValue)
    val menuController = MenuController(mainMenu)
    scene = menuController.view
  }
}