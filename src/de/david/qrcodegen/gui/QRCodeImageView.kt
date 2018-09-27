package de.david.qrcodegen.gui

import tornadofx.View
import tornadofx.form
import tornadofx.imageview

class QRCodeImageView : View("QR-Code") {

    override fun onDock() {
        primaryStage.width = 200.0
        primaryStage.height = 120.0
        primaryStage.isResizable = false
    }

    override val root = form {
        requestFocus()
        imageview ()
    }

}