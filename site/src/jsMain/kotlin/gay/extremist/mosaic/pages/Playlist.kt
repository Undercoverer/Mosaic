package gay.extremist.mosaic.pages


import androidx.compose.runtime.*
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
import gay.extremist.mosaic.Util.*
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.EditPlaylistFunc
import gay.extremist.mosaic.components.widgets.PlaylistVideoTile
import gay.extremist.mosaic.data_models.NewPlaylistData
import gay.extremist.mosaic.data_models.PlaylistResponse
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.w3c.dom.get


@Page("/playlist/{id}")
@Composable
fun PlaylistPage() {
    val pageCtx = rememberPageContext()
    val coroutineScope = rememberCoroutineScope()
    val id = pageCtx.route.params.getValue("id").toIntOrNull() ?: return
    val loadingVal = "Loading..."

    PageLayout("Playlist"){

        var videoList by remember {
            mutableStateOf<List<VideoDisplayResponse>>(emptyList())
        }

        var newTitle by remember { mutableStateOf(loadingVal) }

        var playlist by remember {
            mutableStateOf(
                PlaylistResponse(
                    id = -1,
                    name = newTitle,
                    owner = -1,
                    videos = videoList
                )
            )
        }


        LaunchedEffect(id){
            playlist = getRequest<PlaylistResponse>(
                urlString = "playlists/$id",
                onSuccess = {
                    videoList = it.videos
                    newTitle = it.name
                },
                onError = {
                    println(it.message)
                }
            ) ?: playlist
            if(playlist.owner != window.localStorage["id"]?.toInt()){
                pageCtx.router.tryRoutingTo("/account")
            }
        }


        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    SpanText(
                        text = newTitle,
                        modifier = Modifier.padding(20.px).fontSize(35.px),
                    )

                    EditPlaylistFunc(
                        onSubmit = {submittedTitle ->
                            coroutineScope.launch {
                                putRequest<NewPlaylistData>(
                                    urlString = "playlists/$id",
                                    setHeaders = {
                                        append(headerAccountId, window.localStorage["id"] ?: "")
                                        append(headerToken, window.localStorage["token"] ?: "")
                                    },
                                    setBody = {
                                        NewPlaylistData(
                                            name = submittedTitle
                                        )
                                    },
                                    onSuccess = {
                                        newTitle = submittedTitle
                                    }
                                )
                            }
                        },
                        onDelete = {
                            coroutineScope.launch {
                                deleteRequest(
                                    urlString = "playlists/$id",
                                    setHeaders = {
                                        append(headerAccountId, window.localStorage["id"] ?: "")
                                        append(headerToken, window.localStorage["token"] ?: "")
                                    },
                                    onSuccess = {
                                        pageCtx.router.tryRoutingTo("/account")
                                    },
                                    onError = {
                                        println(it.message)
                                    }
                                )
                            }
                        }
                    )
                }

                Box(
                    Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) },
                    Alignment.TopCenter
                ) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()) {
                        for (video in videoList) {
                            PlaylistVideoTile(
                                video = video,
                                onClick = { pageCtx.router.tryRoutingTo("/video/${video.id}") },
                                onDelete = {
                                    coroutineScope.launch {
                                        postRequestText(
                                            urlString = "playlists/$id/remove",
                                            setHeaders = {
                                                append(headerAccountId,window.localStorage["id"] ?: "")
                                                append(headerToken,window.localStorage["token"] ?: "")
                                                append(headerVideoId, "${video.id}")
                                            },
                                            onSuccess = {
                                                println(it)
                                                videoList -= video
                                            }
                                        )
                                    }
                                }
                            )
                        }
                        ifVideosEmpty(videoList, "You Should Save More Videos, It's Lonely In Here")
                    }
                }
            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).height(20.cssRem).width(25.cssRem)) {  }

            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).height(30.cssRem).width(25.cssRem)) {  }

        }
        Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).height(20.cssRem).width(25.cssRem)) { }

        Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).height(30.cssRem).width(25.cssRem)) { }

        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) { }

    }
}








