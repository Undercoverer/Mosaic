import components.browserRouter
import react.FC
import react.Props
import react.create
import react.dom.client.createRoot
import react.router.RouterProvider
import web.dom.document

fun main() {
    val appContainer = (document.getElementById("root") ?: error("Couldn't find root container!"))// as HTMLElement

    createRoot(appContainer).render(App.create())
}


private val App = FC<Props>("MainApp") {
    RouterProvider {
        router = browserRouter
    }
}








