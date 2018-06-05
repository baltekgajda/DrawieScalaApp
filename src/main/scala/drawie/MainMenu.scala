package drawie

import scalafx.scene.Scene
import scalafx.scene.control.{Button, TextField}
import scalafx.scene.layout.Pane
import scalafx.scene.text.Text

case class MainMenu(sceneHeight: Double, sceneWidth: Double) extends Scene {

  import MainMenu._

  private val titleText: Text = new Text {
    this.layoutX = 257.0
    this.layoutY = 250.0
    this.text = "DRAWIE"
    this.styleClass = List("drawie-title")
  }

  private val newRoomButton: Button = createButton(344.0, 300.0, 108.0, "New Room")
  private val joinRoomButton: Button = createButton(344.0, 337.0, 108.0, "Join Room")
  private val exitButton: Button = createButton(344.0, 410.0, 108.0, "Exit")

  private val urlTextField: TextField = new TextField {
    this.layoutX = 216.0
    this.layoutY = 374.0
    this.prefHeight = 25.0
    this.prefWidth = 365.0
    this.promptText = "type url of existing drawie room"
    this.styleClass = List("text-field")
  }
  private val scenePane: Pane = new Pane {
    this.prefHeight = sceneHeight
    this.prefWidth = sceneWidth
    this.styleClass = List("menu-background")
    this.children = List(titleText, newRoomButton, joinRoomButton, exitButton, urlTextField)
  }

  stylesheets = List(getClass.getClassLoader.getResource("styles.css").toExternalForm)
  content = scenePane
}

object MainMenu {
  def createButton(bLayoutX: Double, bLayoutY: Double, bWidth: Double, text: String): Button = new Button(text) {
    this.layoutX = bLayoutX
    this.layoutY = bLayoutY
    this.prefWidth = bWidth
    this.styleClass = List("custom-button")
  }
}

