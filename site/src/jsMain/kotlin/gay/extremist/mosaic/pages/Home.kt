

package gay.extremist.mosaic.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.FontSize
import com.varabyte.kobweb.compose.css.FontWeight
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.TextAlign
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
import gay.extremist.mosaic.Util.getRequest
import gay.extremist.mosaic.Util.headerToken
import gay.extremist.mosaic.Util.ifVideosEmpty
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.VideoTile
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.w3c.dom.get


@Page("/home")
@Composable
fun HomePage() {
    PageLayout("Home"){
        val pageCtx = rememberPageContext()
        val coroutineScope = rememberCoroutineScope()
        val sitePalette = ColorMode.current.toSitePalette()

        var creatorsRecommendations by remember {
            mutableStateOf(
                listOf<VideoDisplayResponse>()
            )
        }

        var generalRecommendations by remember {
            mutableStateOf(
                listOf<VideoDisplayResponse>()
            )
        }

        var tagsRecommendations by remember {
            mutableStateOf(
                listOf<VideoDisplayResponse>()
            )
        }


        coroutineScope.launch {
            creatorsRecommendations = getRequest<List<VideoDisplayResponse>>(
                urlString = "accounts/${window.localStorage["id"]}/recommended-videos",
                setQueryParameters = {
                    parameters.append("by", "following")
                },
                setHeaders = {
                    append(headerToken, window.localStorage["token"] ?: "")
                },
                onError = {
                    println(it.message)
                }
            ) ?: creatorsRecommendations

            generalRecommendations = getRequest<List<VideoDisplayResponse>>(
                urlString = "videos",
                onError = {
                    println(it.message)
                }
            ) ?: generalRecommendations


            tagsRecommendations = getRequest<List<VideoDisplayResponse>>(
                urlString = "accounts/${window.localStorage["id"]}/recommended-videos",
                setQueryParameters = {
                    parameters.append("by", "tags")
                },
                setHeaders = {
                    append(headerToken, window.localStorage["token"] ?: "")
                },
                onError = {
                    println(it.message)
                }
            ) ?: tagsRecommendations
        }

        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.secondary),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "Followed Creators",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        for (video in creatorsRecommendations) {
                            VideoTile(
                                onClick = { pageCtx.router.tryRoutingTo("/video/${video.id}") },
                                video
                            )
                        }
                        ifVideosEmpty(creatorsRecommendations, "Follow More Creators To See More Videos")
                    }
                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.accent),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "General",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        for (video in generalRecommendations) {
                            VideoTile(
                                onClick = { pageCtx.router.tryRoutingTo("/video/${video.id}") },
                                video = video
                            )
                        }
                        ifVideosEmpty(generalRecommendations, "Huh, This One Isn't Supposed To Be Empty...")
                    }

                }
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.primary),
                verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "Followed Tags",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        for (video in tagsRecommendations) {
                            VideoTile(
                                onClick = { pageCtx.router.tryRoutingTo("/video/${video.id}") },
                                video
                            )
                        }
                        ifVideosEmpty(tagsRecommendations, "Follow More Tags To See More Videos")
                    }
                }
            }
        }

        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}


    }

}