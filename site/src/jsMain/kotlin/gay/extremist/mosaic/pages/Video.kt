package gay.extremist.mosaic.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.style.toAttrs
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.HeadlineTextStyle
import gay.extremist.mosaic.SubheadlineTextStyle
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.VideoPlayer
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text


val VideoContainerStyle by ComponentStyle {
    base { Modifier.fillMaxWidth().gap(10.cssRem) }

}
@Page("/video")
@Composable
fun VideoPage() {
    PageLayout("Video") {

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

                        //temp color
                        Link("/creator", "Creator",  Modifier.color(sitePalette.brand.accent))

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
                        val ctx = rememberPageContext()
                        Button(onClick = {
                            // Change this click handler with your call-to-action behavior
                            // here. Link to an order page? Open a calendar UI? Play a movie?
                            // Up to you!
                            ctx.router.tryRoutingTo("/creator")
                        }, Modifier.color(sitePalette.brand.accent)) {
                            Text("Follow")
                        }

                        Button(onClick = {
                            // Change this click handler with your call-to-action behavior
                            // here. Link to an order page? Open a calendar UI? Play a movie?
                            // Up to you!
                            ctx.router.tryRoutingTo("/playlist")
                        }, Modifier.color(sitePalette.brand.secondary)) {
                            Text("Save")
                        }
                    }
                }
                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(15.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {
                    Div(SubheadlineTextStyle.toAttrs()){
                        Link("/tags",
                            "Tags", Modifier.color(sitePalette.brand.primary)
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
                    val ctx = rememberPageContext()
                    Button(onClick = {
                        // Change this click handler with your call-to-action behavior
                        // here. Link to an order page? Open a calendar UI? Play a movie?
                        // Up to you!
                        ctx.router.tryRoutingTo("/tags")
                    }, Modifier.color(sitePalette.brand.primary)) {
                        Text("Tags")
                    }

                    Div(HeadlineTextStyle.toAttrs()) {
                        SpanText(
                            "Mosaic",
                            Modifier
                                .color(sitePalette.brand.secondary)
                                // Use a shadow so this light-colored word is more visible in light mode
                                .textShadow(0.px, 0.px, blurRadius = 0.5.cssRem, color = Colors.Gray)
                        )
                    }
                    Div(HeadlineTextStyle.toAttrs()){
                        SpanText(
                            "Video List", Modifier.color(
                                when (ColorMode.current) {
                                    ColorMode.LIGHT -> Colors.Black
                                    ColorMode.DARK -> Colors.White
                                }
                            )
                        )
                    }
                    Column {
                        Div(SubheadlineTextStyle.toAttrs()) {
                            SpanText("List of Videos (Database) ")
                        }

                    }

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(1.cssRem)) {}

    }
}





