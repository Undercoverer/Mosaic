package gay.extremist.mosaic.pages

//import androidx.compose.foundation.shape

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
import gay.extremist.mosaic.components.layouts.UnsignInPageLayout
import gay.extremist.mosaic.components.widgets.SearchVideoTile
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Page("/unsignedhome")
@Composable
fun UnsignedHomePage() {
    UnsignInPageLayout("Unsigned Home"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.secondary)
                .width(25.cssRem)
                .height(20.cssRem),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                }

            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.accent)
                .height(45.cssRem),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "General Videos",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(38.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fillMaxSize()){
                        val ctx = rememberPageContext()
                        SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                            P { Text("Title\n") }

                        }
                        SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                            P { Text("Title\n") }

                        }
                        SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                            P { Text("Title\n") }

                        }
                        SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                            P { Text("Title\n") }

                        }
                        SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                            P { Text("Title\n") }

                        }
                        SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                            P { Text("Title\n") }

                        }

                    }

                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette
                    .brand.primary)
                .width(25.cssRem)
                .height(30.cssRem),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(1.cssRem)) {}



    }

}