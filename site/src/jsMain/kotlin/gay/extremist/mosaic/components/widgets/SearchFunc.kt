@file:OptIn(ExperimentalMaterial3Api::class)

package gay.extremist.mosaic.components.widgets

//import com.varabyte.kobweb.compose.ui.Modifier
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.varabyte.kobweb.compose.foundation.layout.Row

@Composable
fun SearchFunc() {
    var text by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }

    Row {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
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
            placeholder = {
                Text(text = "Search")
            }
        ) {

        }
    }
}