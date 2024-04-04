package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.components.disclosure.Tabs
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.forms.CheckboxSize
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun PresetTagTabs(tabTags: List<Pair<String, List<String>>>) {
    Tabs {
        tabTags.forEach { (tabTitle, tags) ->
            TabPanel {
                Tab { Text(tabTitle) }; Panel {
                    Column(Modifier.gap(0.2.cssRem)) {
                        val chunkedTags = tags.chunked(2) // Split the tags into chunks of 5

                        chunkedTags.forEach { chunk ->
                            Row(Modifier.gap(1.cssRem), verticalAlignment = Alignment.CenterVertically) {
                                chunk.forEach { tag ->
                                    Checkbox(
                                        checked = false, // Set the default checked state here
                                        onCheckedChange = { /* Handle checkbox state change here */ },
                                        colorScheme = ColorSchemes.LightBlue,
                                        size = CheckboxSize.LG
                                    ) {
                                        Text(tag)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


