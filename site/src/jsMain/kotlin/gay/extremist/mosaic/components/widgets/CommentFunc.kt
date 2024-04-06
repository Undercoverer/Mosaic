package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.width
import com.varabyte.kobweb.silk.components.forms.*
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun CommentFunc(onAction: (String) -> Unit){
    Column(){
        var comment by remember { mutableStateOf("") }
        InputGroup(Modifier.width(45.cssRem), size = InputSize.SM) {

            TextInput(
                comment,
                placeholder = "Comment",
                variant = FilledInputVariant,
                onTextChanged = { comment = it })
            RightInset(width = 7.7.cssRem) {
                Button(
                    onClick = { onAction(comment) },
                    size = ButtonSize.SM,
                ) {
                    Text("Add Comment")
                }
            }
        }
    }

}