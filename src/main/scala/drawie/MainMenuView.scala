package drawie

import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text

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

  def changeURLTextFieldPromptText(text: String): Unit = {
    urlTextField.clear()
    urlTextField.promptText = text
  }

  def getURLTextFieldText: String = urlTextField.text.value
}

object MainMenuView {
  def createButton(bLayoutX: Double, bLayoutY: Double, bWidth: Double, text: String): Button = new Button(text) {
    this.layoutX = bLayoutX
    this.layoutY = bLayoutY
    this.prefWidth = bWidth
    this.styleClass = List("custom-button")
  }
}

