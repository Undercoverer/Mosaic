package gay.extremist.mosaic.data_models

import kotlinx.serialization.Serializable


@Serializable
data class RatingResponse(
    val id: Int,
    val videoDisplayResponse: VideoDisplayResponse,
    val account: AccountDisplayResponse,
    val rating: Int
)