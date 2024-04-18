package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.graphics.Colors
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.overlay.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import gay.extremist.mosaic.Util.getRequest
import gay.extremist.mosaic.data_models.PlaylistDisplayResponse
import gay.extremist.mosaic.data_models.PlaylistResponse
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get

@Composable
fun SavePopUp(
    onPlaylistAction: (String) -> PlaylistDisplayResponse?,
    onCheckboxAction: (PlaylistDisplayResponse?) -> Unit,
) {
    val sitePalette = ColorMode.current.toSitePalette()
    val coroutineScope = rememberCoroutineScope()
    var isPopoverVisible by remember { mutableStateOf(false) }
    var checkedItem by remember { mutableStateOf<PlaylistDisplayResponse?>(null) }
    var playlistInput by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isSaveError by remember { mutableStateOf(false) }

    val playlists = mutableStateOf(listOf<PlaylistDisplayResponse>())

    var playlistNames by remember {
        mutableStateOf(listOf<String>())
    }

    coroutineScope.launch {
        playlists.value = getRequest<List<PlaylistDisplayResponse>>(
            urlString = "accounts/${window.localStorage["id"]}/playlists",
            onError = {
                println(it.message)
            }
        ) ?: playlists.value

        playlistNames = playlists.value.map { it.name }
    }

    Button(
        onClick = {
            isPopoverVisible = !isPopoverVisible
            // Reset error state when button is clicked
            isError = false
            isSaveError = false
        },
        modifier = Modifier.color(sitePalette.brand.secondary)
    ) {
        Text("Save")
    }

    if (isPopoverVisible) {
        Popover(
            target = ElementTarget.PreviousSibling,
            modifier = Modifier.padding(2.cssRem).background(when (ColorMode.current) {
                ColorMode.LIGHT -> Colors.LightGray
                ColorMode.DARK -> Color.rgb(0x2B2B2B)
            }),
            placement = PopupPlacement.TopRight,
            keepOpenStrategy = KeepPopupOpenStrategy.manual(true),
            content = {
                Column(Modifier.fillMaxWidth().gap(1.cssRem)) {
                    // Playlist section
                    InputGroup(size = InputSize.LG) {
                        TextInput(
                            playlistInput,
                            placeholder = "Add to New Playlist",
                            variant = FilledInputVariant,
                            onTextChanged = { newText ->
                                // Update playlist input and clear error state when typing a new name
                                playlistInput = newText
                                isError = false
                            }
                        )
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                // Check if the entered playlist does not exist in the checkbox items
                                if (playlistInput !in playlistNames) {
                                    // Invoke the action if playlist does not exist
                                    when(val newPlaylist = onPlaylistAction(playlistInput)){
                                        is PlaylistDisplayResponse -> {
                                            playlists.value += newPlaylist
                                            println (newPlaylist.name)
                                        }
                                        null -> {
                                            //TODO(Not Quite Sure How to change an existing element)
                                        }
                                    }
                                } else {
                                    // Set error state if playlist exists
                                    isError = true
                                }
                            },
                            enabled = !isError // Disable the button if there's an error
                        ) {
                            Text("Add to New Playlist")
                        }
                    }

                    // Error tooltip for invalid playlist input
                    if (isError) {
                        Tooltip(ElementTarget.PreviousSibling, "Playlist already exists", placement = PopupPlacement.Bottom, keepOpenStrategy = KeepPopupOpenStrategy.manual(true))

                    }

                    // Checkbox section
                    Box(
                        Modifier.fillMaxWidth().height(10.cssRem).overflow { y(Overflow.Auto) },
                        Alignment.TopCenter
                    ) {
                        Column(Modifier.padding(1.cssRem).fillMaxSize()) {
                            playlists.value.forEach { playlist ->
                                Checkbox(
                                    checked = playlist == checkedItem,
                                    onCheckedChange = {
                                        checkedItem = if (it) playlist else null
                                    },
                                    size = CheckboxSize.LG,
                                    colorScheme = ColorSchemes.LightBlue
                                ) {
                                    Text(playlist.name)
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = { onCheckboxAction(checkedItem) },
                        ) {
                            Text("Add to Playlist")
                        }
                    }

                    if (isSaveError) {
                        Tooltip(ElementTarget.PreviousSibling, "The Video is already in that playlist", placement = PopupPlacement.Bottom, keepOpenStrategy = KeepPopupOpenStrategy.manual(true))

                    }
                }
            }
        )
    }
}