package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
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
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

/**
 * A video tile which is a rectangle with a thumbnail on the left and the title and description on the right
 * The thumbnail takes up 20% of the rectangle and the title and description take up the other 80% and are
 * vertically centered in that section. Clicking on the tile will trigger the onClick callback.
 *
 * Video description and title are passed in as content.
 */
@Composable
fun PlaylistVideoTile(
    onClick: () -> Unit,
    onDelete: () -> Unit, // New parameter for delete action
    content: @Composable () -> Unit
) {
    Row(Modifier.fillMaxSize().height(10.cssRem).padding(15.px)
        .background(Color.rgba(0.3f,0.3f,0.3f,0.3f))
        .onClick { onClick() }, verticalAlignment = Alignment.CenterVertically) {
        IconButton(
            onClick = { onDelete() }
        ) {
            Text("X") // White "x" icon
        }

        Box(Modifier.fillMaxWidth(20.percent).height(7.cssRem).onClick { onClick() }, Alignment.Center) {
            ContentScale.Fit

            Image("/MosaicThumbnail.PNG",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.px), )
        }
        Box(Modifier.fillMaxWidth(80.percent).padding(1.cssRem).overflow(Overflow.Hidden)) {
            Column(Modifier.gap(2.px).fontSize(1.2.cssRem), verticalArrangement = Arrangement.Top){
                SpanText("T32111111111111111111111111111111111111111111111111111111111111111111111111le")
                Column(Modifier.gap(1.px).fontSize(1.cssRem)){
                    SpanText("Creator")
                    SpanText("01/23/24")
                }
            }

        }
    }
}