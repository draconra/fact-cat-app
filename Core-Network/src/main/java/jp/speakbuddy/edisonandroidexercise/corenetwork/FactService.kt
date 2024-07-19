package jp.speakbuddy.edisonandroidexercise.corenetwork

import jp.speakbuddy.edisonandroidexercise.corenetwork.model.FactListResponse
import jp.speakbuddy.edisonandroidexercise.corenetwork.model.FactResponse
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
