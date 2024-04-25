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
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.icons.ChevronRightIcon
import com.varabyte.kobweb.silk.components.icons.PlusIcon
import com.varabyte.kobweb.silk.components.overlay.*
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.Util.*
import gay.extremist.mosaic.data_models.ErrorResponse
import gay.extremist.mosaic.data_models.VideoResponse
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.cssRem
import org.w3c.dom.get


@Composable
fun RatingFunc(
    video: VideoResponse,
    videoRating: MutableState<Double?>,
    onAdded: (Double) -> Unit,
    onUpdated: (Double) -> Unit,
    onError: (ErrorResponse) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    var inputRating by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isPopoverVisible by remember { mutableStateOf(false) }

    coroutineScope.launch {
        videoRating.value = getRequest<Double>(
            urlString = "ratings/averageOn/${video.id}"
        )
    }

    Box(
        modifier = Modifier.borderRadius(1.cssRem), contentAlignment = Alignment.Center
    ) {
        IconButton(onClick = { isPopoverVisible = !isPopoverVisible }) {
            PlusIcon()
        }
    }
    if (isPopoverVisible) {
        Popover(
            target = ElementTarget.PreviousSibling, modifier = Modifier.padding(1.cssRem).background(
                when (ColorMode.current) {
                    ColorMode.LIGHT -> Colors.LightGray
                    ColorMode.DARK -> Color.rgb(0x2B2B2B)
                }
            ), placement = PopupPlacement.TopRight, keepOpenStrategy = KeepPopupOpenStrategy.manual(true)
        ) {
            Column(
                Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center
            ) { // Ensure the row lays out items vertically aligned
                InputGroup(Modifier.width(7.cssRem), size = InputSize.LG) {
                    TextInput(
                        inputRating,
                        placeholder = "Rate",
                        variant = FilledInputVariant,
                        onTextChanged = { newText ->
                            // Reset the error flag when the user starts typing
                            isError = false
                            // Only update the rating if the new text is empty or a valid number between 1 and 5
                            if (newText.isEmpty() || newText.toIntOrNull() in 1..5) {
                                inputRating = newText
                            } else {
                                // If the input is invalid, set the error flag to true
                                isError = true
                            }
                        },
                    )
                    RightInset(Modifier.width(3.cssRem)) {
                        Button(
                            onClick = {
                                val ratingVal = inputRating.toIntOrNull() ?: return@Button
                                if (ratingVal in 1..5 && videoRating.value != null) {

                                    coroutineScope.launch {
                                        val oldRatingId = getRequest<Int>(urlString = "ratings/on", setHeaders = {
                                            append(headerVideoId, video.id.toString())
                                            append(headerAccountId, window.localStorage["id"] ?: "")
                                            append(headerToken, window.localStorage["token"] ?: "")
                                        }, onSuccess = {
                                            postRequest<Int, Any>(
                                                urlString = "ratings/$it",
                                                setBody = { ratingVal })
                                            videoRating.value = getRequest<Double>(
                                                urlString = "ratings/averageOn/${video.id}"
                                            )
                                        }, onError = {
                                            postRequest<Int, Any>(urlString = "ratings", setHeaders = {
                                                append(headerVideoId, video.id.toString())
                                                append(headerAccountId, window.localStorage["id"] ?: "")
                                                append(headerToken, window.localStorage["token"] ?: "")
                                            }, setBody = { ratingVal })
                                            videoRating.value = getRequest<Double>(
                                                urlString = "ratings/averageOn/${video.id}"
                                            )
                                        })
                                    }
                                } else {
                                    isError = true
                                }
                            },
                            size = ButtonSize.SM,
                        ) {
                            ChevronRightIcon()
                        }
                    }


                }

                if (isError) {
                    Tooltip(
                        ElementTarget.PreviousSibling,
                        "Enter a Number between 1 and 5",
                        placement = PopupPlacement.BottomRight
                    )
                    window.setTimeout({ isError = false }, 1000)
                }

            }

        }
    }

    // Add an invisible target element for the tooltip
    Box(modifier = Modifier.id("rating-tooltip"))
}
