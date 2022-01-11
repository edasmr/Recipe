package com.example.deneme.data.home.local.entity


import androidx.room.*

@Entity (tableName = "Recipe")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "image") val image: String?,
    @ColumnInfo(name = "summary") val summary: String?,
    @ColumnInfo(name = "spoonacularScore") val spoonacularScore: Double?,
    @ColumnInfo(name = "readyInMinutes") val readyInMinutes: Int?,
    @ColumnInfo(name = "pricePerServing") val pricePerServing: Double?
)