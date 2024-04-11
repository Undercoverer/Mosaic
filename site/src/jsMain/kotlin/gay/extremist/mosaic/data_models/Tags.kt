package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable

@Serializable
data class TagDisplayResponse(
    val id: Int,
    val tag: String
)

@Serializable
data class TagResponse(
    val id: Int,
    val tag: String,
    val category: String?,
    val isPreset: Boolean
)