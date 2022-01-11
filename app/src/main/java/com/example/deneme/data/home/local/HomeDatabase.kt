package com.example.deneme.data.home.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deneme.data.home.local.dao.RecipeDao
import com.example.deneme.data.home.local.dao.TodayRecipeDao
import com.example.deneme.data.home.local.entity.RecipeEntity
import com.example.deneme.data.home.local.entity.TodayRecipeEntity

@Database(entities = [RecipeEntity::class, TodayRecipeEntity::class], version = 10)
abstract class HomeDatabase : RoomDatabase() {
    abstract fun receipeDao(): RecipeDao
    abstract fun todayRecipeDao(): TodayRecipeDao
}