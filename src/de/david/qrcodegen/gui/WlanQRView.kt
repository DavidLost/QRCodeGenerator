package de.david.qrcodegen.gui

import de.david.qrcodegen.cmd.NetworkInfoGetter
import de.david.qrcodegen.gui.qrcodeimage.ImageEvent
import javafx.geometry.Pos
import javafx.scene.control.Alert
import tornadofx.*
import kotlin.NullPointerException

class WlanQRView : View(MyApp.APP_TITLE) {

    val networkInfoGetter = NetworkInfoGetter()

    override val root = form {

        fun exitOnNoWlanSupport() {
            val error = Alert(Alert.AlertType.ERROR)
            error.title = "Error"
            error.contentText = "This machine does not support Wlan!"
            error.show()
            replaceWith(MainMenu::class, ViewTransition.Slide(0.4.seconds, ViewTransition.Direction.RIGHT))
        }

        try {
            if (networkInfoGetter.networks.size <= 0) {
                exitOnNoWlanSupport()
            }
        } catch (e: NullPointerException) {
            exitOnNoWlanSupport()
        }

        for (n in networkInfoGetter.networks) {
            button(n.name) {
                setOnAction {
                    println("you pressed")
                }
            }
        }

        paddingAll = 30

        borderpane {
            center = imageview {
                subscribe<ImageEvent> { event ->
                    image = event.image
                }
            }
        }

        hbox(254) {
            vbox {
                alignment = Pos.BOTTOM_CENTER
                button("Back") {
                    action {
                        replaceWith(MainMenu::class, ViewTransition.Slide(0.4.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
            }
            vbox {
                alignment = Pos.BOTTOM_CENTER
                button("Generate") {
                    /*disableProperty().bind(textStringProperty.isEmpty.or(textStringProperty.isNull))
                    action {
                        imageController.url = dataController.getQRCodeLink(textStringProperty.value)
                        fire(ImageRequest)
                    }*/
                }
            }
            vbox {
                alignment = Pos.BOTTOM_CENTER
                button("Download") {
                    /*disableProperty().bind(textStringProperty.isEmpty.or(textStringProperty.isNull))
                    action {
                        val chooser = FileChooser()
                        chooser.initialFileName = "qrcoce.png"
                        val file = chooser.showSaveDialog(null)
                        Thread(Downloader(dataController.getQRCodeLink(textStringProperty.value), file)).start()
                    }*/
                }
            }
        }
    }
}