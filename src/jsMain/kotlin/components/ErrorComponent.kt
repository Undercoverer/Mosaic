package components

import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.p
import web.cssom.ClassName

external interface ErrorProps : Props {
    var message: String
}

val ErrorComponent = FC<ErrorProps> { props ->
    div {
        className = ClassName("error")
        p {
            +props.message
        }
    }
}

