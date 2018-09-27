package de.david.qrcodegen.controller

import javafx.scene.image.Image
import tornadofx.*

class ImageController : Controller() {

    var url: String = ""

    init {
        subscribe<de.david.qrcodegen.gui.qrcodeimage.ImageRequest> {
            val image = Image(url)
            fire(de.david.qrcodegen.gui.qrcodeimage.ImageEvent(image))
        }
    }
}