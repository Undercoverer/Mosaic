@file:OptIn(ExperimentalMaterial3Api::class)

package gay.extremist.mosaic.components.widgets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.foundation.layout.Row

@Composable
fun SearchFunc() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Row {
        SearchBar(
            query = text,
            onQueryChange = {
                text = it
            },
            onSearch = {
               active = false
            },
            active = active,
            onActiveChange = {
                 active = it
            },
        ) {

        }
    }
}