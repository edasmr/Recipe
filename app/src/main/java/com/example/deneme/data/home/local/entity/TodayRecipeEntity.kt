package com.example.deneme.data.home.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "TodayRecipe")
data class TodayRecipeEntity(
    @PrimaryKey val day: String,
    @ColumnInfo(name = "recipe_id") val recipeId: Int = 0
)