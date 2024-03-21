package gay.extremist.mosaic.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.varabyte.kobweb.compose.css.height
import com.varabyte.kobweb.compose.css.width
import gay.extremist.mosaic.bindings.dashjs
import kotlinx.browser.document
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.selectors.CSSSelector
import org.jetbrains.compose.web.dom.Video
import org.w3c.dom.HTMLVideoElement

@Composable
fun VideoPlayer(id: String, src: String, width: Int, height: Int) {
    val scope = rememberCoroutineScope()

    Video(
        attrs = {
            id(id)
            attr("controls", "true")
            attr("width", width.toString())
            attr("height", height.toString())
        }
    )

    scope.launch {
        val video = document.getElementById("video-player") as HTMLVideoElement
        dashjs.MediaPlayer().create().initialize(video, src, false)
    }

}

fun attr(attr: String) {

}
