package com.example.deneme.data.home.repository

import com.example.deneme.domain.entity.Receipe


interface HomeRepo {

    suspend fun getTodaysRecipe(): Receipe?
    suspend fun getCollections(page:Int): List<Receipe>
    suspend fun getRandomRecipe(start:Int, end:Int): List<Receipe>
}