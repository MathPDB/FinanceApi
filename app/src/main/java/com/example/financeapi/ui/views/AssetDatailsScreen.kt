package com.example.financeapi.ui.views

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.financeapi.data.Asset

@Composable
fun AssetDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: AssetDetailsScreenUiState,
    onCancel: () -> Unit
) {
    //Voltar
    BackHandler {
        onCancel()
    }

    when (uiState) {
        is AssetDetailsScreenUiState.Loading -> LoadingScreen()
        is AssetDetailsScreenUiState.Error -> ErrorScreen()
        is AssetDetailsScreenUiState.Success -> {
            val asset = uiState.asset

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = asset.name,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F1F20)
                        )
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Ticker
                Text(
                    text = "Ticker: ${asset.ticker}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2B4C7E)
                    )
                )

                // Moeda
                Text(
                    text = "Moeda: ${asset.currency}",
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color(0xFF606D80))
                )

                // Preço atual
                Text(
                    text = "Preço atual: \$${asset.price}",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F1F20)
                    )
                )
                //Variação
                Text(
                    text = "Variação do dia: ${asset.day_change}%",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (asset.day_change >= 0) Color(0xFF388E3C)
                        else Color.Red,
                        fontWeight = FontWeight.Medium
                    )
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 12.dp)
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Dados Diários",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F1F20)
                        )
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    //Preço abertura
                    Text(
                        text = "Abertura do dia: \$${asset.day_open}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF606D80))
                    )
                    //Preço teto dia
                    Text(
                        text = "Maior preço diário: \$${asset.day_high}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF606D80))
                    )
                    //Preço piso dia
                    Text(
                        text = "Menor preço diário: \$${asset.day_low}",
                        style = MaterialTheme.typography.bodyMedium.copy(color = Color(0xFF606D80))
                    )

                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAssetDetailsScreen() {
    val sampleAsset = Asset(
        ticker = "AAPL",
        name = "Apple Inc.",
        currency = "USD",
        price = 198.67,
        day_high = 200.00,
        day_low = 190.50,
        day_open = 192.00,
        day_change = 1.45
    )

    AssetDetailsScreen(
        uiState = AssetDetailsScreenUiState.Success(sampleAsset),
        onCancel = {} // No-op no preview
    )
}
