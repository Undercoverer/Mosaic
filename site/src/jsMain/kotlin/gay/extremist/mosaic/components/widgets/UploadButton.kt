package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.silk.components.forms.FilledInputVariant
import com.varabyte.kobweb.silk.components.forms.InputGroup
import com.varabyte.kobweb.silk.components.forms.InputSize
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.icons.DownloadIcon

@Composable
fun UploadButton(onAction: (String) -> Unit) {
    var video by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputGroup(size = InputSize.LG) {
            TextInput(
                video,
                placeholder = "Video",
                variant = FilledInputVariant,
                onTextChanged = { video = it })

            RightInset {
                IconButton(onClick = { onAction(video) },) {
                    DownloadIcon()
                }
            }
        }

    }
}