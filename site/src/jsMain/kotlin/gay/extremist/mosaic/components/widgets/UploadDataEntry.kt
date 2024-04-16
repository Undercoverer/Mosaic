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
import com.varabyte.kobweb.silk.components.forms.*
import com.varabyte.kobweb.silk.components.icons.fa.FaPlus
import com.varabyte.kobweb.silk.components.icons.fa.FaUpload
import gay.extremist.mosaic.BASE_URL
import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.Util.getRequest
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
        val inputWidth = Modifier.width(16.cssRem)

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
                            "",
                            emptyList()
                        )
                    )
                )
            )
        }

        val coroutineScope = rememberCoroutineScope()

        coroutineScope.launch{
            presetTags = getRequest<TagCategorizedResponse>(
                urlString = "tags/preset",
                onError = {
                    println(it.message)
                }
            ) ?: presetTags
        }

        Box(
            modifier = Modifier.background(Color.rgb(0x2454BF)).borderRadius(2.cssRem).margin(bottom = 0.5.cssRem),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { onAction(title, desc, userTags, checkedPresetTags, file) }
            ) {
                FaUpload()
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




        InputGroup(size = InputSize.LG, modifier = inputWidth) {
            TextInput(
                title,
                placeholder = "Title",
                variant = FilledInputVariant,
                onTextChanged = { title = it })
        }

        InputGroup(size = InputSize.LG, modifier = inputWidth) {
            TextInput(
                desc,
                placeholder = "Description",
                variant = FilledInputVariant,
                onTextChanged = { desc = it })

        }

        InputGroup(size = InputSize.LG, modifier = inputWidth) {
            TextInput(
                currentUserTag,
                placeholder = "Add Custom Tag",
                variant = FilledInputVariant,
                onTextChanged = { currentUserTag = it },
                onCommit = {
                    if (currentUserTag.isNotBlank()) {
                        userTags = userTags + currentUserTag
                        currentUserTag = ""
                    }
                }
            )
            RightInset(width = 4.cssRem) {
                Button(
                    onClick = {
                        if (currentUserTag.isNotBlank()) {
                            userTags = userTags + currentUserTag
                            currentUserTag = ""
                        }
                    },
                    Modifier.background(Color.rgb(0x2454BF)).borderRadius(3.cssRem),
                    size = ButtonSize.SM,
                ) {
                    FaPlus()
                }
            }
        }

        // Display all entered user tags with delete buttons
        Box(Modifier.fillMaxWidth().width(16.cssRem)) {
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
    }
}
