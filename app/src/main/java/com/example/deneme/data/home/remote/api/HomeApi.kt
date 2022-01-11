package com.example.deneme.data.home.remote.api

import com.example.deneme.data.home.remote.dto.RandomRecipeRes
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {

    @GET("recipes/random")
    suspend fun randomRecipes(@Query("number") number: Int,
                              @Query("tags") tags: String,
                              @Query("limitLicense") limitLicense: Boolean,
                              @Query("apiKey") apiKey: String)
            : Response<RandomRecipeRes>

}