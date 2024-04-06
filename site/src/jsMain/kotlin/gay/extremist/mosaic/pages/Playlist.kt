package gay.extremist.mosaic.pages


import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.SearchVideoTile
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px


@Page("/playlist")
@Composable
fun PlaylistPage() {
    PageLayout("Playlist"){

            val sitePalette = ColorMode.current.toSitePalette()
            Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
                Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                    SpanText(
                        text = "MyPlaylist",
                        modifier = Modifier.padding(20.px).fontSize(35.px),
                    )
                    Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                            val ctx = rememberPageContext()
                            for (index in 1..25) {
                                SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                                    SpanText("Title\n")
                                }
                            }

                        }

                    }
                }
                Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).height(20.cssRem).width(25.cssRem)) {  }

                Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).height(30.cssRem).width(25.cssRem)) {  }

            }
            Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}








