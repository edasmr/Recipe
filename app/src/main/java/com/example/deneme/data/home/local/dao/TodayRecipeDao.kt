package com.example.deneme.data.home.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.deneme.data.home.local.entity.TodayRecipeEntity

@Dao
interface TodayRecipeDao {
    @Query("SELECT * FROM TodayRecipe WHERE day = :day LIMIT 1")
    fun get(day:String): TodayRecipeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: TodayRecipeEntity)
}