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
import com.varabyte.kobweb.silk.components.forms.Button
import com.varabyte.kobweb.silk.components.forms.Checkbox
import com.varabyte.kobweb.silk.components.forms.CheckboxSize
import com.varabyte.kobweb.silk.components.overlay.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import com.varabyte.kobweb.silk.theme.colors.ColorSchemes
import gay.extremist.mosaic.Util.getRequest
import gay.extremist.mosaic.data_models.ErrorResponse
import gay.extremist.mosaic.data_models.PlaylistDisplayResponse
import gay.extremist.mosaic.toSitePalette
import kotlinx.browser.window
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.dom.Text
import org.w3c.dom.get

@Composable
fun SavePopUp(
    onCheckboxAction: (PlaylistDisplayResponse?) -> Any?,
) {
    val sitePalette = ColorMode.current.toSitePalette()
    val coroutineScope = rememberCoroutineScope()
    var isPopoverVisible by remember { mutableStateOf(false) }
    var checkedItem by remember { mutableStateOf<PlaylistDisplayResponse?>(null) }
    var error by remember { mutableStateOf<ErrorResponse?>(null) }
    var isSaved by remember { mutableStateOf<Boolean?>(false) }

    val playlists = remember { mutableStateOf(listOf<PlaylistDisplayResponse>()) }

    coroutineScope.launch {
        playlists.value =
            getRequest<List<PlaylistDisplayResponse>>(urlString = "accounts/${window.localStorage["id"]}/playlists",
                onError = {
                    println(it.message)
                }) ?: playlists.value
    }

    Button(
        onClick = {
            isPopoverVisible = !isPopoverVisible
        }, modifier = Modifier.color(sitePalette.brand.secondary)
    ) {
        Text("Save")
    }

    if (isPopoverVisible) {
        Popover(target = ElementTarget.PreviousSibling, modifier = Modifier.padding(2.cssRem).background(
            when (ColorMode.current) {
                ColorMode.LIGHT -> Colors.LightGray
                ColorMode.DARK -> Color.rgb(0x2B2B2B)
            }
        ), placement = PopupPlacement.TopRight, keepOpenStrategy = KeepPopupOpenStrategy.manual(true), content = {
            Column(Modifier.fillMaxWidth().gap(1.cssRem)) {
                // Checkbox section
                Box(
                    Modifier.fillMaxWidth().height(10.cssRem).overflow { y(Overflow.Auto) }, Alignment.TopCenter
                ) {
                    Column(Modifier.padding(1.cssRem).fillMaxSize()) {
                        playlists.value.forEach { playlist ->
                            Checkbox(
                                checked = playlist == checkedItem, onCheckedChange = {
                                    checkedItem = playlist
                                    println("Checked playlist ${checkedItem?.name}")
                                }, size = CheckboxSize.LG, colorScheme = ColorSchemes.LightBlue
                            ) {
                                Text(playlist.name)
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center
                ) {
                    Button(
                        onClick = {
                            if (checkedItem == null) {
                                error = ErrorResponse("selection","No playlist selected", 0)
                            } else {
                                val returnValue = onCheckboxAction(checkedItem)
                                when(returnValue) {
                                    is ErrorResponse -> {
                                        error = returnValue
                                    } else -> {
                                        error = null
                                    }
                                }
                            }
                            isSaved = true

                        },
                    ) {
                        Text("Add to Playlist")
                    }
                }

                ShowAddSuccess(isSaved, error) {
                    isSaved = false
                }
            }
        })
    }
}

@Composable
fun ShowAddSuccess(saved: Boolean?, errorResponse: ErrorResponse?, onTimeout: () -> Unit) {
    if (saved == true || saved == null) {
        AdvancedTooltip(
            ElementTarget.PreviousSibling,
            errorResponse?.message ?: "Video Added To Playlist Successfully",
            placementStrategy = PopupPlacementStrategy.of(PopupPlacement.Top)
        )
        LaunchedEffect(key1 = saved) {
            delay(3000)
            onTimeout()
        }
    }
}