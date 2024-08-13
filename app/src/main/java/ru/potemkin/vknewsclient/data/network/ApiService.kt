package ru.potemkin.vknewsclient.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import ru.potemkin.vknewsclient.data.model.NewsFeedResponseDto

interface ApiService {

    @GET("newsfeed.getRecommended?v=5.131")
    suspend fun loadRecommendations(
        @Query("access_token") token: String
    ): NewsFeedResponseDto
}