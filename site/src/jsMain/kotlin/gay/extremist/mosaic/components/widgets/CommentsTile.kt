package gay.extremist.mosaic.components.widgets

// import org.jetbrains.compose.web.css.LineStyle
import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.OverflowWrap
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px


@Composable
fun CommentTile() {
    val sitePalette = ColorMode.current.toSitePalette()
    Row(Modifier.fillMaxSize().height(10.cssRem).padding(5.px)
        //.border(width = 1.px, style = LineStyle.Solid)
        .background(Color.rgba(0.3f,0.3f,0.3f,0.3f))
        ) {

        Box(Modifier.fillMaxWidth().padding(1.cssRem)) {
            Column(
                modifier = Modifier.gap(2.px).fontSize(1.cssRem),
                verticalArrangement = Arrangement.Top
            ) {
                Link("/creator",
                    "Creator", Modifier.color(sitePalette.brand.accent)
                )

                SpanText(
                    "T1321111111111111111111111111hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh11111111111jkjkjk111111111111111111111111111111111111le",
                    modifier = Modifier.maxWidth(53.cssRem) // Set your desired maximum width
                        .overflowWrap(OverflowWrap.BreakWord) // Wrap the text if it overflows the maxWidth
                )
            }
        }
    }
}