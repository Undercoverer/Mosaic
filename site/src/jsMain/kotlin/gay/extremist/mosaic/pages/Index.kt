package gay.extremist.mosaic.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.UnsignInPageLayout
import gay.extremist.mosaic.components.widgets.RegisterFunc
import gay.extremist.mosaic.components.widgets.SignInFunc
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px


@Page("")
@Composable
fun SignInPage() {
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
                .background(sitePalette.brand.accent).width(35.cssRem).padding(2.cssRem),
                verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

                Column (
                    modifier = Modifier.fillMaxWidth().gap(1.cssRem),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    SpanText(
                        text = "Sign In",
                        modifier = Modifier.padding(8.px).fontSize(35.px),
                    )


                    SignInFunc { username, password ->
                        println("Username: $username")
                        println("Password: $password")
                    }
                }

                Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}
                Column (
                    modifier = Modifier.fillMaxWidth().gap(1.cssRem),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally

                ) {
                    SpanText(
                        text = "Register",
                        modifier = Modifier.padding(8.px).fontSize(35.px),
                    )

                    RegisterFunc{email, username, password ->
                        println("Username: $email")
                        println("Username: $username")
                        println("Password: $password")
                    }
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

        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }

}