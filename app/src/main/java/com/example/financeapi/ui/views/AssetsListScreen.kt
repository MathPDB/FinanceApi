package com.example.financeapi.ui.views

import android.R.attr.onClick
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financeapi.data.Asset

/*Composable
fun AssetsListScreen(
    assetsViewModel: AssetsViewModel = viewModel()
) {
    val uiState by assetsViewModel.uiState.collectAsState()
    when (uiState) {
        is AssetsListUiState.Loading -> LoadingScreen()
        is AssetsListUiState.Success -> AssetsList((uiState as AssetsListUiState.Success).assets)
        is AssetsListUiState.Error -> ErrorScreen()
    }
}*/

@Composable
fun LoadingScreen() {

}

@Composable
fun ErrorScreen() {

}

@Composable
fun AssetsList(
    assets: List<Asset>,
    onAssetClick: (Asset) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize(),
        //.background(Color.LightGray),
        columns = GridCells.Fixed(2)
    ) {
        items(assets) { asset ->
            AssetEntry(
                asset = asset,
                onClick = { onAssetClick(asset) }
            )
        }
    }
}

@Composable
fun AssetEntry(
    asset: Asset,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .height(130.dp)
            .padding(4.dp)
            .clickable { onClick() },

        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
                .fillMaxHeight(),

            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = asset.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2B4C7E)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Ticker: ${asset.ticker}",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF606D80)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Preço: \$${asset.price}",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color(0xFF1F1F20),
                    fontWeight = FontWeight.SemiBold
                )
            )
        }
    }
}


