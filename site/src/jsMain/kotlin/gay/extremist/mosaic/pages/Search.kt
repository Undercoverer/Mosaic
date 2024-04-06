package gay.extremist.mosaic.pages

import androidx.compose.runtime.Composable
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
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.components.style.ComponentStyle
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.components.layouts.PageLayout
import gay.extremist.mosaic.components.widgets.FilterWidget
import gay.extremist.mosaic.components.widgets.SearchVideoTile
import gay.extremist.mosaic.toSitePalette
import org.jetbrains.compose.web.css.cssRem

val SearchContainerStyle by ComponentStyle {
    base { Modifier.fillMaxWidth().gap(10.cssRem) }

}

@Page("/search")
@Composable
fun SearchPage() {

    PageLayout("Search") {
        val sitePalette = ColorMode.current.toSitePalette()
        Row(
            modifier = Modifier.fillMaxSize().gap(1.cssRem).fontSize(1.1.cssRem),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize().background(
                    when (ColorMode.current) {
                        ColorMode.LIGHT -> Colors.LightGray
                        ColorMode.DARK -> Color.rgb(0x2B2B2B)
                    }
                ), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(Modifier.fillMaxSize().padding(2.cssRem).height(40.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter) {
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
            Column(
                modifier = Modifier.fillMaxSize().background(sitePalette.brand.accent).width(35.cssRem).background(
                    when (ColorMode.current) {
                        ColorMode.LIGHT -> Colors.LightGray
                        ColorMode.DARK -> Color.rgb(0x2B2B2B)
                    }
                ), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
            ) {



                val sortOptions = listOf("Option 1", "Option 2", "Option 3")
                val presetTags = listOf(
                    "Tab 1" to listOf("Tag 1", "Tag 2", "Tag 3", "Tag 4", "Tag 5"),
                    "Tab 2" to listOf("Tag 6", "Tag 7", "Tag 8", "Tag 9", "Tag 10"),
                    "Tab 3" to listOf("Tag 11", "Tag 12", "Tag 13")
                )

                FilterWidget(
                    sortOptions = sortOptions,
                    presetTags = presetTags,
                    onAction = { checkedSortOptions, checkedPresetTags ->
                        // Do something with the selected sort options and preset tags
                        // For example:
                        println("Sort options: $checkedSortOptions")
                        println("Preset tags: $checkedPresetTags")
                    }
                )

            }

        }
        Row(modifier = Modifier.fillMaxSize().height(3.cssRem)) {}

    }
}