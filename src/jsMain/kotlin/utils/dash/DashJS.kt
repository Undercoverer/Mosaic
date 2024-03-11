package utils.dash

import kotlinx.browser.document
import org.w3c.dom.HTMLElement

@JsModule("dashjs")
@JsNonModule
external object dashjs {
    fun MediaPlayer(): MP

    interface MP {
        fun create(): MP
        fun initialize(video: HTMLElement, url: String, autoPlay: Boolean)
    }
}


fun dashjs.create(componentId: String, url: String) {
    val player = MediaPlayer().create()
    val component = (document.querySelector("#$componentId") ?: error("Couldn't find component")) as HTMLElement
    player.initialize(component, url, false)
}