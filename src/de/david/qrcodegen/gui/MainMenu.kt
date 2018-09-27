package de.david.qrcodegen.gui

import tornadofx.*

class MainMenu : View(MyApp.APP_TITLE) {

    override fun onDock() {
        primaryStage.width = 800.0
        primaryStage.height = 450.0
        primaryStage.isResizable = false
    }

    override val root = vbox (24) {

        paddingHorizontal = 50
        paddingVertical = 70

        hbox {
            label ("Select QR-Code Mode") {
                style {
                    fontSize = 24.px
                }
            }
        }
        hbox {
            button ("Text") {
                action {
                    replaceWith(TextQRView::class, ViewTransition.Slide(0.4.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }
        hbox {
            button ("WLAN") {
                action {
                    replaceWith(WlanQRView::class, ViewTransition.Slide(0.4.seconds, ViewTransition.Direction.LEFT))
                }
            }
        }
    }

}