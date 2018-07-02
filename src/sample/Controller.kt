package sample

import javafx.fxml.FXML
import javafx.scene.canvas.Canvas
import javafx.scene.control.Alert
import javafx.scene.control.TextArea
import javafx.scene.input.MouseDragEvent
import javafx.scene.input.MouseEvent

const val WEIGHT=106
const val HEIGHT=17
const val PIXEL=15.0
class Controller{
    private val binaryArray= Array(WEIGHT){IntArray(HEIGHT){0}}
    @FXML
    lateinit var textArea:TextArea
    @FXML
    lateinit var canvas: Canvas

    @FXML
    fun initialize(){
        val gc= canvas.graphicsContext2D
        gc.lineWidth=1.0
        for (i in 0..HEIGHT)
            gc.strokeLine(0.0,i*PIXEL, WEIGHT*PIXEL,i*PIXEL)
        for (i in 0..WEIGHT)
            gc.strokeLine(i*PIXEL,0.0,i*PIXEL, HEIGHT*PIXEL)
    }

    @FXML
    fun handleCanvasClicked(event:MouseEvent){
        val gc= canvas.graphicsContext2D
        val i=(event.x/PIXEL).toInt()
        val j=16-(event.y/PIXEL).toInt()
        val x=(event.x/PIXEL).toInt()*PIXEL
        val y=(event.y/PIXEL).toInt()*PIXEL
        if (binaryArray[i][j]==0) {
            gc.fillRect(x+1.5,y+1.5, PIXEL-3, PIXEL-3)
            binaryArray[i][j]= 1
        } else {
            gc.clearRect(x+1.5,y+1.5, PIXEL-3, PIXEL-3)
            binaryArray[i][j]= 0
        }
    }

    @FXML
    fun handleCanvasDragged(event:MouseEvent){
        val gc= canvas.graphicsContext2D
        val i=(event.x/PIXEL).toInt()
        val j=16-(event.y/PIXEL).toInt()
        val x=(event.x/PIXEL).toInt()*PIXEL
        val y=(event.y/PIXEL).toInt()*PIXEL
        if (binaryArray[i][j]==0) {
            gc.fillRect(x+1.5,y+1.5, PIXEL-3, PIXEL-3)
            binaryArray[i][j]= 1
        }
    }

    @FXML
    fun handleBtBitmapToK(){
        var binaryString=""
        for (i in binaryArray)
            for (j in i)
                binaryString = j.toString() + binaryString
        textArea.text=(binaryString.toBigInteger(2)* HEIGHT.toBigInteger()).toString()
    }

    @FXML
    fun handleBtKToBitmap(){
        handleBtClear()
        val gc= canvas.graphicsContext2D
        for (i in binaryArray)
            i.fill(0)
        val alert=Alert(Alert.AlertType.INFORMATION)
        alert.apply {
            title="Invalid K"
            headerText=null
        }
        try {
            val k=textArea.text.filterNot { it==' ' }.toBigInteger()
            if (k%(HEIGHT.toBigInteger())!=(0.toBigInteger()))
                alert.apply {
                    contentText="K must be divided by 17"
                    showAndWait()
                }
            val binaryString=(k/HEIGHT.toBigInteger()).toString(2)
            for ((index,binaryChar) in binaryString.reversed().withIndex()) {
                val i=index / HEIGHT
                val j=index % HEIGHT
                val x=i* PIXEL
                val y=(HEIGHT-j-1)* PIXEL
                if (binaryChar == '1') {
                    gc.fillRect(x+1.5,y+1.5, PIXEL-3, PIXEL-3)
                    binaryArray[i][j] = 1
                } else {
                    gc.clearRect(x+1.5,y+1.5, PIXEL-3, PIXEL-3)
                    binaryArray[i][j] = 0
                }
            }
        }catch (e:NumberFormatException){
            alert.apply {
                contentText="K must be a number"
                showAndWait()
            }
        }
    }

    @FXML
    fun handleBtClear(){
        val gc= canvas.graphicsContext2D
        for ((indexI,i) in binaryArray.withIndex())
            for ((indexJ,j) in i.withIndex())
                if (j==1){
                    val x=indexI* PIXEL
                    val y=(HEIGHT-indexJ-1)* PIXEL
                    gc.clearRect(x+1.5,y+1.5, PIXEL-3, PIXEL-3)
                    binaryArray[indexI][indexJ] = 0
                }
    }
}
