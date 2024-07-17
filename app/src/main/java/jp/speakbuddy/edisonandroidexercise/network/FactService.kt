package jp.speakbuddy.edisonandroidexercise.network

import jp.speakbuddy.edisonandroidexercise.model.FactListResponse
import jp.speakbuddy.edisonandroidexercise.model.FactResponse
import kotlinx.serialization.Serializable
import retrofit2.http.GET
import retrofit2.http.Query

interface FactService {
    @GET("fact")
    suspend fun getFact(): FactResponse

    @GET("facts")
    suspend fun getFacts(
        @Query("limit") limit: Int,
        @Query("max_length") maxLength: Int
    ): FactListResponse

}
