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
import gay.extremist.mosaic.data_models.*
import kotlinx.browser.window
import kotlinx.coroutines.launch
import org.jetbrains.compose.web.css.CSSUnit
import org.jetbrains.compose.web.css.cssRem
import org.w3c.dom.get


@Composable
fun RatingFunc(video: VideoResponse, onAdded: (Double) -> Unit, onUpdated: (Double) -> Unit, onError: (ErrorResponse) -> Unit = {}){
    val coroutineScope = rememberCoroutineScope()

    var rating by remember { mutableStateOf<RatingResponse?>(null) }
    var inputRating by remember { mutableStateOf("") }
    var isError by remember { mutableStateOf(false) }
    var isPopoverVisible by remember { mutableStateOf(false) }

    coroutineScope.launch {
        val existingID = getRequest<Int>(
            urlString = "ratings/on",
            setHeaders = {
                append(headerAccountId, window.localStorage["id"] ?: "")
                append(headerToken, window.localStorage["token"] ?: "")
                append(headerVideoId, video.id.toString())
            },
            onError = {
                println("fetch existing: error")
            }
        )

        rating = getRequest<RatingResponse>(
            urlString = "ratings/$existingID",
            setHeaders = {
                append(headerAccountId, window.localStorage["id"] ?: "")
                append(headerToken, window.localStorage["token"] ?: "")
                append(headerVideoId, video.id.toString())
            },
            onSuccess = {
                println("fetch existing: success")
            },
            onError = {
                println("fetch existing: error")
                print(it.message)
            },
            onNull = {
                println("fetch existing: null")
            }
        )
    }

    Box(
        modifier = Modifier.borderRadius(1.cssRem),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { isPopoverVisible = !isPopoverVisible  }
        ) {
            PlusIcon()
        }
    }
    if (isPopoverVisible) {
        Popover(
            target = ElementTarget.PreviousSibling,
            modifier = Modifier.padding(1.cssRem).background(when (ColorMode.current) {
                ColorMode.LIGHT -> Colors.LightGray
                ColorMode.DARK -> Color.rgb(0x2B2B2B)
            }),
            placement = PopupPlacement.TopRight,
            keepOpenStrategy = KeepPopupOpenStrategy.manual(true),
            content = {
                Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) { // Ensure the row lays out items vertically aligned
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
                                }},
                        )
                        RightInset(Modifier.width(3.cssRem)) {
                            Button(
                                onClick = {
                                    if (inputRating.toIntOrNull() in 1..5 && rating != null) {
                                        coroutineScope.launch {
                                            val avgFactor = ((video.rating * 2) - rating!!.rating)
                                            rating = postRequest<Int,RatingResponse>(
                                                urlString = "ratings/${rating!!.id}",
                                                setBody = {
                                                    inputRating.toInt()
                                                },
                                                onSuccess = {
                                                    onUpdated((avgFactor + it.rating)/2)
                                                },
                                                onError = {
                                                    println(it.message)
                                                    onError(it)
                                                }
                                            )
                                        }
                                    } else if (inputRating.toIntOrNull() in 1..5 && rating == null){
                                        coroutineScope.launch {
                                            rating = postRequest<Int,RatingResponse>(
                                                urlString = "ratings",
                                                setHeaders = {
                                                    append(headerAccountId, window.localStorage["id"] ?: "")
                                                    append(headerToken, window.localStorage["token"] ?: "")
                                                    append(headerVideoId, video.id.toString())
                                                },
                                                setBody = {
                                                    inputRating.toInt()
                                                },
                                                onSuccess = {
                                                    onAdded((video.rating + it.rating)/2)
                                                },
                                                onError = {
                                                    println(it.message)
                                                    onError(it)
                                                }
                                            )
                                        }
                                    } else{
                                        // Show error message as a tooltip if rating is not within range
                                        isError = true
                                    }},
                                size = ButtonSize.SM,
                            ) {
                                ChevronRightIcon()
                            }
                        }



                    }
                    if (isError) {
                        Tooltip(ElementTarget.PreviousSibling, "Enter a Number between 1 and 5", placement = PopupPlacement.BottomRight)
                        window.setTimeout({isError = false}, 1000)
                    }

                }

            }
        )
    }

    // Add an invisible target element for the tooltip
    Box(modifier = Modifier.id("rating-tooltip"))
}
