package com.example.deneme.data.save.repo

import com.example.deneme.data.home.local.HomeDatabase
import com.example.deneme.data.home.local.entity.RecipeEntity
import com.example.deneme.data.home.remote.api.HomeApi
import com.example.deneme.domain.entity.Receipe



class SaveRepoImpl (private var saveapi: HomeApi, private var saveDatabase: HomeDatabase) : SaveRepository {





    override suspend fun getRandomRecipe(start: Int, end:Int): List<Receipe> {

        val recipeCount = saveDatabase.receipeDao().count()

        if(end < recipeCount){
            val recipeList = saveDatabase.receipeDao().getRandom(end - start)

            return recipeList.map {
                Receipe(
                    id = it.id,
                    title = it.title,
                    image = it.image,
                    summary = it.summary,
                    spoonacularScore = it.spoonacularScore,
                    readyInMinutes = it.readyInMinutes,
                    pricePerServing = it.pricePerServing
                )
            }
        }else{
            val response = saveapi.randomRecipes(20, "", false, "b3f9d443546c47a88cde2ef1250146a1")

            if(response.isSuccessful){

                response.body()?.recipes?.map {
                    RecipeEntity(
                        id = it.recipeid ?: 0,
                        title = it.recipetitle,
                        image = it.image,
                        summary = it.summary,
                        spoonacularScore = it.spoonacularScore,
                        readyInMinutes = it.readyInMinutes,
                        pricePerServing = it.pricePerServing
                    )
                }?.let{
                    saveDatabase.receipeDao().insertAll(it)
                }

                return response.body()?.recipes?.map {
                    Receipe(
                        id = it.recipeid,
                        title = it.recipetitle,
                        image = it.image,
                        summary = it.summary,
                        spoonacularScore = it.spoonacularScore,
                        readyInMinutes = it.readyInMinutes,
                        pricePerServing = it.pricePerServing
                    )
                } ?: listOf()
            }
        }

        return listOf()
    }
}