package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.fontSize
import com.varabyte.kobweb.compose.ui.modifiers.gap
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.forms.CheckboxSize
import com.varabyte.kobweb.silk.components.text.SpanText
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import gay.extremist.mosaic.data_models.TagCategorizedResponse
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun FilterWidget(
    sortOptions: List<String>, // List of sort by checkboxes
    presetTags: TagCategorizedResponse, // List of preset tags
    onAction: (List<String>, List<String>) -> Unit // Callback function for filter button
) {
    // Track the checked sort options
    var checkedSortOptions by remember { mutableStateOf<List<String>>(emptyList()) }

    Column(Modifier.gap(0.5.cssRem).fontSize(1.3.cssRem), verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.CenterHorizontally) {
        SpanText("Sort By")

        // Render checkboxes for sort options
        sortOptions.forEach { option ->
            Checkbox(
                checked = option in checkedSortOptions,
                onCheckedChange = { isChecked ->
                    if (isChecked) {
                        checkedSortOptions = checkedSortOptions + option
                    } else {
                        checkedSortOptions = checkedSortOptions - option
                    }
                },
                colorScheme = ColorSchemes.LightBlue,
                size = CheckboxSize.LG
            ) {
                Text(option)
            }
        }

        Row(Modifier.height(1.cssRem)) {}

        // Render preset tag tabs
        var checkedPresetTags by remember { mutableStateOf<List<String>>(emptyList()) }
        PresetTagTabs(
            tabTags = presetTags,
            onCheckedItemsChanged = { checkedPresetTags = it }
        )

        // Filter button
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    // Pass the checked sort options and preset tags to the callback function
                    onAction(checkedSortOptions, checkedPresetTags)
                },
                //Modifier.background(Color.rgb(0x2EB4A9))
            ) {
                Text("Filter")
            }
        }
    }
}
