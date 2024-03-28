package gay.extremist.mosaic.pages

//import androidx.compose.foundation.shape

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.compose.ui.toAttrs
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.UnsignInPageLayout
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Page()
@Composable
fun UnsignInRegisterPage() {
    UnsignInPageLayout("Sign in or Register"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem),  verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center){
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.secondary).height(25.cssRem).width(13.cssRem),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette.brand.accent).width(35.cssRem),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {


                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Sign In")
                }
                val ctx = rememberPageContext()

                Button(onClick = {
                    // Change this click handler with your call-to-action behavior
                    // here. Link to an order page? Open a calendar UI? Play a movie?
                    // Up to you!
                    ctx.router.tryRoutingTo("/home")
                }, Modifier.color(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.Black
                    ColorMode.DARK -> Colors.White
                }).background(Color.rgb(0x2EB4A9))) {
                    Text("Sign In")
                }
                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Register")

                }
                Button(onClick = {
                    // Change this click handler with your call-to-action behavior
                    // here. Link to an order page? Open a calendar UI? Play a movie?
                    // Up to you!
                    ctx.router.tryRoutingTo("/home")
                }, Modifier.color(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.Black
                    ColorMode.DARK -> Colors.White
                }).background(Color.rgb(0x2EB4A9))) {
                    Text("Register")
                }


            }
            Column(modifier = Modifier
                .fillMaxSize()
                .background(sitePalette
                    .brand.primary).height(17.cssRem).width(13.cssRem),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                }

            }
        }

        Row(modifier = Modifier.fillMaxSize().height(1.cssRem)) {}

    }

}