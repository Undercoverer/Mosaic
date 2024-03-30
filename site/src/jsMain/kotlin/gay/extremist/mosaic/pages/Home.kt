

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
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.VideoTile
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Page("/home")
@Composable
fun HomePage() {
    PageLayout("Home"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.secondary),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Creator Videos")
                }
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.7.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fillMaxSize()){
                        val ctx = rememberPageContext()
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }

                    }

                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.accent),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "General Videos")
                }
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.7.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fillMaxSize()){
                        val ctx = rememberPageContext()
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }

                    }

                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette
                    .brand.primary),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Followed Tags Videos")
                }
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.7.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fillMaxSize()){
                        val ctx = rememberPageContext()
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }

                    }

                }
            }
        }

        Row(modifier = Modifier.fillMaxSize().height(1.cssRem)) {}


    }

}