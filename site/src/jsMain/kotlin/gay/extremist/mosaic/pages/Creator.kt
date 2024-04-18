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
import gay.extremist.mosaic.data_models.AccountDisplayResponse
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get

@Page("/creator/{id}")
@Composable
fun CreatorPage() {
    val pageCtx = rememberPageContext()
    val coroutineScope = rememberCoroutineScope()
    val sitePalette = ColorMode.current.toSitePalette()
    val id = pageCtx.route.params.getValue("id").toIntOrNull() ?: return
    val loadingVal = "Loading..."

    var account by remember {
        mutableStateOf(
            AccountDisplayResponse(
                id = -1,
                username = loadingVal
            )
        )
    }

    var videoList by remember {
        mutableStateOf(
            listOf<VideoDisplayResponse>()
        )
    }

    var followedAccounts by remember {
        mutableStateOf(
            listOf<AccountDisplayResponse>()
        )
    }


    PageLayout("Creator"){
        LaunchedEffect(id) {
            account = getRequest<AccountDisplayResponse>(
                urlString = "accounts/$id/creator",
                onError = {
                    println(it.message)
                }
            ) ?: account

            videoList = getRequest<List<VideoDisplayResponse>>(
                urlString = "accounts/$id/videos",
                onError = {
                    println(it.message)
                }
            ) ?: videoList

            followedAccounts = getRequest<List<AccountDisplayResponse>>(
                urlString = "accounts/${window.localStorage["id"]}/following",
                setHeaders = {
                    append(headerToken, window.localStorage["token"] ?: "")
                },
                onError = {
                    println(it.message)
                }
            ) ?: followedAccounts
        }

        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary).height(20.cssRem).width(25.cssRem)) {  }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                val ctx = rememberPageContext()
                SpanText(
                    text = "${account.username.replaceFirstChar { it.uppercase() }}'s Videos",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                key(followedAccounts){
                    if (id != window.localStorage["id"]?.toInt() && account !in followedAccounts) {
                        Button(onClick = {
                            coroutineScope.launch{
                                postRequestText(
                                    urlString = "accounts/$id/follow",
                                    setHeaders = {
                                        append(headerAccountId, window.localStorage["id"] ?: "")
                                        append(headerToken, window.localStorage["token"] ?: "")
                                    },
                                    onSuccess = {
                                        println(it)
                                        followedAccounts += account
                                    },
                                    onError = {
                                        println(it.message)
                                    }
                                )
                            }
                        }, Modifier.background(Color.rgb(0x2EB4A9))) {
                            Text("Follow")
                        }
                    } else if (account in followedAccounts)  {
                        Button(onClick = {
                            coroutineScope.launch{
                                postRequestText(
                                    urlString = "accounts/$id/unfollow",
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
                                    }
                                )

                            }
                        }, Modifier.background(Color.rgb(0x2EB4A9))) {
                            Text("Unfollow")
                        }
                    }
                }
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        for(video in videoList) {
                            SearchVideoTile(
                                onClick = { ctx.router.tryRoutingTo("/video/${video.id}") },
                                video
                            )
                        }
                        ifVideosEmpty(videoList, "Some People Just Aren't The Sharing Type")
                    }

                }
            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).height(30.cssRem).width(25.cssRem)) {  }

        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}