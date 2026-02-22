package com.example.cryptoai.data

import kotlin.math.abs

data class CryptoIndex(
    val symbol: String,
    val displayName: String,
    val currentPrice: Double,
    val changePercent24h: Double,
) {
    val isUp: Boolean get() = changePercent24h >= 0
}

data class AiSignal(
    val symbol: String,
    val action: SignalAction,
    val confidence: Int,
    val reason: String,
)

enum class SignalAction {
    BUY,
    SELL,
    HOLD,
}

data class CryptoDashboardState(
    val indices: List<CryptoIndex> = emptyList(),
    val signals: List<AiSignal> = emptyList(),
    val isAiAnalyzing: Boolean = true,
)

fun generateSignal(index: CryptoIndex): AiSignal {
    val confidence = (55 + abs(index.changePercent24h) * 7).toInt().coerceAtMost(97)
    return when {
        index.changePercent24h <= -2.5 -> AiSignal(
            symbol = index.symbol,
            action = SignalAction.BUY,
            confidence = confidence,
            reason = "Momentum reversal probability detected after strong downside move.",
        )

        index.changePercent24h >= 2.5 -> AiSignal(
            symbol = index.symbol,
            action = SignalAction.SELL,
            confidence = confidence,
            reason = "Index appears overextended after accelerated upside expansion.",
        )

        else -> AiSignal(
            symbol = index.symbol,
            action = SignalAction.HOLD,
            confidence = 60,
            reason = "No high-confidence setup yet; AI keeps monitoring live flow.",
        )
    }
}
