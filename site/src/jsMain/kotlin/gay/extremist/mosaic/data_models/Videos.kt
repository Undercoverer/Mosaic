package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable
import kotlin.js.Date

@Serializable
data class VideoResponse(
    val videoId: Int,
    val title: String,
    val description: String,
    val videoPath: String,
    val tags: List<Tag>,
    val creatorId: Int,
    val uploadDate: String
)

@Serializable
data class VideoListObject(
    val id: Int,
    val title: String,
    val videoPath: String
)