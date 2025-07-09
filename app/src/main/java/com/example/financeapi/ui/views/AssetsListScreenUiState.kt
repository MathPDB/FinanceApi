package com.example.financeapi.ui.views

import com.example.financeapi.data.Asset

//interface para poder usar loading, seccess e error
sealed interface AssetsListScreenUiState {
    object Loading : AssetsListScreenUiState
    data class Success(val assets: List<Asset>) : AssetsListScreenUiState
    object Error : AssetsListScreenUiState
}