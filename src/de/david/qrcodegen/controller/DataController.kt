package de.david.qrcodegen.controller

import tornadofx.Controller

class DataController : Controller() {

    fun getQRCodeLink(text: String): String {
        return "http://api.qrserver.com/v1/create-qr-code/?data="+text.replace(" ", "%20")
    }

}