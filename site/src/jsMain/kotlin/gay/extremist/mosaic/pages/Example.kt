package gay.extremist.mosaic.pages

import androidx.compose.runtime.*
import com.varabyte.kobweb.compose.css.Overflow
import com.varabyte.kobweb.compose.css.ScrollBehavior
import com.varabyte.kobweb.compose.foundation.layout.Arrangement
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.*
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.core.rememberPageContext
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import gay.extremist.mosaic.CLIENT
import gay.extremist.mosaic.data_models.Address
import gay.extremist.mosaic.data_models.Company
import gay.extremist.mosaic.data_models.Geo
import gay.extremist.mosaic.data_models.User
import gay.extremist.mosaic.toSitePalette
import io.ktor.client.*
import io.ktor.client.engine.js.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.json.Json
import org.jetbrains.compose.web.css.AlignSelf
import org.jetbrains.compose.web.css.percent
import org.jetbrains.compose.web.dom.P
import org.jetbrains.compose.web.dom.Text


@Page("/example-users-page")
@Composable
fun ExamplePage() {
    val sitePalette = ColorMode.current.toSitePalette()


    /**
     * Create a client to make HTTP requests to the server.
     * Plugins are installed within the block
     * The ContentNegotiation plugin is installed to turn incoming Json objects
     * into Kotlin data classes (which we have to define ourselves). TODO WHY ISNT IT WORKING >:(
     * Any incoming json which matches the data class will be automatically converted
     */


    // Required to get information about the current page and its url
    val pageCtx = rememberPageContext()

    // We get the parameter 'id' value from the url to pass into the api
    val id = pageCtx.route.params["id"]?.toIntOrNull() ?: return // handle the errors

    // Temporary placeholder values for the user data before they are fetched from the api
    val loadingVal = "Loading..."

    // by remember allows us to store the value of the mutable state in a way that it can be
    // accessed by other Composables without having to pass it in.
    var user by remember {
        mutableStateOf(
            // 'User' is a data class I made in the data_models folder
            User(
                -1,
                loadingVal,
                loadingVal,
                loadingVal,
                Address(loadingVal, loadingVal, loadingVal, loadingVal, Geo(loadingVal, loadingVal)),
                loadingVal,
                loadingVal,
                Company(loadingVal, loadingVal, loadingVal)
            )
        )
    }

    // LaunchedEffect runs the block of code inside whenever the id value changes / when the component is first rendered.
    //
    // Making web requests is blocking in a way that you can't do them just anywhere
    // They have to be launched inside coroutines. LaunchedEffect does that
    LaunchedEffect(id) {
//        // This would be how you do it, but it's broken for an unknown "unsolvable" reason (a couple hours and I couldn't find why)
//        user = httpClient.get("https://jsonplaceholder.typicode.com/users/$id").body<User>()
        user = Json.decodeFromString(CLIENT.get("https://jsonplaceholder.typicode.com/users/$id").bodyAsText())
    }

    // It just works
    Column(
        modifier = Modifier.fillMaxSize().background(sitePalette.cobweb).overflow(Overflow.Auto),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        P { Text("Name: ${user.name}\n") }
        P { Text("Username: ${user.username}\n") }
        P { Text("Email: ${user.email}\n") }
        P { Text("Phone: ${user.phone}\n") }
        P { Text("Website: ${user.website}\n") }
        P { Text("Address: ${user.address.street}, ${user.address.suite}, ${user.address.city}, ${user.address.zipcode}\n") }
        P { Text("Company: ${user.company.name}, ${user.company.catchPhrase}, ${user.company.bs}\n") }
    }
}