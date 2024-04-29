package gay.extremist.mosaic.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.browser.file.readBytes
import com.varabyte.kobweb.compose.css.FontSize
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
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.overlay.AdvancedTooltip
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.PopupPlacementStrategy
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.Util.getRequest
import gay.extremist.mosaic.Util.headerAccountId
import gay.extremist.mosaic.Util.headerToken
import gay.extremist.mosaic.Util.postRequest
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.AccountInfo
import gay.extremist.mosaic.components.widgets.UploadDataEntry
import gay.extremist.mosaic.data_models.NewPlaylistData
import gay.extremist.mosaic.data_models.PlaylistDisplayResponse
import gay.extremist.mosaic.toSitePalette
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get

@Page("/account")
@Composable
fun AccountPage() {
    val coroutineScope = rememberCoroutineScope()
    var uploadedVideoId by remember { mutableStateOf<Int?>(null) }

    var playlists by remember {
        mutableStateOf(listOf<PlaylistDisplayResponse>())
    }
    var playlistName by remember { mutableStateOf("") }


    coroutineScope.launch {
        playlists = getRequest<List<PlaylistDisplayResponse>>(
            urlString = "accounts/${window.localStorage["id"]}/playlists",
            onError = {
                println(it.message)
            }) ?: playlists
    }

    PageLayout("Account") {
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)) {
            Column(
                modifier = Modifier.fillMaxSize().fontSize(1.3.cssRem).background(sitePalette.brand.secondary)
                    .padding(2.cssRem),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpanText(
                    text = "Playlists",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                // New playlist button

                Row() {
                    InputGroup {
                        TextInput(
                            modifier = Modifier.fillMaxWidth(), text = playlistName, onTextChanged = {
                                playlistName = it
                            }, variant = FilledInputVariant,
                            placeholder = "New Playlist"
                        )
                        RightInset(width = 4.5.cssRem) {
                            Button(modifier = Modifier.width(3.5.cssRem).height(1.75.cssRem).background(Color.rgb(0x8269F8)),
                                size = ButtonSize.SM, onClick = {
                                coroutineScope.launch {
                                    postRequest<NewPlaylistData, PlaylistDisplayResponse>(
                                        urlString = "playlists",
                                        setHeaders = {
                                            append(headerAccountId, window.localStorage["id"] ?: "")
                                            append(headerToken, window.localStorage["token"] ?: "")
                                            append(HttpHeaders.ContentType, "application/json")
                                            append(HttpHeaders.Accept, "application/json")
                                        },
                                        setBody = {
                                            NewPlaylistData(
                                                name = playlistName
                                            )
                                        },
                                        onSuccess = {
                                            playlists = playlists + it
                                            playlistName = ""
                                        })
                                }
                            }) {

                               Text("Add")

                            }
                        }
                    }

                }
                Box(
                    Modifier.fillMaxSize().width(20.cssRem).height(35.cssRem).overflow { y(Overflow.Auto) },
                    Alignment.TopCenter
                ) {
                    Column(Modifier.gap(0.2.cssRem).fillMaxSize()) {
                        for (playlist in playlists) {
                            Link(
                                "/playlist/${playlist.id}",
                                playlist.name,
                                Modifier.color(Colors.DarkBlue).fontSize(FontSize.XLarge)
                            )
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).padding(2.cssRem),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpanText(
                    text = "Account Info",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                AccountInfo()
            }
            Column(
                modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).padding(2.cssRem),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SpanText(
                    text = "Upload",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )

                UploadDataEntry { title, description, userTags, checkedItems, file ->
                    coroutineScope.launch {
                        val fileBytes = file?.readBytes() ?: byteArrayOf()
                        uploadedVideoId = postRequest<MultiPartFormDataContent, Int>(
                            urlString = "videos",
                            setHeaders = {
                                append(headerAccountId, window.localStorage["id"] ?: "")
                                append(headerToken, window.localStorage["token"] ?: "")
                            },
                            setBody = {
                                MultiPartFormDataContent(
                                    formData {
                                        append("title", title)
                                        append("description", description)
                                        append("tags[]", (checkedItems + userTags))
                                        append("video", fileBytes, Headers.build {
                                            append(HttpHeaders.ContentType, "video/mp4")
                                            append(HttpHeaders.ContentDisposition, "filename=${file?.name}")
                                        })
                                    }, boundary = "WebAppBoundary"
                                )
                            },
                        )
                    }
                }

                ShowUploadSuccess(uploadedVideoId) {
                    uploadedVideoId = null
                }
            }
        }
    }
}

@Composable
fun ShowUploadSuccess(uploadedVideoId: Int?, onTimeout: () -> Unit) {
    when (uploadedVideoId != null) {
        true -> {
            AdvancedTooltip(
                ElementTarget.PreviousSibling,
                "Video Uploaded Successfully",
                placementStrategy = PopupPlacementStrategy.of(PopupPlacement.Top)
            )
            LaunchedEffect(key1 = uploadedVideoId) {
                delay(3000)
                onTimeout()
            }
        }

        false -> {}
    }
}