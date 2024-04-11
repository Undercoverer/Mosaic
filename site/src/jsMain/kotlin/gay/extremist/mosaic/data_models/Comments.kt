package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable

@Serializable
data class CommentResponse(
    val id: Int,
    val account: AccountDisplayResponse,
    val video: VideoDisplayResponse,
    val parentComment: CommentResponseNonrecursive?,
    val comment: String
)

@Serializable
data class CommentResponseNonrecursive(
    val id: Int,
    val account: AccountDisplayResponse,
    val video: VideoDisplayResponse,
    val comment: String
)