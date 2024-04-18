package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.ButtonSize
import com.varabyte.kobweb.silk.components.icons.ChevronRightIcon
import com.varabyte.kobweb.silk.components.overlay.KeepPopupOpenStrategy
import com.varabyte.kobweb.silk.components.overlay.Popover
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.manual
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text

@Composable
fun EditPlaylistFunc(onSubmit: (String) -> Unit, onDelete:() -> Unit){
    var isPopoverVisible by remember { mutableStateOf(false) }

    Column(verticalArrangement = Arrangement.Center){
        Box(
            modifier = Modifier.background(Color.rgb(0x8269F8)).borderRadius(2.cssRem),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { isPopoverVisible = !isPopoverVisible  },
            ) {
                ChevronRightIcon()
            }
        }
        if (isPopoverVisible) {
            Popover(
                target = ElementTarget.PreviousSibling,
                modifier = Modifier.padding(1.cssRem).background(when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }),
                placement = PopupPlacement.Right,
                keepOpenStrategy = KeepPopupOpenStrategy.manual(true),
                content = {
                    Column(Modifier.fillMaxSize().gap(0.5.cssRem), verticalArrangement = Arrangement.Center) { // Ensure the row lays out items vertically aligned
                        PlaylistTitleFunc { title ->
                            // Do something with the comment
                            onSubmit(title)
                            println("Title submitted: $title")
                        }

                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                onClick = {
                                    onDelete()
                                },
                                size = ButtonSize.SM
                            ) {
                                Text("Delete Playlist")
                            }
                        }

                    }
                }
            )
        }
    }
}