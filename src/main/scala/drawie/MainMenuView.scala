package drawie

import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text

/**
  * Main menu view class with main menu
  * @param sceneWidth width of the scene
  * @param sceneHeight height of the scene
  */
case class MainMenuView(sceneWidth: Double, sceneHeight: Double) extends Scene(sceneWidth, sceneHeight) {

  import MainMenuView._

  val newRoomButton: Button = createButton(344.0, 300.0, 108.0, "New Room")
  val joinRoomButton: Button = createButton(344.0, 337.0, 108.0, "Join Room")
  val exitButton: Button = createButton(344.0, 410.0, 108.0, "Exit")

  private val urlTextField: TextField = new TextField {
    this.layoutX = 216.0
    this.layoutY = 374.0
    this.prefHeight = 25.0
    this.prefWidth = 365.0
    this.promptText = "type url of existing drawie room"
    this.styleClass = List("text-field")
  }

  private val titleText: Text = new Text {
    this.layoutX = 257.0
    this.layoutY = 250.0
    this.text = "DRAWIE"
    this.styleClass = List("drawie-title")
  }

  private val scenePane: Pane = new Pane {
    this.prefHeight = sceneHeight
    this.prefWidth = sceneWidth
    this.styleClass = List("menu-background")
    this.children = List(titleText, newRoomButton, joinRoomButton, exitButton, urlTextField)
  }

  stylesheets = List(getClass.getClassLoader.getResource("styles.css").toExternalForm)
  content = scenePane

  /**
    * Changing text field promt text to the text
    * @param text text to be displayed
    */
  def changeURLTextFieldPromptText(text: String): Unit = {
    urlTextField.clear()
    urlTextField.promptText = text
  }

  /**
    *Getting text from the text field
    * @return text on the url field
    */
  def getURLTextFieldText: String = urlTextField.getText
}

/**
  * Companion object for to MainMenu class for implementing static function
  */
object MainMenuView {
  /**
    * Creating button
    * @param bLayoutX x of the button
    * @param bLayoutY y of the button
    * @param bWidth width of the button
    * @param text text on the button
    * @return button created
    */
  def createButton(bLayoutX: Double, bLayoutY: Double, bWidth: Double, text: String): Button = new Button(text) {
    this.layoutX = bLayoutX
    this.layoutY = bLayoutY
    this.prefWidth = bWidth
    this.styleClass = List("custom-button")
  }
}

