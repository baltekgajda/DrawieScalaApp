package drawie

import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.image.Image

/**
  * Class for starting drawie application
  */
object Start extends JFXApp {

  /**
    * starting app
    * @return application that is started
    */
  def startApp: JFXApp = this

  stage = new PrimaryStage {
    title = "Drawie"
    icons.add(new Image(getClass.getResourceAsStream("/images/DrawieIcon.bmp")))
    centerOnScreen()
    sizeToScene()
    resizable = false
    val mainMenuView = MainMenuView(800, 600)
    val mainMenuController = MainMenuController(mainMenuView)
    scene = mainMenuController.view
  }
}