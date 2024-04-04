package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.forms.CheckboxSize
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun FilterWidget(){


    Row(Modifier.gap(1.cssRem).fontSize(1.3.cssRem), verticalAlignment = Alignment.CenterVertically) {
        listOf(ColorSchemes.LightBlue).forEach { colorScheme ->
            var checked by remember { mutableStateOf(true) }
            Checkbox(
                checked,
                onCheckedChange = { checked = it },
                colorScheme = colorScheme,
                size = CheckboxSize.LG
            ) { Text("Checkbox") }
        }
    }

    Row(Modifier.height(1.cssRem)){}

    val tabTags = listOf(
        "Tab 1" to listOf("Tag 1", "Tag 2", "Tag 3", "Tag 4", "Tag 5"),
        "Tab 2" to listOf("Tag 6", "Tag 7", "Tag 8", "Tag 9", "Tag 10"),
        "Tab 3" to listOf("Tag 11", "Tag 12", "Tag 13")
    )
    PresetTagTabs(tabTags)

}