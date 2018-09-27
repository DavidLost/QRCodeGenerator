package de.david.qrcodegen.gui

import de.david.qrcodegen.gui.qrcodeimage.ImageEvent
import de.david.qrcodegen.gui.qrcodeimage.ImageRequest
import de.david.qrcodegen.controller.DataController
import de.david.qrcodegen.web.Downloader
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Pos
import javafx.stage.FileChooser
import tornadofx.*

class TextQRView : View(MyApp.APP_TITLE) {

    private val dataController: DataController by inject()
    private val textStringProperty = SimpleStringProperty()
    private val imageController = de.david.qrcodegen.controller.ImageController()

    override val root = form {

        fieldset {
            field ("Text") {
                textfield (textStringProperty)
            }
        }
        paddingAll = 30

        borderpane {
            center = imageview {
                subscribe<ImageEvent> {event ->
                    image = event.image
                }
            }
        }

        hbox (254) {
            vbox {
                alignment = Pos.BOTTOM_CENTER
                button ("Back") {
                    action {
                        replaceWith(MainMenu::class, ViewTransition.Slide(0.4.seconds, ViewTransition.Direction.RIGHT))
                    }
                }
            }
            vbox {
                alignment = Pos.BOTTOM_CENTER
                button ("Generate") {
                    disableProperty().bind(textStringProperty.isEmpty.or(textStringProperty.isNull))
                    action {
                        imageController.url = dataController.getQRCodeLink(textStringProperty.value)
                        fire(ImageRequest)
                    }
                }
            }
            vbox {
                alignment = Pos.BOTTOM_CENTER
                button ("Download") {
                    disableProperty().bind(textStringProperty.isEmpty.or(textStringProperty.isNull))
                    action {
                        val chooser = FileChooser()
                        chooser.initialFileName = "qrcoce.png"
                        val file = chooser.showSaveDialog(null)
                        Thread(Downloader(dataController.getQRCodeLink(textStringProperty.value), file)).start()
                    }
                }
            }
        }
        /*borderpane {
            left = button ("Back") {
                action {
                    replaceWith(MainMenu::class, ViewTransition.Slide(0.4.seconds, ViewTransition.Direction.RIGHT))
                }
            }
            center = button ("Generate") {
                disableProperty().bind(textStringProperty.isEmpty.or(textStringProperty.isNull))
                action {
                    imageController.url = dataController.getQRCodeLink(textStringProperty.value)
                    fire(ImageRequest)
                }
            }
            right = button ("Download") {
                disableProperty().bind(textStringProperty.isEmpty.or(textStringProperty.isNull))
                action {
                    val chooser = FileChooser()
                    chooser.initialFileName = "qrcoce.png"
                    val file = chooser.showSaveDialog(null)
                    Thread(Downloader(dataController.getQRCodeLink(textStringProperty.value), file)).start()
                }
            }
        }*/

    }

}