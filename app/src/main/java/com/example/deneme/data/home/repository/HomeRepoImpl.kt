package com.example.deneme.data.home.repository

import com.example.deneme.data.home.local.HomeDatabase
import com.example.deneme.data.home.local.entity.RecipeEntity
import com.example.deneme.data.home.local.entity.TodayRecipeEntity
import com.example.deneme.data.home.remote.api.HomeApi
import com.example.deneme.domain.entity.Receipe
import java.text.SimpleDateFormat
import java.util.*

class HomeRepoImpl(private var homeapi: HomeApi, private var homeDatabase: HomeDatabase) : HomeRepo {
    override suspend fun getTodaysRecipe(): Receipe? {

        val currentDate = SimpleDateFormat("yyyy-MM-dd").format(Date())

        var localRecipe: RecipeEntity? = null

        homeDatabase.todayRecipeDao().get(currentDate)?.recipeId?.let{
            localRecipe = homeDatabase.receipeDao().get(it)
        }

        if(localRecipe == null){
            val response = homeapi.randomRecipes(1, "", false, "b3f9d443546c47a88cde2ef1250146a1")

            if(response.isSuccessful){

                val recipeDTO = response.body()?.recipes?.get(0)

                localRecipe = recipeDTO?.run {
                    RecipeEntity(
                        id = this.recipeid ?: 0,
                        title = this.recipetitle,
                        image = this.image,
                        summary = this.summary,
                        spoonacularScore = this.spoonacularScore,
                        readyInMinutes = this.readyInMinutes,
                        pricePerServing = this.pricePerServing
                    )
                }

                localRecipe?.let{
                    homeDatabase.receipeDao().insert(it)
                    homeDatabase.todayRecipeDao().insert(it.run { TodayRecipeEntity(
                        day = currentDate,
                        recipeId = it.id
                    ) })
                }
            }
        }

        return localRecipe?.run{
            Receipe(
                id = this.id,
                title = this.title,
                image = this.image,
                summary = this.summary,
                spoonacularScore = this.spoonacularScore,
                readyInMinutes = this.readyInMinutes,
                pricePerServing = this.pricePerServing
            )
        }
    }

    override suspend fun getCollections(page: Int): List<Receipe> {
        TODO("Not yet implemented")
    }


    override suspend fun getRandomRecipe(start: Int, end:Int): List<Receipe> {

        val recipeCount = homeDatabase.receipeDao().count()

        if(end < recipeCount){
            val recipeList = homeDatabase.receipeDao().getRandom(end - start)

            return recipeList?.map {
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
            val response = homeapi.randomRecipes(20, "", false, "b3f9d443546c47a88cde2ef1250146a1")

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
                    homeDatabase.receipeDao().insertAll(it)
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