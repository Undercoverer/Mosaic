package gay.extremist.mosaic.components.widgets

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.foundation.layout.Row
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.graphics.Color
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.silk.components.forms.FilledInputVariant
import com.varabyte.kobweb.silk.components.forms.InputGroup
import com.varabyte.kobweb.silk.components.forms.InputSize
import com.varabyte.kobweb.silk.components.forms.TextInput
import com.varabyte.kobweb.silk.components.icons.fa.FaUpload
import gay.extremist.mosaic.BASE_URL
import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.data_models.Category
import gay.extremist.mosaic.data_models.ErrorResponse
import gay.extremist.mosaic.data_models.TagCategorizedResponse
import gay.extremist.mosaic.data_models.VideoResponse
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.w3c.files.File
import org.w3c.files.get


@Composable
fun UploadDataEntry(onAction: (String, String, List<String>, List<String>, file: File?) -> Unit ) {

    Column(Modifier.gap(0.5.cssRem).fontSize(1.3.cssRem),  verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        var title by remember { mutableStateOf("") }
        var desc by remember { mutableStateOf("") }
        var userTags by remember { mutableStateOf(listOf<String>()) }
        var checkedPresetTags by remember { mutableStateOf<List<String>>(emptyList()) }
        var currentUserTag by remember { mutableStateOf("") }
        var file by remember { mutableStateOf<File?>(null) }
        var presetTags by remember {
            mutableStateOf(
                TagCategorizedResponse(
                    listOf(
                        Category(
                            "Loading...",
                            emptyList()
                        )
                    )
                )
            )
        }

        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch{
            val responseBody = CLIENT.get("tags/preset").bodyAsText()
            val response = runCatching {
                Json.decodeFromString<TagCategorizedResponse>(responseBody)
            }.recoverCatching {
                Json.decodeFromString<ErrorResponse>(responseBody)
            }.getOrNull()

            when(response){
                is TagCategorizedResponse -> {
                    presetTags = response
                }

                is ErrorResponse -> {
                    println(response.message)
                    // TODO add error handling for video
                }

                null -> {
                    // TODO idk
                }

            }
        }

        Box(
            contentAlignment = Alignment.Center
        ) {
            Form(action = "$BASE_URL/video", attrs = {
                attr("method", "POST")
                attr("enctype", "multipart/form-data")
            }) {
                Column(Modifier.fillMaxSize().gap(1.cssRem), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Div(attrs = {
                        classes("input-group")
                        style {
                            display(DisplayStyle.Flex)
                            justifyContent(JustifyContent.Center)
                            alignItems(AlignItems.Center)
                        }
                    }) {
                        Label(forId = "files") {

                        }
                        Input(InputType.File, attrs = {
                            id("file")
                            attr("multiple", "")
                            onChange {
                                file = it.target.files?.get(0)
                            }
                        })
                    }
                }
            }
        }




        InputGroup(size = InputSize.LG) {
            TextInput(
                title,
                placeholder = "Title",
                variant = FilledInputVariant,
                onTextChanged = { title = it })
        }

        InputGroup(size = InputSize.LG) {
            TextInput(
                desc,
                placeholder = "Description",
                variant = FilledInputVariant,
                onTextChanged = { desc = it })
        }

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


        PresetTagTabs(
            tabTags = presetTags,
            onCheckedItemsChanged = { checkedPresetTags = it }
        )

        Box(
            modifier = Modifier.background(Color.rgb(0x2454BF)).borderRadius(2.cssRem),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { onAction(title, desc, userTags, checkedPresetTags, file) }
            ) {
                FaUpload()
            }
        }


    }
}
