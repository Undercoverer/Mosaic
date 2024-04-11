package gay.extremist.mosaic.pages

import androidx.compose.runtime.Composable
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
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.AccountInfo
import gay.extremist.mosaic.components.widgets.UploadDataEntry
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px

@Page("/account")
@Composable
fun AccountPage() {
    PageLayout("Account"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().fontSize(1.3.cssRem).background(sitePalette.brand.secondary).padding(2.cssRem), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                val ctx = rememberPageContext()
                SpanText(
                    text = "Playlists",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )
                //SearchFunc()
                Box(Modifier.fillMaxSize().width(20.cssRem).height(35.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
                    Column(Modifier.gap(0.2.cssRem).fillMaxSize()){
                        val ctx = rememberPageContext()
                        for (index in 1..25) {
                            Link("/playlist", "Playlist " + index, Modifier.color(Colors.DarkBlue))
                        }

                    }

                }



            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).padding(2.cssRem), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {

                SpanText(
                    text = "Account Info",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )


                AccountInfo {email, username, password ->
                    println("Email: $email")
                    println("Username: $username")
                    println("Password: $password")

                }

            }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary).padding(2.cssRem), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
                val ctx = rememberPageContext()
                SpanText(
                    text = "Upload",
                    modifier = Modifier.padding(20.px).fontSize(35.px),
                )


                //use println to print to database
                UploadDataEntry{ title, description, userTags, checkedItems, file ->
                        // Perform action with the video data and checked items
                        println("Title: $title")
                        println("Description: $description")
                        println("User Tags: $userTags")
                        println("Checked Items: $checkedItems")
                        println("File: ${file?.name}")

                        // Example: Upload video data to server along with checked items
                        //uploadVideoData(videoUrl, title, description, userTags, checkedItems)
                }


            }
        }

        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}


    }

}