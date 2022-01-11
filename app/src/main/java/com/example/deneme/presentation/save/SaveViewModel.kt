package com.example.deneme.presentation.save

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.deneme.domain.common.BaseResult
import com.example.deneme.domain.entity.Receipe
import com.example.deneme.domain.entity.HomeEntity
import com.example.deneme.domain.usecase.HomeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SaveViewModel(private val saveUseCase: HomeUseCase) : ViewModel() {

    private val _saveState: MutableStateFlow<SaveUiState> = MutableStateFlow(SaveUiState.Idle)
    val saveState: StateFlow<SaveUiState> = _saveState

    fun getLists(){
        viewModelScope.launch {
            saveUseCase.execute()
                .onStart {
                    _saveState.value = SaveUiState.Loading
                }
                .catch { exception ->
                    _saveState.value = SaveUiState.Error(exception.message)
                }
                .collect { baseResult ->
                    when(baseResult){
                        //is BaseResult.Error -> state.value = LoginActivityState.ErrorLogin(baseResult.rawResponse)
                        is BaseResult.Success -> _saveState.value = SaveUiState.Success(baseResult.data)
                        //is BaseResult.UseCaseError -> state.value = LoginActivityState.UseCaseError(baseResult.rawResponse)
                    }
                }
        }
    }

    fun fetch(){
        if(_saveState.value != SaveUiState.Loading){
            viewModelScope.launch {
                saveUseCase.nextRandomRecipe()
                    .onStart {
                        _saveState.value = SaveUiState.Loading
                    }
                    .catch { exception ->
                        _saveState.value = SaveUiState.Error(exception.message)
                    }
                    .collect { baseResult ->
                        when(baseResult){
                            //is BaseResult.Error -> state.value = LoginActivityState.ErrorLogin(baseResult.rawResponse)
                            is BaseResult.Success -> _saveState.value = SaveUiState.PageSuccess(baseResult.data)
                            //is BaseResult.UseCaseError -> state.value = LoginActivityState.UseCaseError(baseResult.rawResponse)
                        }
                    }
            }
        }
    }
}

sealed class SaveUiState {
    data class Success(val saveEntity: HomeEntity): SaveUiState()
    data class Error(val error: String?): SaveUiState()
    data class PageSuccess(val recipeList: List<Receipe>): SaveUiState()
    object Idle : SaveUiState()
    object Loading : SaveUiState()
}