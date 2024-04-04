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
import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.VideoTile
import gay.extremist.mosaic.data_models.Tag
import gay.extremist.mosaic.toSitePalette
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page("/tags/{id}")
@Composable
fun TagPage() {
    val pageCtx = rememberPageContext()
    val id = pageCtx.route.params.getValue("id").toIntOrNull() ?: return
    val loadingVal = "Loading..."

    var tag by remember {
        mutableStateOf(
            Tag(
                -1,
                loadingVal,
                loadingVal
            )
        )
    }

    // To Be Implemented, Waiting for Tag endpoints
    // For example of how this will work, check creator page
//    LaunchedEffect(id) {
//        tag = Json.decodeFromString(
//            CLIENT
//                .get("https://jsonplaceholder.typicode.com/users/$id")
//                .bodyAsText()
//        )
//    }

    PageLayout("Tag"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary).height(20.cssRem).width(25.cssRem)) {  }

            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).height(30.cssRem).width(25.cssRem)) {  }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                SpanText(
                    text = "Videos of ${tag.tag.uppercase()}",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fillMaxSize()){
                        val ctx = rememberPageContext()
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }
                        VideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                            P { Text("Title\n") }

                        }

                    }

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}








