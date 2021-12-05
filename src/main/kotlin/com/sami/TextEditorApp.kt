package com.sami

import com.sami.stylesheets.TextStyle
import com.sami.views.TextView
import javafx.stage.Stage
import tornadofx.*

class TextEditorApp: App(TextView::class, TextStyle::class) {
    override fun start(stage: Stage) {
        super.start(stage)
        if (parameters.raw.size > 0) {
            find<TextView>().textController.loadFile(parameters.raw[0])
        }
    }
}