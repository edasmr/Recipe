package com.example.deneme.data.home.remote.dto

import com.google.gson.annotations.SerializedName

data class RecipeDTO (
    @SerializedName("id") var recipeid: Int? = null,
    @SerializedName("title") var recipetitle: String? = null,
    @SerializedName("image") var image: String? = null,
    @SerializedName("summary") var summary: String? = null,
    @SerializedName("spoonacularScore") val spoonacularScore: Double?,
    @SerializedName("readyInMinutes") val readyInMinutes: Int?,
    @SerializedName("pricePerServing") val pricePerServing: Double?,
   // @SerializedName("saved") val saved: Boolean?
)