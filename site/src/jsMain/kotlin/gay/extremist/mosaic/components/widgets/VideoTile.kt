package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import androidx.compose.ui.layout.ContentScale
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.graphics.Image
// import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px


@Composable
fun VideoTile(onClick: () -> Unit, content: @Composable () -> Unit) {

    // Create for loop to take in number of videos (dynamic)
    // Each row has a padding of 10px
    Row(Modifier.fillMaxSize().height(10.cssRem).padding(15.px)
        //.border(width = 1.px, style = LineStyle.Solid)
        .background(Color.rgba(0.3f,0.3f,0.3f,0.3f))
        .onClick { onClick() }) {
        Box(Modifier.fillMaxWidth(30.percent).height(7.cssRem), Alignment.Center) {
            ContentScale.Fit
            Image("https://previews.123rf.com/images/mirage3/mirage31312/mirage3131200021/24595044-mosaic-small-colorful-tile-background-fun-abstract.jpg",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.px), )
        }
        
        Box(Modifier.fillMaxWidth(70.percent).padding(1.cssRem)) {
            content()
        }
    }
}