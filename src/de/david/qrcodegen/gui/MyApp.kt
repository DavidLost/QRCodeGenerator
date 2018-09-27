package de.david.qrcodegen.gui

import tornadofx.App

class MyApp : App(MainMenu::class) {
    companion object {
        val APP_TITLE = "QR Code Generator"
    }
}