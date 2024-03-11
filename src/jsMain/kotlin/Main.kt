import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.browser.document
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.p
import kotlinx.html.js.video
import org.w3c.dom.HTMLElement


suspend fun main() {
    val appContainer = (document.getElementById("root") ?: error("Couldn't find root container!")) as HTMLElement

    val url = "https://dash.akamaized.net/envivio/Envivio-dash2/manifest.mpd"

    appContainer.append.video {
        id = "videoPlayer"
        controls = true
        width = "50%"
        height = "50%"
    }
    dashjs.create("videoPlayer", url)
}


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






