import components.DashVideoPlayer
import react.*
import react.dom.client.createRoot
import web.dom.document

fun main() {
    val appContainer = (document.getElementById("root") ?: error("Couldn't find root container!"))// as HTMLElement

    createRoot(appContainer).render(App.create())
}


private val App = FC<Props>("MainApp") {
    DashVideoPlayer {
        src = "https://dash.akamaized.net/envivio/Envivio-dash2/manifest.mpd"
    }
}








