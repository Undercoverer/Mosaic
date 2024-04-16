package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import com.varabyte.kobweb.compose.css.Cursor
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.style.KobwebComposeStyleSheet.hover
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import gay.extremist.mosaic.data_models.VideoDisplayResponse
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
fun SearchVideoTile(onClick: () -> Unit, content: @Composable () -> Unit) {

    // Create for loop to take in number of videos (dynamic)
    // Each row has a padding of 10px
    Row(Modifier.fillMaxSize().height(10.cssRem).padding(15.px).cursor(Cursor.Pointer)

        .background(Color.rgba(0.3f,0.3f,0.3f,0.3f))
        .onClick { onClick() }) {
        Box(Modifier.fillMaxWidth(20.percent).height(7.cssRem), Alignment.Center) {
            ContentScale.Fit
            Image("/MosaicThumbnail.PNG",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.px), )
        }
        Box(Modifier.fillMaxWidth(80.percent).padding(1.cssRem)) {
            content()
        }
    }
}