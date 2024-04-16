package gay.extremist.mosaic.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
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
import com.varabyte.kobweb.silk.components.overlay.KeepPopupOpenStrategy
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.components.overlay.manual
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.BASE_URL
import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.Util.*
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.*
import gay.extremist.mosaic.data_models.*
import gay.extremist.mosaic.toSitePalette
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.browser.window
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get
import kotlin.js.Date

val VideoContainerStyle by ComponentStyle {
    base { Modifier.fillMaxWidth().gap(10.cssRem) }

}
@Page("/video/{id}")
@Composable
fun VideoPage() {
    val pageCtx = rememberPageContext()
    val coroutineScope = rememberCoroutineScope()
    val sitePalette = ColorMode.current.toSitePalette()
    val id = pageCtx.route.params.getValue("id").toIntOrNull() ?: return
    val loadingVal = "Loading..."

    var video by remember {
        mutableStateOf(
            VideoResponse(
                id = -1,
                title = loadingVal,
                description = loadingVal,
                videoPath = loadingVal,
                tags = listOf(),
                creator = AccountDisplayResponse(
                    id = -1,
                    username = loadingVal
                ),
                viewCount = 0,
                uploadDate = loadingVal,
                rating = 0.0
            )
        )
    }

    var newPlaylist by remember {
        mutableStateOf<PlaylistDisplayResponse?>(null)
    }


    PageLayout("Video") {

        LaunchedEffect(id) {
            video = getRequest<VideoResponse>(
                urlString = "videos/$id",
                onError = {
                    println(it.message)
                }
            ) ?: video
        }

        Row(
            Modifier.fillMaxWidth().gap(1.cssRem)

        ) {


            Column(Modifier.fillMaxSize().width(180.cssRem), horizontalAlignment = Alignment.Start) {
                Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center){
                    VideoPlayer(
                        id = "player",
                        src = "${BASE_URL}${video.videoPath}/output.mpd".also {
                            println(it)
                        }
                    )
                }


                Row(Modifier.fillMaxSize().gap(2.cssRem).padding(3.px).background(Colors.Transparent), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Start){}

                Column(Modifier.fillMaxSize().padding(15.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                })) {
                    Row(Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically) {
                        Row(Modifier.fontSize(1.4.cssRem).gap(2.cssRem)){
                            Div {
                                SpanText(
                                    video.title, Modifier.color(
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
                                Link("/creator/${video.creator.id}", video.creator.username, Modifier.color(sitePalette.brand.accent))
                            }
                        }

                        Spacer()
                        Row(Modifier.fontSize(1.1.cssRem).gap(2.cssRem), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                            SpanText(
                                "Uploaded: ${Date(video.uploadDate).toLocaleDateString()}", Modifier.color(
                                    when (ColorMode.current) {
                                        ColorMode.LIGHT -> Colors.Black
                                        ColorMode.DARK -> Colors.White
                                    }
                                )
                            )

                            SpanText(
                                "Views: ${video.viewCount}", Modifier.color(
                                    when (ColorMode.current) {
                                        ColorMode.LIGHT -> Colors.Black
                                        ColorMode.DARK -> Colors.White
                                    }
                                )
                            )
                            Row(Modifier.fontSize(1.1.cssRem), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                                SpanText(
                                    "Rating: " + video.rating.asDynamic().toFixed(1) + "/5", Modifier.color(
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
                        Button(onClick = {
                            // Change this click handler with your call-to-action behavior
                            // here. Link to an order page? Open a calendar UI? Play a movie?
                            // Up to you!
                            pageCtx.router.tryRoutingTo("/creator")
                        }, Modifier.color(sitePalette.brand.accent)) {
                            Text("Follow")
                        }
                        Column(Modifier.width(1.cssRem)){  }
                        SavePopUp(
                            onPlaylistAction = { playlist ->
                                // Perform action with playlist
                                coroutineScope.launch{
                                    newPlaylist = postRequest<NewPlaylistData,PlaylistDisplayResponse>(
                                        urlString = "playlists",
                                        setHeaders = {
                                            append(headerAccountId, window.localStorage["id"] ?: "")
                                            append(headerToken, window.localStorage["token"] ?: "")
                                        },
                                        setBody = {
                                            NewPlaylistData(
                                                name = playlist
                                            )
                                        },
                                        onSuccess = {
                                            println("Playlist submitted: ${it.name}")

                                        }
                                    )
                                }

                                newPlaylist
                            },
                            onCheckboxAction = { selectedItem ->
                                // Perform action with selected checkbox item
                                when (selectedItem){
                                    is PlaylistDisplayResponse ->{
                                        coroutineScope.launch{
                                            postRequest<String>(
                                                urlString = "playlists/${selectedItem.id}/add",
                                                setHeaders = {
                                                    append(headerAccountId, window.localStorage["id"] ?: "")
                                                    append(headerToken, window.localStorage["token"] ?: "")
                                                    append(headerVideoId, id.toString())
                                                },
                                                onSuccess = {
                                                    println(it)

                                                }
                                            )
                                        }
                                    }
                                    else -> {
                                    }
                                }
                            }
                        )

                    }
                }


                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(15.px).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {
                    Column(Modifier.fillMaxSize().gap(1.cssRem).fontSize(1.1.cssRem).height(6.cssRem).overflow { y(Overflow.Auto)}, horizontalAlignment = Alignment.Start) {

                        for(tag in video.tags) {

                            Link(
                                "/tags/${tag.id}",
                                tag.tag, Modifier.color(sitePalette.brand.primary)
                            )
                        }
                        Div{
                            SpanText(
                                video.description, Modifier.color(
                                    when (ColorMode.current) {
                                        ColorMode.LIGHT -> Colors.Black
                                        ColorMode.DARK -> Colors.White
                                    }
                                )
                            )
                        }


                    }

                }

                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)){}


                Row(Modifier.fillMaxSize().padding(15.px).fontSize(1.1.cssRem).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }), horizontalArrangement = Arrangement.Start) {
                    Column(Modifier.fillMaxSize().gap(1.cssRem).height(8.cssRem).overflow { y(Overflow.Auto)}){
                        CommentFunc { comment ->
                            // Do something with the comment
                            println("Comment submitted: $comment")
                        }

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

                    Box(Modifier.fillMaxSize().padding(2.cssRem).height(50.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                        Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                            for (index in 1..25) {
                                VideoTile(onClick = { pageCtx.router.tryRoutingTo("/video") }) {
                                    SpanText("Title\n")
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





