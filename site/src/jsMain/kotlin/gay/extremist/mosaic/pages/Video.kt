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
import com.varabyte.kobweb.silk.components.overlay.*
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.BASE_URL
import gay.extremist.mosaic.Util.*
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.*
import gay.extremist.mosaic.data_models.*
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.FlexWrap
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

    var tooltipText by remember {
        mutableStateOf("")
    }

    var toggle by remember { mutableStateOf(false) }

    var video by remember {
        mutableStateOf(
            VideoResponse(
                id = -1,
                title = loadingVal,
                description = loadingVal,
                videoPath = loadingVal,
                tags = listOf(),
                creator = AccountDisplayResponse(
                    id = -1, username = loadingVal
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
    var newComment by remember {
        mutableStateOf<CommentResponseNonrecursive?>(null)
    }
    var responseText by remember {
        mutableStateOf<String?>(null)
    }

    var followedAccounts by remember {
        mutableStateOf(
            listOf<AccountDisplayResponse>()
        )
    }

    var comments by remember {
        mutableStateOf(
            listOf<CommentResponseNonrecursive>()
        )
    }

    var recommends by remember {
        mutableStateOf(
            listOf<VideoDisplayResponse>()
        )
    }

    var isDeleteVisible by remember {
        mutableStateOf(
            false
        )
    }

    val videoRating = remember { mutableStateOf<Double?>(0.0) }


    PageLayout("Video") {

        coroutineScope.launch {
            video = getRequest<VideoResponse>(
                urlString = "videos/$id",
                onError = {
                    println(it.message)
                }
            ) ?: video

            followedAccounts = getRequest<List<AccountDisplayResponse>>(
                urlString = "accounts/${window.localStorage["id"]}/following",
                setHeaders = {
                    append(headerToken, window.localStorage["token"] ?: "")
                },
                onError = {
                    println(it.message)
                }
            ) ?: followedAccounts

            comments = getRequest<List<CommentResponseNonrecursive>>(
                urlString = "comments/video/$id",
                onError = {
                    println(it.message)
                }
            ) ?: comments

            recommends = getRequest<List<VideoDisplayResponse>>(
                urlString = "accounts/${video.creator.id}/videos",
                onError = {
                    println(it.message)
                }
            ) ?: recommends
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


                Row(
                    Modifier.fillMaxSize().gap(2.cssRem).padding(3.px).background(Colors.Transparent),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {}

                Column(
                    Modifier.fillMaxSize().padding(15.px).background(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.LightGray
                            ColorMode.DARK -> Color.rgb(0x2B2B2B)
                        }
                    )
                ) {
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(Modifier.fontSize(1.4.cssRem).gap(2.cssRem)) {
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
                    Row(
                        Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            Modifier.fontSize(1.4.cssRem).gap(2.cssRem),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Div {//temp color
                                Link(
                                    "/creator/${video.creator.id}",
                                    video.creator.username,
                                    Modifier.color(sitePalette.brand.accent)
                                )
                            }
                        }

                        Spacer()
                        Row(
                            Modifier.fontSize(1.1.cssRem).gap(2.cssRem),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

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


                            Row(
                                Modifier.fontSize(1.1.cssRem),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                key("rating", videoRating.value) {
                                    SpanText(
                                        "Rating: " + (if (videoRating.value != null) videoRating.value.asDynamic()
                                            .toFixed(1) else "0.0") + "/5", Modifier.color(
                                            when (ColorMode.current) {
                                                ColorMode.LIGHT -> Colors.Black
                                                ColorMode.DARK -> Colors.White
                                            }
                                        )
                                    )
                                }
                                RatingFunc(video, videoRating, onUpdated = {
                                    toggle = true
                                }, onAdded = {
                                    toggle = true
                                })
                            }
                        }

                        Spacer()
                        key(followedAccounts) {
                            if (video.creator in followedAccounts) {
                                Button(onClick = {
                                    coroutineScope.launch {
                                        postRequestText(urlString = "accounts/${video.creator.id}/unfollow",
                                            setHeaders = {
                                                append(headerAccountId, window.localStorage["id"] ?: "")
                                                append(headerToken, window.localStorage["token"] ?: "")
                                            },
                                            onSuccess = {
                                                println(it)
                                                followedAccounts = emptyList()
                                            },
                                            onError = {
                                                println(it.message)
                                            })

                                    }
                                }, Modifier.color(sitePalette.brand.accent)) {
                                    Text("Unfollow")
                                }
                            } else if (video.creator.id == window.localStorage["id"]?.toInt()) {
                                Button(
                                    onClick = {
                                        isDeleteVisible = !isDeleteVisible
                                    }, Modifier.background(Color.rgb(0x2EB4A9))
                                ) {
                                    Text("Delete")
                                }
                                if (isDeleteVisible) {
                                    Popover(target = ElementTarget.PreviousSibling,
                                        modifier = Modifier.padding(2.cssRem).background(
                                            when (ColorMode.current) {
                                                ColorMode.LIGHT -> Colors.LightGray
                                                ColorMode.DARK -> Color.rgb(0x2B2B2B)
                                            }
                                        ),
                                        placement = PopupPlacement.TopRight,
                                        keepOpenStrategy = KeepPopupOpenStrategy.manual(true),
                                        content = {
                                            Column(
                                                Modifier.fillMaxWidth().gap(1.cssRem),
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                SpanText("Are you sure?")
                                                Button(
                                                    onClick = {
                                                        coroutineScope.launch {
                                                            deleteRequest(urlString = "videos/$id", setHeaders = {
                                                                append(
                                                                    headerAccountId, window.localStorage["id"] ?: ""
                                                                )
                                                                append(
                                                                    headerToken, window.localStorage["token"] ?: ""
                                                                )
                                                            }, onSuccess = {
                                                                pageCtx.router.tryRoutingTo("/account")
                                                            }, onError = {
                                                                println(it.message)
                                                            })
                                                        }
                                                    },
                                                ) {
                                                    Text("Delete")
                                                }
                                            }
                                        })
                                }
                            } else {
                                Button(onClick = {
                                    coroutineScope.launch {
                                        postRequestText(urlString = "accounts/${video.creator.id}/follow",
                                            setHeaders = {
                                                append(headerAccountId, window.localStorage["id"] ?: "")
                                                append(headerToken, window.localStorage["token"] ?: "")
                                            },
                                            onSuccess = {
                                                println(it)
                                                followedAccounts += video.creator
                                            },
                                            onError = {
                                                println(it.message)
                                            })
                                    }
                                }, Modifier.background(Color.rgb(0x2EB4A9))) {
                                    Text("Follow")

                                }
                            }
                        }
                        Column(Modifier.width(1.cssRem)) { }
                        key(newPlaylist, responseText) {
                            SavePopUp(onPlaylistAction = { playlist ->
                                // Perform action with playlist
                                coroutineScope.launch {
                                    newPlaylist =
                                        postRequest<NewPlaylistData, PlaylistDisplayResponse>(urlString = "playlists",
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
                                                tooltipText = "Playlist ${newPlaylist!!.name} Created"
                                            })
                                }

                                newPlaylist
                            }, onCheckboxAction = { selectedItem ->
                                // Perform action with selected checkbox item
                                when (selectedItem) {
                                    is PlaylistDisplayResponse -> {
                                        coroutineScope.launch {
                                            responseText =
                                                postRequestText(urlString = "playlists/${selectedItem.id}/add",
                                                    setHeaders = {
                                                        append(headerAccountId, window.localStorage["id"] ?: "")
                                                        append(headerToken, window.localStorage["token"] ?: "")
                                                        append(headerVideoId, id.toString())
                                                    },
                                                    onSuccess = {
                                                        println(it)
                                                        tooltipText = "Video Added to ${newPlaylist!!.name}"
                                                    },
                                                    onError = {
                                                        println(it.message)
                                                    })
                                        }
                                    }

                                    else -> {
                                    }
                                }
                            })
                            if (tooltipText != "") {
                                AdvancedTooltip(
                                    ElementTarget.PreviousSibling,
                                    tooltipText,
                                    hideDelayMs = 2000,
                                    placementStrategy = PopupPlacementStrategy.of(PopupPlacement.Top)
                                )
                            }
                        }

                    }
                }


                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)) {}


                Row(
                    Modifier.fillMaxSize().padding(10.px).background(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.LightGray
                            ColorMode.DARK -> Color.rgb(0x2B2B2B)
                        }
                    ), horizontalArrangement = Arrangement.Start
                ) {
                    Column(
                        Modifier.fillMaxSize().gap(1.cssRem).fontSize(1.1.cssRem).height(6.cssRem)
                            .overflow { y(Overflow.Auto) }, horizontalAlignment = Alignment.Start
                    ) {

                        Div {
                            SpanText(
                                video.description, Modifier.color(
                                    when (ColorMode.current) {
                                        ColorMode.LIGHT -> Colors.Black
                                        ColorMode.DARK -> Colors.White
                                    }
                                )
                            )
                        }
                        Box(Modifier.fillMaxSize().height(15.cssRem)) {
                            Row(Modifier.width(58.cssRem).gap(0.5.cssRem).flexWrap(FlexWrap.Wrap)) {
                                for (tag in video.tags) {
                                    Link(
                                        "/tags/${tag.id}", tag.tag, Modifier.color(sitePalette.brand.primary)
                                    )
                                }
                            }
                        }
                    }

                }

                Row(Modifier.fillMaxSize().padding(3.px).background(Colors.Transparent)) {}


                Row(
                    Modifier.fillMaxSize().padding(10.px).fontSize(1.1.cssRem).background(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.LightGray
                            ColorMode.DARK -> Color.rgb(0x2B2B2B)
                        }
                    ), horizontalArrangement = Arrangement.Start
                ) {
                    key(comments) {
                        Box(
                            Modifier.fillMaxSize().padding(2.cssRem).height(15.cssRem).overflow { y(Overflow.Auto) },
                            Alignment.TopCenter
                        ) {
                            Column(Modifier.gap(1.cssRem).fillMaxSize()) {
                                CommentFunc { comment ->
                                    // Do something with the comment
                                    coroutineScope.launch {
                                        newComment =
                                            postRequest<String, CommentResponseNonrecursive>(urlString = "comments/video/$id",
                                                setHeaders = {
                                                    append(headerAccountId, window.localStorage["id"] ?: "")
                                                    append(headerToken, window.localStorage["token"] ?: "")
                                                },
                                                setBody = {
                                                    comment
                                                },
                                                onSuccess = {
                                                    println("comment successfully added")
                                                    comments += it
                                                })
                                    }

                                }

                                for (comment in comments) {
                                    CommentTile(comment)
                                }

                            }

                        }
                    }

                }

            }

            Column(
                Modifier.fillMaxSize().background(
                    when (ColorMode.current) {
                        ColorMode.LIGHT -> Colors.LightGray
                        ColorMode.DARK -> Color.rgb(0x2B2B2B)
                    }
                )
            ) {
                Column(
                    Modifier.fillMaxSize().padding(15.px).background(
                        when (ColorMode.current) {
                            ColorMode.LIGHT -> Colors.LightGray
                            ColorMode.DARK -> Color.rgb(0x2B2B2B)
                        }
                    ), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Top
                ) {

                    Box(
                        Modifier.fillMaxSize().padding(2.cssRem).height(60.cssRem).overflow { y(Overflow.Auto) },
                        Alignment.TopCenter
                    ) {
                        Column(Modifier.gap(1.cssRem).fillMaxSize()) {
                            for (recommend in (recommends.filter { video.id != it.id })) {
                                if (id != recommend.id) {
                                    VideoTile(
                                        onClick = { pageCtx.router.tryRoutingTo("/video/${recommend.id}") }, recommend
                                    )
                                }
                            }
                            ifVideosEmpty(recommends, "Looks Like This Creator Doesn't have Many Videos")
                        }

                    }

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}


        if(toggle){
//            coroutineScope.launch {
//                video = getRequest<VideoResponse>(
//                    urlString = "videos/$id",
//                    onError = {
//                        println(it.message)
//                    }
//                ) ?: video
//            }
            toggle = !toggle
        }

    }
}





