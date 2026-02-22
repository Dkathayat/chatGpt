package com.example.cryptoai.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoai.data.CryptoDashboardState
import com.example.cryptoai.data.CryptoRepository
import com.example.cryptoai.data.generateSignal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CryptoIndicesViewModel(
    private val repository: CryptoRepository = CryptoRepository(),
) : ViewModel() {

    private val _uiState = MutableStateFlow(CryptoDashboardState())
    val uiState: StateFlow<CryptoDashboardState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.streamIndices().collect { indices ->
                _uiState.update {
                    it.copy(
                        indices = indices,
                        signals = indices.map(::generateSignal),
                        isAiAnalyzing = true,
                    )
                }
            }
        }
    }
}
