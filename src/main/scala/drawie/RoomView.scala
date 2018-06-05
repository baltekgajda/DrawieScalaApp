package drawie

import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, ColorPicker, Slider, ToggleButton}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{HBox, Pane, StackPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.text.Text
import scalafx.scene.{AccessibleRole, Scene}

case class RoomView(sceneWidth: Double, sceneHeight: Double) extends Scene(sceneWidth, sceneHeight) {

  val paintbrushWidthSlider = new Slider {
    this.min = 1.0
    this.max = 30.0
    this.minHeight = 26.0
    this.styleClass = List("slider")
  }

  val colorPicker = new ColorPicker {
    this.accessibleRole = AccessibleRole.ImageView
    this.value = Color.Black
    this.prefWidth = 20.0
    this.minHeight = 26.0
    this.styleClass = List("color-picker")
  }

  val bucketFillToggleButton = new ToggleButton {
    this.graphic = createImageView(14.0, 14.0, "/images/BucketIcon.png")
    this.styleClass = List("toggle-button")
  }

  val undoButton: Button = new Button {
    this.graphic = createImageView(14.0, 14.0, "/images/UndoIcon.png")
    this.styleClass = List("custom-button")
  }

  val redoButton: Button = new Button {
    this.graphic = createImageView(14.0, 14.0, "/images/RedoIcon.png")
    this.styleClass = List("custom-button")
  }

  val mainMenuButton: Button = MainMenuView.createButton(715.0, 14.0, 70.0, "Menu")
  val copyURLButton: Button = MainMenuView.createButton(715.0, 50.0, 70.0, "Copy URL")
  val roomCanvas: Canvas = new Canvas(500.0, 500.0)

  val loadingStackPane = new StackPane {
    this.prefHeight = 500.0
    this.prefWidth = 500.0
    this.styleClass = List("loading-pane")
    this.children = new Text {
      this.text = "Loading ..."
      this.styleClass = List("loading-text")
    }
  }

  private val serverCanvas: Canvas = new Canvas(500.0, 500.0)

  private val canvasStackPane: StackPane = new StackPane {
    this.prefHeight = 500.0
    this.prefWidth = 500.0
    this.children = List(serverCanvas, roomCanvas, loadingStackPane)
  }

  private val toolsHBox: HBox = new HBox {
    this.prefHeight = 44.0
    this.prefWidth = 500.0
    this.spacing = 15
    this.styleClass = List("tools-HBox")
    this.children = List(undoButton, redoButton, bucketFillToggleButton, colorPicker, paintbrushWidthSlider)
  }

  private val sceneVBox: VBox = new VBox {
    this.layoutX = 162.0
    this.layoutY = 26.0
    this.prefHeight = 548.8
    this.prefWidth = 500.0
    this.styleClass = List("room-VBox")
    this.children = List(toolsHBox, canvasStackPane)
  }

  private val scenePane: Pane = new Pane {
    this.prefHeight = sceneHeight
    this.prefWidth = sceneWidth
    this.styleClass = List("menu-background")
    this.children = List(mainMenuButton, copyURLButton, sceneVBox)
  }

  stylesheets = List(getClass.getClassLoader.getResource("styles.css").toExternalForm)
  content = scenePane

  private def createImageView(iFitWidth: Double, iFitHeight: Double, path: String): ImageView = new ImageView {
    this.fitWidth = iFitWidth
    this.fitHeight = iFitHeight
    this.image = new Image(getClass.getResourceAsStream(path))
  }

  def drawDump(image:Image): Unit ={
    roomCanvas.graphicsContext2D.drawImage(image, 0, 0)
  }
}
