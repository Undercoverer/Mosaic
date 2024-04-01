package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.silk.components.disclosure.Tabs
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text


@Composable
fun CheckPopUp() {
    Tabs {
        TabPanel {
            Tab { Text("Tab 1") }; Panel {
                Column(Modifier.gap(1.cssRem)) {


                    Row(Modifier.gap(1.cssRem), verticalAlignment = Alignment.CenterVertically) {
                        listOf(ColorSchemes.LightBlue).forEach { colorScheme ->
                            var checked by remember { mutableStateOf(true) }
                            Checkbox(
                                checked,
                                onCheckedChange = { checked = it },
                                colorScheme = colorScheme
                            ) { Text("Checkbox") }
                        }
                    }

                }
            }
        }
        TabPanel {
            Tab { Text("Tab 2") }; Panel { Text("Panel 2") }
        }
        TabPanel {
            Tab { Text("Tab 3") }; Panel { Text("Panel 3") }
        }
    }

}