package gay.extremist.mosaic.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.Util.*
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.SearchVideoTile
import gay.extremist.mosaic.data_models.TagResponse
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get

@Page("/tags/{id}")
@Composable
fun TagPage() {
    val pageCtx = rememberPageContext()
    val coroutineScope = rememberCoroutineScope()
    val id = pageCtx.route.params.getValue("id").toIntOrNull() ?: return
    val loadingVal = "Loading..."

    var tag by remember {
        mutableStateOf(
            TagResponse(
                -1,
                loadingVal,
                loadingVal,
                false
            )
        )
    }

    var videoList by remember {
        mutableStateOf(
            listOf<VideoDisplayResponse>()
        )
    }

    var followedTags by remember {
        mutableStateOf(
            listOf<TagResponse>()
        )
    }

    LaunchedEffect(id) {
        tag = getRequest<TagResponse>(
            urlString = "tags/$id",
            onError = {
                println(it.message)
            }
        ) ?: tag

        videoList = getRequest<List<VideoDisplayResponse>>(
            urlString = "tags/$id/videos",
            onError = {
                println(it.message)
            }
        ) ?: videoList

        followedTags = getRequest<List<TagResponse>>(
            urlString = "accounts/${window.localStorage["id"]}/tags",
            setHeaders = {
                append(headerToken, window.localStorage["token"] ?: "")
            },
            onError = {
                println(it.message)
            }
        ) ?: followedTags
    }

    PageLayout("Tag"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary).height(20.cssRem).width(25.cssRem)) {  }

            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).height(30.cssRem).width(25.cssRem)) {  }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "Videos of ${tag.tag.capitalize()}",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                key(followedTags){
                    if (tag !in followedTags) {
                        Button(onClick = {
                            coroutineScope.launch{
                                postRequestText(
                                    urlString = "tags/$id/follow",
                                    setHeaders = {
                                        append(headerAccountId, window.localStorage["id"] ?: "")
                                        append(headerToken, window.localStorage["token"] ?: "")
                                    },
                                    onSuccess = {
                                        println(it)
                                        followedTags += tag
                                    },
                                    onError = {
                                        println(it.message)
                                    },
                                    onNull = {
                                        println("wrong return type dummy")
                                    }
                                )

                            }
                        }, Modifier.background(Color.rgb(0x2454BF))) {
                            Text("Follow")
                        }
                    } else {
                        Button(onClick = {
                            coroutineScope.launch{
                                postRequestText(
                                    urlString = "tags/$id/unfollow",
                                    setHeaders = {
                                        append(headerAccountId, window.localStorage["id"] ?: "")
                                        append(headerToken, window.localStorage["token"] ?: "")
                                    },
                                    onSuccess = {
                                        println(it)
                                         followedTags = emptyList()
                                    },
                                    onError = {
                                        println(it.message)
                                    },
                                    onNull = {
                                    }
                                )

                            }
                        }, Modifier.background(Color.rgb(0x2454BF))) {
                            Text("Unfollow")
                        }
                    }
                }
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        for (video in videoList) {
                            SearchVideoTile(
                                onClick = { pageCtx.router.tryRoutingTo("/video/${video.id}") },
                                video
                            )
                        }
                        ifVideosEmpty(videoList, "Not Many Videos For This Tag")
                    }

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}








