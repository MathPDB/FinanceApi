package com.example.financeapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
import com.example.financeapi.ui.theme.FinanceApiTheme

//import com.example.financeapi.ui.views.AssetsList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FinanceApiTheme {
                //Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                //AssetsListScreen()
                //}
                /*Surface(
                    modifier = Modifier.fillMaxSize()
                    //,color = MaterialTheme.colorScheme.background
                ) {*/
                AssetsListApp()
                //}
            }
        }
    }
}

