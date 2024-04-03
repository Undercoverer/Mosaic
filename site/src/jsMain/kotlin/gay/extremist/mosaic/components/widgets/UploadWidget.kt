package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.icons.DownloadIcon
import org.jetbrains.compose.web.css.Position
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.Text

@Composable
fun UploadWidget(){
    var showDownloadBox by remember { mutableStateOf(false) }

    IconButton(
        onClick = {
            showDownloadBox = true
        },
    ) {
        DownloadIcon()
    }

    if (showDownloadBox) {
        Box(
            modifier = Modifier
                .position(Position.Absolute)
                .left(77.percent)
                .top(32.percent)
                .translate((-50).percent, (-50).percent)
                .padding(10.px)
                .background(Color.rgb(0x1F4599))
                .zIndex(9999) // Ensure box appears over other elements
                .onClick { showDownloadBox = false } // Dismiss the box when clicked
        ) {
            Text("Download")
        }
    }
}