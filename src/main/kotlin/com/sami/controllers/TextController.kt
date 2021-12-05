package com.sami.controllers

import javafx.beans.property.SimpleStringProperty
import javafx.scene.control.Alert
import tornadofx.*
import java.io.*

class TextController: Controller() {
    val filename = SimpleStringProperty()
    val data = SimpleStringProperty()

    fun loadFile(filepath: String) {
        runAsync {
            updateProgress(0.4, 1.0)

            val bufferedReader = File(filepath).bufferedReader()
            val text = bufferedReader.use { it.readText() }
            text
        } ui {
            filename.value = filepath
            data.value = it
        } fail {
            alert(Alert.AlertType.ERROR, "File cannot be opened",
                content = "${it.javaClass.name}: ${it.message}")
        }
    }

    fun saveFile(filepath: String) {
        runAsync {
            updateProgress(0.4, 1.0)

            File(filepath).bufferedWriter().use {
                it.write(data.value)
            }
        } ui {
            filename.value = filepath
        } fail {
            alert(Alert.AlertType.ERROR, "File cannot be saved",
                content = "${it.javaClass.name}: ${it.message}")
        }
    }
}