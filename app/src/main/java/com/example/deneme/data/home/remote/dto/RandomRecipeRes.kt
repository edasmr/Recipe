package com.example.deneme.data.home.remote.dto

import com.google.gson.annotations.SerializedName

data class RandomRecipeRes(
    @SerializedName("recipes") var recipes: List<RecipeDTO>? = null
)