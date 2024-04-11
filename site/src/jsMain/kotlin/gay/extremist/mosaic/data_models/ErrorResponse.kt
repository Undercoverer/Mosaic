package gay.extremist.mosaic.data_models

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponse(
    val error: String,
    val message: String,
    val status: Int,
    var data: @Contextual Any? = null,
)
