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
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.forms.FilledInputVariant
import com.varabyte.kobweb.silk.components.forms.InputGroup
import com.varabyte.kobweb.silk.components.forms.InputSize
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.icons.DownloadIcon
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun UploadDataEntry(onAction: (String, String, String, List<String>, List<String>) -> Unit) {

    Column(Modifier.gap(0.5.cssRem),  verticalArrangement = Arrangement.Center) {
        var video by remember { mutableStateOf("") }
        var title by remember { mutableStateOf("") }
        var desc by remember { mutableStateOf("") }
        var userTags by remember { mutableStateOf(listOf<String>()) }
        var checkedPresetTags by remember { mutableStateOf<List<String>>(emptyList()) }

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { onAction(video, title, desc, userTags, checkedPresetTags) }
            ) {
                DownloadIcon()
            }
        }


        InputGroup(size = InputSize.LG) {
            TextInput(
                video,
                placeholder = "Video",
                variant = FilledInputVariant,
                onTextChanged = { video = it })
        }


        InputGroup( size = InputSize.LG) {
            TextInput(
                title,
                placeholder = "Title",
                variant = FilledInputVariant,
                onTextChanged = { title = it })
        }


        InputGroup( size = InputSize.LG) {
            TextInput(
                desc,
                placeholder = "Description",
                variant = FilledInputVariant,
                onTextChanged = { desc = it })
        }

        // Input field for user tags
        var currentUserTag by remember { mutableStateOf("") }
        InputGroup(size = InputSize.LG) {
            TextInput(
                currentUserTag,
                placeholder = "Enter Tag",
                variant = FilledInputVariant,
                onTextChanged = { currentUserTag = it },
                onCommit = {
                    if (currentUserTag.isNotBlank()) {
                        userTags = userTags + currentUserTag
                        currentUserTag = ""
                    }
                }
            )
        }

        // Display all entered user tags with delete buttons
        Box(Modifier.fillMaxWidth().width(12.cssRem)) {
            if (userTags.isNotEmpty()) {
                var remainingTags = userTags
                Column(modifier = Modifier.fillMaxWidth()) {
                    while (remainingTags.isNotEmpty()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            val rowTags = if (remainingTags.size > 2) remainingTags.take(2) else remainingTags
                            remainingTags = remainingTags.drop(rowTags.size)
                            rowTags.forEach { tag ->
                                Row(Modifier.fontSize(1.3.cssRem), verticalAlignment = Alignment.CenterVertically) {
                                    Text(tag)
                                    IconButton(
                                        onClick = { userTags = userTags - tag }
                                    ) {
                                        Text("X") // White "x" icon
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        val presetTags = listOf(
            "Tab 1" to listOf("Tag 1", "Tag 2", "Tag 3", "Tag 4", "Tag 5"),
            "Tab 2" to listOf("Tag 6", "Tag 7", "Tag 8", "Tag 9", "Tag 10"),
            "Tab 3" to listOf("Tag 11", "Tag 12", "Tag 13")
        )


        PresetTagTabs(
            tabTags = presetTags,
            onCheckedItemsChanged = { checkedPresetTags = it }
        )


    }
}
