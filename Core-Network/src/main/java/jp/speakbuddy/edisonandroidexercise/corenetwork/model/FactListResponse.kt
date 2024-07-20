package jp.speakbuddy.edisonandroidexercise.corenetwork.model

import kotlinx.serialization.Serializable

@Serializable
data class FactListResponse(
    val data: List<FactResponse> = emptyList(),
)