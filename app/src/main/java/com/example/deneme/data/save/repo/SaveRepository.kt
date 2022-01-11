package com.example.deneme.data.save.repo

import com.example.deneme.domain.entity.Receipe

interface SaveRepository {

    suspend fun getRandomRecipe(start:Int, end:Int): List<Receipe>
}
