package com.sami.stylesheets

import javafx.scene.text.FontWeight
import tornadofx.Stylesheet
import tornadofx.px

class TextStyle: Stylesheet() {
    init {
        root {
            prefHeight = 700.px
            prefWidth = 1200.px
            fontSize = 20.px
            FontWeight.BOLD
        }
    }
}