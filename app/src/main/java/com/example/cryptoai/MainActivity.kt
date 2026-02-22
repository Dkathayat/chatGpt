package com.example.cryptoai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cryptoai.ui.CryptoIndicesScreen
import com.example.cryptoai.ui.CryptoIndicesViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val viewModel: CryptoIndicesViewModel = viewModel()
                CryptoIndicesScreen(viewModel = viewModel)
            }
        }
    }
}
