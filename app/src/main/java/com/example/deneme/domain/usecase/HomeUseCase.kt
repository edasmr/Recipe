package com.example.deneme.domain.usecase

import com.example.deneme.data.home.remote.dto.RandomRecipeRes
import com.example.deneme.data.home.repository.HomeRepo
import com.example.deneme.domain.common.BaseResult
import com.example.deneme.domain.entity.HomeEntity
import com.example.deneme.domain.entity.Receipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HomeUseCase(private val homeRepository: HomeRepo) {

    val pageSize = 20
    var page = 0

    fun execute() : Flow<BaseResult<HomeEntity, RandomRecipeRes>> {
        return flow {

            emit(
                BaseResult.Success(
                    HomeEntity(
                        todaysRecipe = homeRepository.getTodaysRecipe(),
                        randomRecipe = homeRepository.getRandomRecipe(0, pageSize)
                    )
                )
            )
        }
    }

    suspend fun nextRandomRecipe() : Flow<BaseResult<List<Receipe>, RandomRecipeRes>> {
        ++page
        return flow {

            emit(
                BaseResult.Success(
                    homeRepository.getRandomRecipe(page * pageSize, (page + 1) * pageSize)
                )
            )
        }
    }

}