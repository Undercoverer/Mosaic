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
import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.SearchVideoTile
import gay.extremist.mosaic.data_models.AccountDisplayResponse
import gay.extremist.mosaic.data_models.VideoDisplayResponse
import gay.extremist.mosaic.toSitePalette
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page("/creator/{id}")
@Composable
fun CreatorPage() {
    val pageCtx = rememberPageContext()
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

    LaunchedEffect(id) {
        account = Json.decodeFromString(
            CLIENT.get("accounts/$id").bodyAsText()
        )
        videoList = Json.decodeFromString(
            CLIENT.get("accounts/$id/videos").bodyAsText()
        )
    }

    PageLayout("Creator"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary).height(20.cssRem).width(25.cssRem)) {  }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                val ctx = rememberPageContext()
                SpanText(
                    text = "${account.username.replaceFirstChar { it.uppercase() }}'s Videos",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                Button(onClick = {
                    // Change this click handler with your call-to-action behavior
                    // here. Link to an order page? Open a calendar UI? Play a movie?
                    // Up to you!
                    ctx.router.tryRoutingTo("/creator")
                }, Modifier.background(Color.rgb(0x2EB4A9))) {
                    Text("Follow")
                }
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        for(video in videoList) {
                            SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/video/${video.id}") }) {
                                P { Text("${video.title}\n") }
                            }
                        }
                    }

                }
            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).height(30.cssRem).width(25.cssRem)) {  }

        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}








