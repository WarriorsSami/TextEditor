package com.sami.views

import com.sami.controllers.TextController
import com.sami.utils.ShutdownEvent
import javafx.scene.layout.Priority
import javafx.stage.FileChooser
import tornadofx.*

class TextView: View() {
    val textController: TextController by inject()
    private val status: TaskStatus by inject()

    override val root = vbox {
        // Menu Navigation Bar
        menubar {
            menu("File") {
                item("New Window") {
                    action {
                        val newScope = Scope()
                        find<TextView>(newScope).openWindow(owner = null)
                    }
                }
                item("Open") {
                    action {
                        val files = chooseFile("Open File",
                            //arrayOf(FileChooser.ExtensionFilter("Text Files", "*.txt"))
                            emptyArray()
                        )

                        if (files.isNotEmpty()) {
                            val file = files[0]
                            textController.loadFile(file.absolutePath)
                        }
                    }
                }
                item("Save") {
                    action {
                        textController.saveFile(textController.filename.value)
                    }
                }
                item("Close") {
                    action {
                        currentStage!!.hide()
                    }
                }
                separator()
                item("Exit") {
                    action {
                        fire(ShutdownEvent)
                    }
                }
            }
        }

        // Text Area
        vbox {
            textarea(textController.data) {
                vgrow = Priority.ALWAYS
            }
            separator()
            progressbar {
                visibleWhen(status.running)
                progressProperty().bind(status.progress)
            }

            paddingAll = 10.0
            spacing = 5.0
            vgrow = Priority.ALWAYS
        }
    }

    init {
        FX.eventbus.subscribe<ShutdownEvent>(
            FX.defaultScope,
            FXEventRegistration(ShutdownEvent::class, this, 1L) {
                root.scene.window!!.hide()
            }
        )
    }

    override fun onBeforeShow() {
        currentStage!!.titleProperty().bind(textController.filename)
    }
}