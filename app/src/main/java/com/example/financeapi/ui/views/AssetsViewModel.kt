package com.example.financeapi.ui.views

import android.util.Log

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapi.AppScreens
import com.example.financeapi.data.Asset
import com.example.financeapi.network.AssetApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
//import coil.network.HttpException
//import androidx.compose.runtime.State
//import androidx.compose.runtime.collectAsState
//import java.io.IOException

//enviado para arquivo próprio
/*sealed interface AssetsListScreenUiState {
    object Loading : AssetsListScreenUiState
    data class Success(val assets: List<Asset>) : AssetsListScreenUiState
    object Error : AssetsListScreenUiState

}*/

class AssetsViewModel : ViewModel() {

    private val _uiState: MutableStateFlow<AssetsListScreenUiState> =
        MutableStateFlow(AssetsListScreenUiState.Loading)
    val uiState: StateFlow<AssetsListScreenUiState> = _uiState.asStateFlow()

    private val _assetDetailsScreenUiState: MutableStateFlow<AssetDetailsScreenUiState> =
        MutableStateFlow(AssetDetailsScreenUiState.Loading)
    val assetDetailsScreenUiState: StateFlow<AssetDetailsScreenUiState> =
        _assetDetailsScreenUiState.asStateFlow()

    private val _currentRoute = MutableStateFlow(AppScreens.AssetsList.name)
    val currentRoute: StateFlow<String> = _currentRoute.asStateFlow()

    init {
        Log.d("AppDebug", "Init da ViewModel")
        fetchAssets("AAPL,TSLA,MSFT,F,NVDA,DIS,NFLX,DDOG,TRIP,INTC,HOOD,BB")//AAPL,TSLA,MSFT,F,NVDA,DIS,NFLX,DDOG,TRIP,INTC,HOOD,BB
    }

    fun fetchAssets(symbols: String) {
        Log.d("AppDebug", "fetchAssets chamado com symbols = $symbols")
        viewModelScope.launch {
            // Captura os ativos atuais antes de entrar no loading
            val currentAssets =
                (_uiState.value as? AssetsListScreenUiState.Success)?.assets ?: emptyList()

            // Exibe os ativos atuais enquanto busca os novos
            _uiState.value = AssetsListScreenUiState.Success(currentAssets)

            try {
                val allSymbols = symbols
                    .split(",")
                    .map { it.trim().uppercase() }
                    .filter { it.isNotBlank() }

                val batches = allSymbols.chunked(3)
                val allAssets = mutableListOf<Asset>()

                for (batch in batches) {
                    val queryParams = mapOf(
                        "symbols" to batch.joinToString(","),
                        "api_token" to "K6KJVbf5B75PKUzdS7ygiyYmn0fZ7ahWZQ4GFZhg"
                    )
                    val response = AssetApi.retrofitService.getAssets(queryParams)
                    allAssets.addAll(response.data)
                }

                // ANTIGO SEM INCLUSÃO
                // _uiState.value = AssetsListScreenUiState.Success(allAssets)

                // COM INCLUSÃO
                val updatedAssets = (currentAssets + allAssets).distinctBy { it.ticker }
                _uiState.value = AssetsListScreenUiState.Success(updatedAssets)

            } catch (e: Exception) {
                Log.e("AppDebug", "Erro no fetchAssets: ${e.message}", e)
                _uiState.value = AssetsListScreenUiState.Error
            }
        }
    }


    fun selectAsset(asset: Asset) {
        _assetDetailsScreenUiState.value = AssetDetailsScreenUiState.Success(asset)
    }

    fun setCurrentRoute(route: String) {
        _currentRoute.value = route
    }

    fun onBackClick() {
        _currentRoute.value = AppScreens.AssetsList.name
    }

    /*private fun getAssets() {
    viewModelScope.launch {
        try {
            _uiState.value =
                AssetsListUiState.Success(AssetApi.retrofitService.getAssets().data)

        } catch (e: IOException) {
            _uiState.value = AssetsListUiState.Error
        }/*catch (e: HttpException){
            _uiState.value = AssetsListUiState.Error
        }*/
    }
}*/

}