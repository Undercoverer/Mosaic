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
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.SearchVideoTile
import gay.extremist.mosaic.data_models.TagResponse
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Page("/tags/{id}")
@Composable
fun TagPage() {
    val pageCtx = rememberPageContext()
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
                val ctx = rememberPageContext()
                Button(onClick = {
                    // Change this click handler with your call-to-action behavior
                    // here. Link to an order page? Open a calendar UI? Play a movie?
                    // Up to you!
                    ctx.router.tryRoutingTo("/creator")
                }, Modifier.background(Color.rgb(0x2454BF))) {
                    Text("Follow")
                }
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(33.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(1.cssRem).fontSize(1.2.cssRem).fillMaxSize()){
                        val ctx = rememberPageContext()
                        for (index in 1..25) {
                            SearchVideoTile(onClick = { ctx.router.tryRoutingTo("/video") }) {
                                SpanText("Title\n")
                            }
                        }

                    }

                }

            }
        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}








