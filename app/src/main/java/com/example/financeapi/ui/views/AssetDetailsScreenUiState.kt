package com.example.financeapi.ui.views

import com.example.financeapi.data.Asset

//interface para poder usar loading, seccess e error
sealed interface AssetDetailsScreenUiState {
    object Loading : AssetDetailsScreenUiState
    object Error : AssetDetailsScreenUiState
    data class Success(val asset: Asset) : AssetDetailsScreenUiState
}
