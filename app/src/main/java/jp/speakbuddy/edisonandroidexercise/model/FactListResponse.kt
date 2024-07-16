package jp.speakbuddy.edisonandroidexercise.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class FactListResponse(
    @SerialName("current_page")
    val currentPage: Long,

    val data: List<FactResponse>,

    @SerialName("first_page_url")
    val firstPageURL: String,

    val from: Long,

    @SerialName("last_page")
    val lastPage: Long,

    @SerialName("last_page_url")
    val lastPageURL: String,

    val links: List<Link>,

    @SerialName("next_page_url")
    val nextPageURL: String,

    val path: String,

    @SerialName("per_page")
    val perPage: Long,

    @SerialName("prev_page_url")
    val prevPageURL: JsonElement? = null,

    val to: Long,
    val total: Long
)

@Serializable
data class Link(
    val url: String? = null,
    val label: String,
    val active: Boolean
)