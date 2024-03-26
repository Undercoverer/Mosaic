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
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text

@Page("/tags")
@Composable
fun TagPage() {
    PageLayout("Tag"){
        val sitePalette = ColorMode.current.toSitePalette()
        Row(modifier = Modifier.fillMaxSize().gap(1.cssRem)){
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.secondary).height(18.cssRem).width(10.cssRem)) {  }

            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).height(25.cssRem).width(10.cssRem)) {  }
            Column(modifier = Modifier.fillMaxSize().background(sitePalette.brand.primary), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    MoonIcon()
                }
                P(attrs = Modifier.fontSize(40.px).toAttrs()){
                    Text(value = "Videos of Tag")
                }
            }
        }
    }
}








