package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.icons.ChevronRightIcon
import org.jetbrains.compose.web.css.cssRem

@Composable
fun PlaylistTitleFunc(onAction: (String) -> Unit){
    Column(){
        var title by remember { mutableStateOf("") }
        InputGroup(Modifier.width(15.cssRem), size = InputSize.MD) {

            TextInput(
                title,
                placeholder = "Change Playlist Title",
                variant = FilledInputVariant,
                onTextChanged = { title = it })
            RightInset(width = 2.cssRem) {
                Button(
                    onClick = { onAction(title) },
                    size = ButtonSize.MD,
                ) {
                    ChevronRightIcon()
                }
            }
        }
    }

}