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

@Serializable
data class Category(
    val category: String,
    val tags:List<TagDisplayResponse>
)

@Serializable
data class TagCategorizedResponse(
    val categories: List<Category>
)