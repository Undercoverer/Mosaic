package gay.extremist.mosaic.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.file.readBytes
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.Util.getRequest
import gay.extremist.mosaic.Util.headerAccountId
import gay.extremist.mosaic.Util.headerToken
import gay.extremist.mosaic.Util.postRequest
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.AccountInfo
import gay.extremist.mosaic.components.widgets.UploadDataEntry
import gay.extremist.mosaic.data_models.*
import gay.extremist.mosaic.toSitePalette
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.w3c.dom.get

@Page("/account")
@Composable
fun AccountPage() {
    val pageCtx = rememberPageContext()
    val coroutineScope = rememberCoroutineScope()

    var playlists by remember {
        mutableStateOf(listOf<PlaylistDisplayResponse>())
    }

    coroutineScope.launch {
        playlists = getRequest<List<PlaylistDisplayResponse>>(
            urlString = "accounts/${window.localStorage["id"]}/playlists",
            onError = {
                println(it.message)
            }
        ) ?: playlists
    }

    PageLayout("Account"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().fontSize(1.3.cssRem).background(sitePalette.brand.secondary).padding(2.cssRem), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "Playlists",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )

                Box(Modifier.fillMaxSize().width(20.cssRem).height(35.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(0.2.cssRem).fillMaxSize()){
                        for (playlist in playlists) {
                            Link("/playlist/${playlist.id}", playlist.name, Modifier.color(Colors.DarkBlue))
                        }

                    }

                }



            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).padding(2.cssRem), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

                SpanText(
                    text = "Account Info",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )


                AccountInfo {email, username, password, confirmPassword ->
                    println("Email: $email")
                    println("Username: $username")
                    println("Password: $password")
                    println("Confirm Password: $confirmPassword")

                }

            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).padding(2.cssRem), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "Upload",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )


                //use println to print to database
                UploadDataEntry{ title, description, userTags, checkedItems, file ->
                    coroutineScope.launch {
                        val fileBytes = file?.readBytes() ?: byteArrayOf()
                        postRequest<MultiPartFormDataContent, Int>(
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
                                    },
                                    boundary = "WebAppBoundary"
                                )
                            },
                            onSuccess = {
                                println(it)
                            },
                            onError = {
                                println(it.message)
                            }
                        )
                    }
                }


            }
        }

        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}


    }

}