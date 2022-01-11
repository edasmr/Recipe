package com.example.deneme.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deneme.domain.common.BaseResult
import com.example.deneme.domain.entity.HomeEntity
import com.example.deneme.domain.entity.Receipe
import com.example.deneme.domain.usecase.HomeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase) : ViewModel() {

    private val _homeState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Idle)
    val homeState: StateFlow<HomeUiState> = _homeState

    fun getLists(){
        viewModelScope.launch {
            homeUseCase.execute()
                .onStart {
                    _homeState.value = HomeUiState.Loading
                }
                .catch { exception ->
                    _homeState.value = HomeUiState.Error(exception.message)
                }
                .collect { baseResult ->
                    when(baseResult){
                        //is BaseResult.Error -> state.value = LoginActivityState.ErrorLogin(baseResult.rawResponse)
                        is BaseResult.Success -> _homeState.value = HomeUiState.Success(baseResult.data)
                        //is BaseResult.UseCaseError -> state.value = LoginActivityState.UseCaseError(baseResult.rawResponse)
                    }
                }
        }
    }

    fun fetch(){
        if(_homeState.value != HomeUiState.Loading){
            viewModelScope.launch {
                homeUseCase.nextRandomRecipe()
                    .onStart {
                        _homeState.value = HomeUiState.Loading
                    }
                    .catch { exception ->
                        _homeState.value = HomeUiState.Error(exception.message)
                    }
                    .collect { baseResult ->
                        when(baseResult){
                            //is BaseResult.Error -> state.value = LoginActivityState.ErrorLogin(baseResult.rawResponse)
                            is BaseResult.Success -> _homeState.value = HomeUiState.PageSuccess(baseResult.data)
                            //is BaseResult.UseCaseError -> state.value = LoginActivityState.UseCaseError(baseResult.rawResponse)
                        }
                    }
            }
        }
    }
}

sealed class HomeUiState {
    data class Success(val homeEntity: HomeEntity): HomeUiState() 
     
    data class Error(val error: String?): HomeUiState()
    data class PageSuccess(val recipeList: List<Receipe>): HomeUiState()
    object Idle : HomeUiState()
    object Loading : HomeUiState()
}