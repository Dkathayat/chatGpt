package com.example.cryptoai.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

/**
 * Replace this with your real API integration.
 * Any API response field that indicates trend direction can be mapped to changePercent24h.
 */
class CryptoRepository {

    fun streamIndices(): Flow<List<CryptoIndex>> = flow {
        var seed = listOf(
            CryptoIndex("CMC200", "CMC Crypto 200", 812.40, -1.24),
            CryptoIndex("DEFI", "DeFi Composite", 1342.89, 2.91),
            CryptoIndex("NFT", "NFT Market Index", 221.52, -3.43),
            CryptoIndex("L1", "Layer-1 Index", 4021.10, 0.74),
            CryptoIndex("AI", "AI Tokens Index", 973.64, 4.06),
        )

        while (true) {
            seed = seed.map {
                val delta = Random.nextDouble(-2.0, 2.0)
                val nextChange = (it.changePercent24h + delta).coerceIn(-9.5, 9.5)
                it.copy(
                    currentPrice = (it.currentPrice * (1 + delta / 100)).coerceAtLeast(1.0),
                    changePercent24h = nextChange,
                )
            }
            emit(seed)
            delay(3500)
        }
    }
}
