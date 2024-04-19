package gay.extremist.mosaic.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.*
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
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.*
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.FlexWrap
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


                Row(Modifier.fillMaxSize().gap(2.cssRem).padding(3.px).background(Colors.Transparent), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){}

                Column(Modifier.fillMaxSize().padding(15.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                })) {
                    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        Row(Modifier.fontSize(1.4.cssRem).gap(2.cssRem)){
                            Div() {
                                SpanText(
                                    "Video Title", Modifier.color(
                                        when (ColorMode.current) {
                                            ColorMode.LIGHT -> Colors.Black
                                            ColorMode.DARK -> Colors.White
                                        }
                                    )
                                )
                            }

                        }


                    }
                    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        Row(Modifier.fontSize(1.4.cssRem).gap(2.cssRem), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                            Div {//temp color
                                Link("/creator", "Creator", Modifier.color(sitePalette.brand.accent))
                            }
                        }

                        Spacer()
                        Row(Modifier.fontSize(1.1.cssRem).gap(2.cssRem), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                            SpanText(
                                "01/23/14", Modifier.color(
                                    when (ColorMode.current) {
                                        ColorMode.LIGHT -> Colors.Black
                                        ColorMode.DARK -> Colors.White
                                    }
                                )
                            )
                            SpanText(
                                "Views: 789", Modifier.color(
                                    when (ColorMode.current) {
                                        ColorMode.LIGHT -> Colors.Black
                                        ColorMode.DARK -> Colors.White
                                    }
                                )
                            )
                            Row(Modifier.fontSize(1.1.cssRem), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                SpanText(
                                    "Rating: 3.54/5", Modifier.color(
                                        when (ColorMode.current) {
                                            ColorMode.LIGHT -> Colors.Black
                                            ColorMode.DARK -> Colors.White
                                        }
                                    )
                                )
                                var ratingValue by remember { mutableStateOf(0) }
                                RatingFunc { rating ->
                                    ratingValue = rating
                                    // Do something with the rate
                                    println("Rate submitted: $rating")
                                }
                            }

                        }

                        Spacer()
                        val ctx = rememberPageContext()
                        Button(onClick = { ctx.router.tryRoutingTo("/creator")}, Modifier.color(sitePalette.brand.accent)) {
                            Text("Follow")
                        }
                        Column(Modifier.width(1.cssRem)){  }
                        SavePopUp(
                            checkboxItems = listOf("Playlist 1", "Playlist 2", "Playlist 3","Playlist 1", "Playlist 2", "Playlist 3","Playlist 1", "Playlist 2", "Playlist 3","Playlist 1", "Playlist 2", "Playlist 3"),
                            onPlaylistAction = { playlist ->
                                // Perform action with playlist
                                println("Playlist submitted: $playlist")
                            },
                            onCheckboxAction = { selectedItem ->
                                // Perform action with selected checkbox item
                                selectedItem?.let {
                                    println("Checkbox submitted: $it")
                                }
                            }
                        )

                    }
                }


                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(10.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {
                    Column(Modifier.fillMaxSize().gap(1.cssRem).fontSize(1.1.cssRem).height(10.cssRem).overflow { y(Overflow.Auto)}, horizontalAlignment = Alignment.Start) {
                        Div{
                            SpanText(
                                "Desc", Modifier.color(
                                    when (ColorMode.current) {
                                        ColorMode.LIGHT -> Colors.Black
                                        ColorMode.DARK -> Colors.White
                                    }
                                )
                            )
                        }
                        Box(Modifier.fillMaxSize().height(15.cssRem)) {
                           Row(Modifier.width(58.cssRem).gap(0.5.cssRem).flexWrap(FlexWrap.Wrap)){
                               for (index in 1..100) {
                                   Link(
                                       "/tags",
                                       "Tags",
                                       Modifier.color(sitePalette.brand.primary)
                                   )
                               }

                           }

                        }

                    }

                }

                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(10.px).fontSize(1.1.cssRem).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {
                    Box(Modifier.fillMaxSize().padding(2.cssRem).height(15.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(1.cssRem).fillMaxSize()){
                            CommentFunc { comment ->
                                // Do something with the comment
                                println("Comment submitted: $comment")
                            }

                            for (index in 1..25) {
                                CommentTile()
                            }

                        }

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
                    ), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
                ) {

                    Box(Modifier.fillMaxSize().padding(2.cssRem).height(60.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(1.cssRem).fillMaxSize()){
                            val ctx = rememberPageContext()
                            for (index in 1..25) {
                                VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {

                                }
                            }

                        }

                    }

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}





