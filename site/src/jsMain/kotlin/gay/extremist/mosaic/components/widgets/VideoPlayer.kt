package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import gay.extremist.mosaic.bindings.dashjs
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Video
import org.w3c.dom.HTMLVideoElement

@Composable
fun VideoPlayer(id: String, src: String) {
    val scope = rememberCoroutineScope()

    Video(
        attrs = {
            id(id)
            attr("controls", "true")
            style {
                width(100.percent)
                height(100.percent)
            }

        }
    )

    scope.launch {
        runCatching {
            val video = document.getElementById(id) as HTMLVideoElement
            dashjs.MediaPlayer().create().initialize(video, src, false)
        }
    }

}

fun attr(attr: String) {

}
