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
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.Util.getRequest
import gay.extremist.mosaic.Util.ifVideosEmpty
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.FilterWidget
import gay.extremist.mosaic.components.widgets.SearchVideoTile
import gay.extremist.mosaic.data_models.Category
import gay.extremist.mosaic.data_models.ErrorResponse
import gay.extremist.mosaic.data_models.TagCategorizedResponse
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import gay.extremist.mosaic.toSitePalette
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem

val SearchContainerStyle by ComponentStyle {
    base { Modifier.fillMaxWidth().gap(10.cssRem) }

}

@Page("/search")
@Composable
fun SearchPage() {

    PageLayout("Search") {
        val sitePalette = ColorMode.current.toSitePalette()
        val pageCtx = rememberPageContext()
        val coroutineScope = rememberCoroutineScope()
        val query = pageCtx.route.params.getValue("q")

        var presetTags by remember {
            mutableStateOf(
                TagCategorizedResponse(
                    listOf(
                        Category(
                            "Loading...",
                            emptyList()
                        )
                    )
                )
            )
        }

        var videoList by remember {
            mutableStateOf(listOf<VideoDisplayResponse>())
        }

        var sortBy by remember { mutableStateOf<String?>(null) }

        coroutineScope.launch{
            val responseBody = CLIENT.get("tags/preset").bodyAsText()
            val response = runCatching {
                Json.decodeFromString<TagCategorizedResponse>(responseBody)
            }.recoverCatching {
                Json.decodeFromString<ErrorResponse>(responseBody)
            }.getOrNull()

            when(response){
                is TagCategorizedResponse -> {
                    presetTags = response
                }

                is ErrorResponse -> {
                    println(response.message)
                    // TODO add error handling for video
                }

                null -> {
                    // TODO idk
                }

            }

            videoList = getRequest<List<VideoDisplayResponse>>(
                urlString = "videos/search",
                setQueryParameters = {
                    parameters.append("title", query)
                },
                onError = {
                    println(it.message)
                }
            ) ?: videoList
        }


        Row(
            modifier = Modifier.fillMaxSize().gap(1.cssRem).fontSize(1.1.cssRem),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(
                    when (ColorMode.current) {
                        ColorMode.LIGHT -> Colors.LightGray
                        ColorMode.DARK -> Color.rgb(0x2B2B2B)
                    }
                ), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(40.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        for (video in videoList) {
                            SearchVideoTile(onClick = { pageCtx.router.tryRoutingTo("/video/${video.id}") }, video)
                        }
                        ifVideosEmpty(videoList, "Hmm... Maybe Try That Again")
                    }

                }


            }
            Column(
                modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).width(35.cssRem).background(
                    when (ColorMode.current) {
                        ColorMode.LIGHT -> Colors.LightGray
                        ColorMode.DARK -> Color.rgb(0x2B2B2B)
                    }
                ), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {



                val sortOptions = listOf("Upload Date", "Views", "Rating")

                FilterWidget(
                    sortOptions = sortOptions,
                    presetTags = presetTags,
                    onAction = { checkedSortOption, checkedPresetTags ->
                        // Do something with the selected sort options and preset tags
                        // For example:
                        println("Sort option: $checkedSortOption")
                        println("Preset tags: ${checkedPresetTags.joinToString(separator = ",")}")
                        when (checkedSortOption) {
                            "Upload Date" -> {
                                sortBy = "date"
                            }
                            "Views" -> {
                                sortBy = "views"
                            }
                            "Rating" -> {
                                sortBy = "rating"
                            }
                        }
                        coroutineScope.launch {
                            videoList = getRequest<List<VideoDisplayResponse>>(
                                urlString = "videos/search",
                                setQueryParameters = {
                                    parameters.append("title", query)
                                    when(checkedPresetTags.isNotEmpty()){
                                        true -> {
                                            parameters.append("tags", checkedPresetTags.joinToString(separator = ","))
                                        }
                                        false -> {}
                                    }
                                    when (sortBy) {
                                        is String -> {
                                            parameters.append("sortBy", sortBy!!)
                                        }
                                        null -> {}
                                    }

                                },
                                onError = {
                                    println(it.message)
                                }
                            ) ?: videoList
                        }
                    }
                )

            }

        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}