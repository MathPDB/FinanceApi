package com.example.financeapi

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.financeapi.ui.views.AssetDetailsScreen
import com.example.financeapi.ui.views.AssetDetailsScreenUiState
import com.example.financeapi.ui.views.AssetsList
import com.example.financeapi.ui.views.AssetsListScreenUiState
import com.example.financeapi.ui.views.AssetsViewModel
import com.example.financeapi.ui.views.ErrorScreen
import com.example.financeapi.ui.views.LoadingScreen

/* topBar sem travar a busca
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsListApp(
    appViewModel: AssetsViewModel = viewModel()
) {
    val uiState = appViewModel.uiState.collectAsState()
    var tickerInput by remember { mutableStateOf("") }
    val currentRoute = appViewModel.currentRoute.collectAsState()
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            // TopBar com campo de digitação
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = tickerInput,
                        onValueChange = { tickerInput = it },
                        placeholder = { Text("Digite o ticker...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                },
                actions = {
                    Button(
                        onClick = {
                            Log.d("AppDebug", "currentRoute = ${currentRoute.value}")
                            if (currentRoute.value == AppScreens.AssetsList.name) {
                                appViewModel.fetchAssets(tickerInput)
                            } else if (currentRoute.value == AppScreens.AssetDetails.name) {
                                appViewModel.onBackClick()
                                navController.popBackStack()
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2B4C7E), // Background do botão
                            contentColor = Color.White         // Cor Texto
                        )
                    ) {
                        if (currentRoute.value == AppScreens.AssetsList.name) {
                            Text("Buscar")
                        } else if (currentRoute.value == AppScreens.AssetDetails.name) {
                            Text("Voltar")
                        }
                    }
                }
            )
        }
    ) { innerPadding ->
        navHost(
            modifier = Modifier.padding(innerPadding),
            navController = navController,
            viewModel = appViewModel
        )
        /*when (uiState) {
            is AssetsListScreenUiState.Loading -> LoadingScreen()
            is AssetsListScreenUiState.Error -> ErrorScreen()
            is AssetsListScreenUiState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    // Conteúdo principal: lista de ativos
                    AssetsList((uiState as AssetsListScreenUiState.Success).assets)
                }
            }
        }*/
    }
}
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetsListApp(
    appViewModel: AssetsViewModel = viewModel()
) {
    val currentRoute = appViewModel.currentRoute.collectAsState()
    val navController = rememberNavController()
    val assetDetailsState = appViewModel.assetDetailsScreenUiState.collectAsState()

    // Controla o texto do campo
    val tickerInput = remember {
        mutableStateOf("")
    }

    // Atualiza o texto com base na tela atual
    if (currentRoute.value == AppScreens.AssetDetails.name &&
        assetDetailsState.value is AssetDetailsScreenUiState.Success
    ) {
        tickerInput.value =
            (assetDetailsState.value as AssetDetailsScreenUiState.Success).asset.ticker
    } else if (currentRoute.value == AppScreens.AssetsList.name) {
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = tickerInput.value,
                        onValueChange = {
                            if (currentRoute.value == AppScreens.AssetsList.name) {
                                tickerInput.value = it
                            }
                        },
                        placeholder = {
                            Text("Digite o ticker...")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 15.dp), // final do texto
                        singleLine = true,
                        shape = MaterialTheme.shapes.medium,
                        enabled = currentRoute.value == AppScreens.AssetsList.name
                    )
                },
                actions = {
                    Button(
                        onClick = {
                            Log.d("AppDebug", "currentRoute = ${currentRoute.value}")
                            if (currentRoute.value == AppScreens.AssetsList.name) {
                                appViewModel.fetchAssets(tickerInput.value)
                            } else if (currentRoute.value == AppScreens.AssetDetails.name) {
                                appViewModel.onBackClick()
                                navController.popBackStack()
                                tickerInput.value = ""
                            }
                        },
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .height(56.dp)
                            .width(95.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2B4C7E),
                            contentColor = Color.White
                        )
                    ) {
                        Text(

                            if (currentRoute.value == AppScreens.AssetsList.name)
                                "Buscar"
                            else
                                "Voltar",

                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        navHost(
            modifier = Modifier
                .padding(innerPadding),
            navController = navController,
            viewModel = appViewModel
        )
    }
}


@Composable
fun navHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    viewModel: AssetsViewModel,
) {
    NavHost(navController = navController, startDestination = AppScreens.AssetsList.name) {
        composable(AppScreens.AssetsList.name) {
            val uiState = viewModel.uiState.collectAsState()
            when (uiState.value) {
                is AssetsListScreenUiState.Loading -> LoadingScreen()
                is AssetsListScreenUiState.Error -> ErrorScreen()
                is AssetsListScreenUiState.Success -> {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        // Conteúdo principal
                        AssetsList(
                            assets = (uiState.value as AssetsListScreenUiState.Success).assets,
                            onAssetClick = { asset ->
                                viewModel.selectAsset(asset)
                                viewModel.setCurrentRoute(AppScreens.AssetDetails.name)
                                navController.navigate(AppScreens.AssetDetails.name)
                            }
                        )
                    }
                }
            }
        }
        composable(AppScreens.AssetDetails.name) {
            val uiState = viewModel.assetDetailsScreenUiState.collectAsState()
            when (uiState.value) {
                is AssetDetailsScreenUiState.Loading -> LoadingScreen()
                is AssetDetailsScreenUiState.Error -> ErrorScreen()
                is AssetDetailsScreenUiState.Success -> {
                    AssetDetailsScreen(
                        modifier = modifier,
                        uiState = uiState.value,
                        onCancel = {
                            viewModel.onBackClick()
                            navController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}

enum class AppScreens() {
    AssetsList,
    AssetDetails
}


/* Preview
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun PreviewAssetsListScreen() {
    val sampleAssets = listOf(
        Asset(
            name = "Apple Inc.", ticker = "AAPL", currency = "USD", price = 198.67,
            day_high = 199.67, day_low = 190.67, day_open = 190.00, day_change = 1.33
        ),
        Asset(
            name = "Tesla Inc.", ticker = "TSLA", currency = "USD", price = 255.12,
            day_high = 260.00, day_low = 250.00, day_open = 252.00, day_change = 0.89
        ),
        Asset(
            name = "Microsoft Corp.", ticker = "MSFT", currency = "USD", price = 345.78,
            day_high = 350.00, day_low = 340.00, day_open = 342.50, day_change = 1.21
        ),
        Asset(
            name = "Ford Motor Co.", ticker = "F", currency = "USD", price = 13.49,
            day_high = 14.00, day_low = 13.00, day_open = 13.20, day_change = 0.42
        )
    )

    var tickerInput by remember { mutableStateOf("AAPL,TSLA,MSFT,F") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = tickerInput,
                        onValueChange = { tickerInput = it },
                        placeholder = { Text("Digite o ticker...") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true
                    )
                },
                actions = {
                    Button(
                        onClick = { /* No-op no preview */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2B4C7E),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Buscar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            AssetsList(sampleAssets)
        }
    }
}
*/