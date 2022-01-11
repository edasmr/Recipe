package com.example.deneme.data.home.local.dao

import androidx.room.*
import com.example.deneme.data.home.local.entity.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM Recipe")
    fun getAll(): List<RecipeEntity>

    @Query("SELECT * FROM Recipe LIMIT :limit")
    fun getAll(limit:Int): List<RecipeEntity>

    @Query("SELECT * FROM Recipe ORDER BY RANDOM() LIMIT :limit")
    fun getRandom(limit:Int): List<RecipeEntity>

    @Query("SELECT count(*) FROM Recipe")
    fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(recipe: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipe: RecipeEntity)

    @Delete
    fun delete(recipe: RecipeEntity)

    @Query("SELECT * FROM Recipe WHERE id = :id LIMIT 1")
    fun get(id:Int): RecipeEntity



}