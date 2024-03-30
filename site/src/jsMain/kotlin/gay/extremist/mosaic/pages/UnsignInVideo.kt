package gay.extremist.mosaic.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.HeadlineTextStyle
import gay.extremist.mosaic.SubheadlineTextStyle
import gay.extremist.mosaic.components.layouts.UnsignInPageLayout
import gay.extremist.mosaic.components.widgets.VideoPlayer
import gay.extremist.mosaic.components.widgets.VideoTile
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


val UnsignInVideoContainerStyle by ComponentStyle {
    base { Modifier.fillMaxWidth().gap(10.cssRem) }

}
@Page("/unsigninvideo")
@Composable
fun UnsignInVideoPage() {
    UnsignInPageLayout("Unsigned In Video") {

        Row(
            Modifier.fillMaxWidth().gap(1.cssRem)

        ) {


            Column(Modifier.fillMaxSize().width(180.cssRem), horizontalAlignment = Alignment.Start) {
                val sitePalette = ColorMode.current.toSitePalette()
                Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center){
                    VideoPlayer(
                        id = "player",
                        src = "https://dash.akamaized.net/envivio/EnvivioDash3/manifest.mpd")

                }


                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(15.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {

                    Div(SubheadlineTextStyle.toAttrs()){
                        SpanText(
                            "Video Title    ", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )
                        SpanText(
                            "Creator   ", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )

                        SpanText(
                            "   Date   ", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )
                        SpanText(
                            "Views   ", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )
                        SpanText(
                            "Rating   ", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )

                    }
                }
                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(15.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {
                    Div(SubheadlineTextStyle.toAttrs()){
                        SpanText(
                            text = "Tags",
                        )
                        SpanText(
                            "   Desc", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )
                    }

                }

                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(15.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {
                    Div(SubheadlineTextStyle.toAttrs()){
                        SpanText(
                            "Comments", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )
                    }
                }

            }


            val sitePalette = ColorMode.current.toSitePalette()
            Column(Modifier.fillMaxSize().background(when (ColorMode.current) {
                ColorMode.LIGHT -> Colors.LightGray
                ColorMode.DARK -> Color.rgb(0x2B2B2B)
            })) {
                Column(
                    Modifier.fillMaxSize().padding(15.px).background(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.LightGray
                            ColorMode.DARK -> Color.rgb(0x2B2B2B)
                        }
                    ), horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Div(HeadlineTextStyle.toAttrs()){
                        SpanText(
                            text = "Similar Videos",
                            modifier = Modifier.padding(20.px).fontSize(35.px),
                        )
                    }

                    Box(Modifier.fillMaxSize().padding(2.cssRem).height(38.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(1.cssRem).fillMaxSize()){
                            val ctx = rememberPageContext()
                            VideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                                P { Text("Title\n") }

                            }
                            VideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                                P { Text("Title\n") }

                            }
                            VideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                                P { Text("Title\n") }

                            }
                            VideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                                P { Text("Title\n") }

                            }
                            VideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                                P { Text("Title\n") }

                            }
                            VideoTile(onClick = { ctx.router.tryRoutingTo("/unsigninvideo") }) {
                                P { Text("Title\n") }

                            }

                        }

                    }

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(1.cssRem)) {}

    }
}





