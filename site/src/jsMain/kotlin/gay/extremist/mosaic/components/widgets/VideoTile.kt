package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Width
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px

/**
 * A video tile which is a rectangle with a thumbnail on the left and the title and description on the right
 * The thumbnail takes up 20% of the rectangle and the title and description take up the other 80% and are
 * vertically centered in that section. Clicking on the tile will trigger the onClick callback.
 *
 * Video description and title are passed in as content.
 */
@Composable
fun VideoTile(onClick: () -> Unit, content: @Composable () -> Unit) {
    // Each row has a padding of 10px
    Row(Modifier.fillMaxSize(90.percent).padding(10.px).border(width = 1.px, style = LineStyle.Solid).borderRadius(10.percent).onClick { onClick() }) {
        Box(Modifier.fillMaxWidth(20.percent).outline(1.px, LineStyle.Solid).borderRadius(10.percent)) {
            Image("https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%3Fid%3DOIP.OWjczO_I11JE4BnIcrnpPAAAAA%26pid%3DApi&f=1&ipt=1195600b0700b9fca663ebf733ee02315b6e6dfda363309db3fbbe82db0eb787&ipo=images")
        }
        Box(Modifier.fillMaxWidth(80.percent).outline(1.px, LineStyle.Solid).borderRadius(10.percent)) {
            content()
        }
    }
}
