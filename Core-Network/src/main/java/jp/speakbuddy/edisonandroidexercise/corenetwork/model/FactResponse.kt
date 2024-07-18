package jp.speakbuddy.edisonandroidexercise.corenetwork.model

import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)