package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable

@Serializable
data class VideoResponse(
    val id: Int,
    val title: String,
    val description: String,
    val videoPath: String,
    val tags: List<TagResponse>,
    val creator: AccountDisplayResponse,
    val viewCount: Int,
    val uploadDate: String,
    val rating: Double
)

@Serializable
data class VideoDisplayResponse(
    val id: Int,
    val title: String,
    val videoPath: String
)