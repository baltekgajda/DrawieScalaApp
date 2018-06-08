package drawie

import javafx.embed.swing.JFXPanel
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec
import org.scalatest.easymock.EasyMockSugar.MockObjects

class MainMenuControllerTest extends FlatSpec with MockFactory{

  val jfxPanel = new JFXPanel
  val mainMenuView = MainMenuView(200,200)

  val roomController = MainMenuController(mainMenuView);


  "mainMenuController" should "initialize joinRoomButton listener properly" in {
    assert(mainMenuView.joinRoomButton.onMouseClicked.isNotNull.getValue == true)
  }

  "mainMenuController" should "initialize newRoomButton listener properly" in {
    assert(mainMenuView.newRoomButton.onMouseClicked.isNotNull.getValue == true)
  }

  "mainMenuController" should "initialize exitButton listener properly" in {
    assert(mainMenuView.exitButton.onMouseClicked.isNotNull.getValue == true)
  }
}
