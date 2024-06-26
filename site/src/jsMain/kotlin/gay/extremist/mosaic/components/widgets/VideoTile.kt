package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.text.SpanText
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import kotlin.js.Date


@Composable
fun VideoTile(onClick: () -> Unit, video: VideoDisplayResponse) {

    // Create for loop to take in number of videos (dynamic)
    // Each row has a padding of 10px
    Row(Modifier.fillMaxSize().height(10.cssRem).padding(15.px).cursor(Cursor.Pointer)
        //.border(width = 1.px, style = LineStyle.Solid)
        .background(Color.rgba(0.3f,0.3f,0.3f,0.3f))
        .onClick { onClick() }) {
        Box(Modifier.fillMaxWidth(35.percent).height(7.cssRem), Alignment.Center) {
            ContentScale.Fit
            Image("/MosaicThumbnail.PNG",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.px), )
        }

        Box(Modifier.fillMaxWidth(65.percent).padding(1.cssRem).overflow(Overflow.Hidden)) {
            Column(Modifier.gap(2.px).fontSize(1.2.cssRem), verticalArrangement = Arrangement.Top) {
                SpanText(video.title, Modifier.fontSize(FontSize.Large).padding(bottom = .5.cssRem))
                Column(Modifier.gap(1.px).fontSize(1.cssRem)) {
                    SpanText("Creator: ${ video.creator.username }")
                    SpanText("Uploaded: ${Date(video.uploadDate).toLocaleDateString()}")
                }
            }
        }
    }
}