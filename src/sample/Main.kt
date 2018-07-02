package sample

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class Main : Application() {
    @Throws(Exception::class)
    override fun start(primaryStage: Stage) {
        val root = FXMLLoader.load<Parent>(javaClass.getResource("sample.fxml"))
        primaryStage.apply {
            title="Tupper's self-referential formula Tool"
            scene=Scene(root, 1650.0, 500.0)
            isResizable=false
            show()
        }
    }
}
fun main(args: Array<String>)= Application.launch(Main::class.java)