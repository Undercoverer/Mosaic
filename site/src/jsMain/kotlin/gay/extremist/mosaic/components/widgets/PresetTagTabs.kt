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
import androidx.compose.runtime.*

@Composable
fun PresetTagTabs(
    tabTags: List<Pair<String, List<String>>>,
    onCheckedItemsChanged: (List<String>) -> Unit // Callback function to pass checked items
) {
    val checkedItems = remember { mutableStateOf<List<String>>(emptyList()) }

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
                                    checked = checkedItems.value.contains(tag), // Check if tag is in the checked items
                                    onCheckedChange = {
                                        checkedItems.value = if (it) {
                                            checkedItems.value + tag // Add tag to checked items
                                        } else {
                                            checkedItems.value - tag // Remove tag from checked items
                                        }
                                    },
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

    // Pass the list of checked items to the callback function
    onCheckedItemsChanged(checkedItems.value)
}



