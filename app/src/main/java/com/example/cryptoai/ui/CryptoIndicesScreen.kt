package com.example.cryptoai.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.cryptoai.data.AiSignal
import com.example.cryptoai.data.CryptoIndex
import com.example.cryptoai.data.SignalAction
import java.util.Locale

@Composable
fun CryptoIndicesScreen(viewModel: CryptoIndicesViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            Text(
                text = "Crypto Indices + AI Signals",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
            )
        }

        item {
            Text(
                text = if (uiState.isAiAnalyzing) {
                    "🧠 AI is continuously analyzing market direction..."
                } else {
                    "AI paused"
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFFE6F4EA), RoundedCornerShape(12.dp))
                    .padding(12.dp),
                color = Color(0xFF0E6245),
                fontWeight = FontWeight.Medium,
            )
        }

        item {
            Text(text = "Live Crypto Indices", style = MaterialTheme.typography.titleMedium)
        }

        items(uiState.indices) { index ->
            IndexCard(index)
        }

        item {
            Spacer(Modifier.height(8.dp))
            Text(text = "AI Buy/Sell Predictions", style = MaterialTheme.typography.titleMedium)
        }

        items(uiState.signals) { signal ->
            SignalCard(signal)
        }
    }
}

@Composable
private fun IndexCard(index: CryptoIndex) {
    val trendColor = if (index.isUp) Color(0xFF1B8F45) else Color(0xFFB3261E)

    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(index.displayName, fontWeight = FontWeight.SemiBold)
                Text(index.symbol, color = Color.DarkGray)
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "$${String.format(Locale.US, "%,.2f", index.currentPrice)}")
                Text(
                    text = String.format(Locale.US, "%+.2f%%", index.changePercent24h),
                    color = trendColor,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
private fun SignalCard(signal: AiSignal) {
    val signalColor = when (signal.action) {
        SignalAction.BUY -> Color(0xFF1B8F45)
        SignalAction.SELL -> Color(0xFFB3261E)
        SignalAction.HOLD -> Color(0xFF7A5E00)
    }

    Card(colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))) {
        Column(Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = signal.symbol, fontWeight = FontWeight.SemiBold)
                Text(text = signal.action.name, color = signalColor, fontWeight = FontWeight.Bold)
            }
            HorizontalDivider()
            Text(text = "Confidence: ${signal.confidence}%")
            Text(text = signal.reason, style = MaterialTheme.typography.bodySmall)
        }
    }
}
