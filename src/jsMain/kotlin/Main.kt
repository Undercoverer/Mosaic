import kotlinx.browser.document
import kotlinx.html.dom.append
import kotlinx.html.id
import kotlinx.html.js.video
import kotlinx.html.onLoad
import org.w3c.dom.HTMLElement
import utils.dash.create
import utils.dash.dashjs


suspend fun main() {
    val appContainer = (document.getElementById("root") ?: error("Couldn't find root container!")) as HTMLElement

    val url = "https://dash.akamaized.net/envivio/Envivio-dash2/manifest.mpd"

    appContainer.append.video {
        id = "videoPlayer"
        controls = true
    }
    dashjs.create("videoPlayer", url)
}







