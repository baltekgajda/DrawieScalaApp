package drawie

import scalafx.scene.canvas.Canvas
import scalafx.scene.control.{Button, ColorPicker, Slider, ToggleButton}
import scalafx.scene.image.{Image, ImageView}
import scalafx.scene.layout.{HBox, Pane, StackPane, VBox}
import scalafx.scene.paint.Color
import scalafx.scene.shape.StrokeLineCap
import scalafx.scene.text.Text
import scalafx.scene.{AccessibleRole, Scene}

/**
  * View of the room
  *
  * @param sceneWidth  widht of the room window
  * @param sceneHeight height of the room window
  */
case class RoomView(sceneWidth: Double, sceneHeight: Double) extends Scene(sceneWidth, sceneHeight) {

  /**
    * Button for undo action
    */
  val undoButton: Button = new Button {
    this.graphic = createImageView(14.0, 14.0, "/images/UndoIcon.png")
    this.styleClass = List("custom-button")
  }

  /**
    * Button for redo action
    */
  val redoButton: Button = new Button {
    this.graphic = createImageView(14.0, 14.0, "/images/RedoIcon.png")
    this.styleClass = List("custom-button")
  }

  /**
    * Button to return to main menu
    */
  val mainMenuButton: Button = MainMenuView.createButton(715.0, 14.0, 70.0, "Menu")

  /**
    * Button to copy room url
    */
  val copyURLButton: Button = MainMenuView.createButton(715.0, 50.0, 70.0, "Copy URL")

  /**
    * Canvas of the room (only local changes)
    */
  val roomCanvas: Canvas = new Canvas(500.0, 500.0)

  /**
    * Loading pane visible only until canvas is full loaded
    */
  private val loadingStackPane: StackPane = new StackPane {
    this.prefHeight = 500.0
    this.prefWidth = 500.0
    this.styleClass = List("loading-pane")
    this.children = new Text {
      this.text = "Loading ..."
      this.styleClass = List("loading-text")
    }
  }

  /**
    * Toggle button to bucket fill
    */
  private val bucketFillToggleButton: ToggleButton = new ToggleButton {
    this.graphic = createImageView(14.0, 14.0, "/images/BucketIcon.png")
    this.styleClass = List("toggle-button")
  }

  /**
    * Slider to change paintbrush width
    */
  private val paintbrushWidthSlider: Slider = new Slider {
    this.min = 1.0
    this.max = 30.0
    this.minHeight = 26.0
    this.styleClass = List("slider")
  }

  /**
    * Picker to pick paintbrush color
    */
  private val colorPicker: ColorPicker = new ColorPicker {
    this.accessibleRole = AccessibleRole.ImageView
    this.value = Color.Black
    this.prefWidth = 20.0
    this.minHeight = 26.0
    this.styleClass = List("color-picker")
  }

  /**
    * Server canvas which shows user and online changes
    */
  private val serverCanvas: Canvas = new Canvas(500.0, 500.0)

  /**
    * Stack pane for canvases and loading image
    */
  private val canvasStackPane: StackPane = new StackPane {
    this.prefHeight = 500.0
    this.prefWidth = 500.0
    this.children = List(serverCanvas, roomCanvas, loadingStackPane)
  }

  /**
    * HBox for tools (redo, undo...)
    */
  private val toolsHBox: HBox = new HBox {
    this.prefHeight = 44.0
    this.prefWidth = 500.0
    this.spacing = 15
    this.styleClass = List("tools-HBox")
    this.children = List(undoButton, redoButton, bucketFillToggleButton, colorPicker, paintbrushWidthSlider)
  }

  /**
    * Scene main VBox to position elements
    */
  private val sceneVBox: VBox = new VBox {
    this.layoutX = 162.0
    this.layoutY = 26.0
    this.prefHeight = 548.8
    this.prefWidth = 500.0
    this.styleClass = List("room-VBox")
    this.children = List(toolsHBox, canvasStackPane)
  }

  /**
    * Main pane of the scene
    */
  private val scenePane: Pane = new Pane {
    this.prefHeight = sceneHeight
    this.prefWidth = sceneWidth
    this.styleClass = List("menu-background")
    this.children = List(mainMenuButton, copyURLButton, sceneVBox)
  }

  stylesheets = List(getClass.getClassLoader.getResource("styles.css").toExternalForm)
  content = scenePane

  /**
    * Getting color chosen by user
    *
    * @return color
    */
  def getColorFromColorPicker: Color = new Color(colorPicker.getValue)

  /**
    * Getting width chosen by user
    *
    * @return width
    */
  def getPaintbrushWidth: Double = paintbrushWidthSlider.getValue

  /**
    *
    * @return true if the button is pressed, false otherwise
    */
  def isBucketFillButtonPressed: Boolean = bucketFillToggleButton.isSelected

  /**
    * Hide the loading pane when finished loading
    */
  def endLoading(): Unit = loadingStackPane.visible = false

  /**
    * Drawing dump image on the canvas
    *
    * @param image image to be drawn
    */
  def drawDump(image: Image): Unit = {
    serverCanvas.graphicsContext2D.clearRect(0, 0, roomCanvas.getWidth, roomCanvas.getHeight)
    serverCanvas.graphicsContext2D.drawImage(image, 0, 0)
  }

  /**
    * Drawing stroke from serwer on canvas
    *
    * @param color     color of the stroke
    * @param lineCap   lineCap of the stroke
    * @param fillStyle fillStyle of the stroke
    * @param lineWidth width of the stroke
    * @param stroke    points of the stroke
    */
  def drawStrokeOnCanvas(color: String, lineCap: String, fillStyle: String, lineWidth: Int, stroke: List[Int]): Unit = {
    roomCanvas.getGraphicsContext2D.clearRect(0, 0, roomCanvas.getWidth, roomCanvas.getHeight)
    val gc = serverCanvas.graphicsContext2D
    gc.setStroke(Color.web(color))
    gc.setLineWidth(lineWidth)
    lineCap match {
      case "round" =>
        gc.setLineCap(StrokeLineCap.Round)
      case "square" =>
        gc.setLineCap(StrokeLineCap.Square)
      case _ =>
        gc.setLineCap(StrokeLineCap.Butt)
    }

    gc.beginPath
    for (List(x, y) <- stroke.grouped(2)) {
      gc.lineTo(x, y)
      gc.stroke()
    }
  }

  /**
    * Beginning drawing after mouse was pressed on canvas
    *
    * @param x x coordinate where the event of drawing was executed
    * @param y y coordinate where the event of drawing was executed
    */
  def beginUserStroke(x: Double, y: Double): Unit = {
    val gc = roomCanvas.graphicsContext2D
    gc.setLineWidth(getPaintbrushWidth)
    gc.setStroke(getColorFromColorPicker)
    gc.setLineCap(StrokeLineCap.Round)
    gc.beginPath()
    drawUserStroke(x, y)
  }

  /**
    * Drawing on the canvas stroke made by user
    *
    * @param x x coordinate where the event of drawing was executed
    * @param y y coordinate where the event of drawing was executed
    */
  def drawUserStroke(x: Double, y: Double): Unit = {
    val gc = roomCanvas.graphicsContext2D
    gc.lineTo(x, y)
    gc.stroke()
  }

  /**
    * Creating ImageView
    *
    * @param iFitWidth  width to fit the image
    * @param iFitHeight height to fit the image
    * @param path       path of the image
    * @return image to imageView
    */
  private def createImageView(iFitWidth: Double, iFitHeight: Double, path: String): ImageView = new ImageView {
    this.fitWidth = iFitWidth
    this.fitHeight = iFitHeight
    this.image = new Image(getClass.getResourceAsStream(path))
  }

}
