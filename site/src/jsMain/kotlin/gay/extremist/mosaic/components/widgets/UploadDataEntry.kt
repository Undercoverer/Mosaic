package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.disclosure.Tabs
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.icons.DownloadIcon
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun UploadDataEntry(onAction: (String, String, String, List<String>, List<String>) -> Unit) {

    Column(Modifier.gap(0.5.cssRem),  verticalArrangement = Arrangement.Center) {
        var video by remember { mutableStateOf("") }
        var title by remember { mutableStateOf("") }
        var desc by remember { mutableStateOf("") }
        var userTags by remember { mutableStateOf(listOf<String>()) }
        var checkedItems by remember { mutableStateOf(emptyList<String>()) }


        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { onAction(video, title, desc, userTags, checkedItems) }
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
                modifier = Modifier.height(10.cssRem),
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


        Tabs {
            TabPanel {
                Tab { Text("Tab 1") }; Panel {
                Column(Modifier.gap(1.cssRem)) {
                    Row(Modifier.gap(1.cssRem), verticalAlignment = Alignment.CenterVertically) {
                        listOf(ColorSchemes.LightBlue).forEach { colorScheme ->
                            var checked by remember { mutableStateOf(false) }
                            Checkbox(
                                checked,
                                onCheckedChange = {
                                    checked = it
                                    // Update checkedItems list
                                    checkedItems = if (it) {
                                        checkedItems + "Checkbox" // Adjust this as per your checkbox label
                                    } else {
                                        checkedItems - "Checkbox"
                                    }
                                },
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
}
