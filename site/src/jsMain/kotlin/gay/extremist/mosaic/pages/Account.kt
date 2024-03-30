package gay.extremist.mosaic.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.icons.ArrowUpIcon
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.IconButton
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Page("/account")
@Composable
fun AccountPage() {
    PageLayout("Account"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary).height(30.cssRem), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                val ctx = rememberPageContext()
                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Playlists")
                }
                //SearchFunc()
                Button(onClick = {
                    // Change this click handler with your call-to-action behavior
                    // here. Link to an order page? Open a calendar UI? Play a movie?
                    // Up to you!
                    ctx.router.tryRoutingTo("/playlist")
                },
                    ) {
                    Text("Playlist")
                    //colorScheme = ButtonDefaults.buttonColors(backgroundColor = Color.rgb(0x8269F8))
                }


            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MoonIcon()
                }
                P(attrs = Modifier.fontSize(30.px).toAttrs()){
                    Text(value = "Account Info")
                }

            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).height(30.cssRem), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                val ctx = rememberPageContext()
                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Upload")
                }
                IconButton(onClick = { ctx.router.tryRoutingTo("/playlist") },) {
                    ArrowUpIcon()
                }

                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Upload Video Input")
                }
            }
        }

        Row(modifier = Modifier.fillMaxSize().height(1.cssRem)) {}


    }

}