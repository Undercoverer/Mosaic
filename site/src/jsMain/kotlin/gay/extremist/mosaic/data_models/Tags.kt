package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable

@Serializable
data class Tag(
    val id: Int,
    val tag: String,
    val category: String?
)