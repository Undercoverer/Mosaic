package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable

@Serializable
data class PlaylistDisplayResponse(
    val id: Int,
    val name: String
)

@Serializable
data class NewPlaylistData(
    val name: String
)

@Serializable
data class PlaylistResponse(
    val id: Int,
    val owner: Int,
    val name: String,
    val videos: List<VideoDisplayResponse>
)