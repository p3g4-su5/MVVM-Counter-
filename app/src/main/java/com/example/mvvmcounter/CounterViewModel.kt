package com.example.mvvmcounter

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// ── Sealed event class ────────────────────────────────────────────────────────
sealed class CounterEvent {
    object Increment : CounterEvent()
    object Decrement : CounterEvent()
    object Reset     : CounterEvent()
}

// ── UI State ──────────────────────────────────────────────────────────────────
data class CounterUiState(
    val count   : Int          = 0,
    val history : List<String> = emptyList()
)

// ── ViewModel ─────────────────────────────────────────────────────────────────
class CounterViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CounterUiState())
    val uiState: StateFlow<CounterUiState> = _uiState.asStateFlow()

    fun onEvent(event: CounterEvent) {
        _uiState.update { current ->
            when (event) {
                is CounterEvent.Increment -> {
                    val newCount = current.count + 1
                    current.copy(
                        count   = newCount,
                        history = current.history + "+1 → $newCount"
                    )
                }
                is CounterEvent.Decrement -> {
                    if (current.count == 0) return@update current
                    val newCount = current.count - 1
                    current.copy(
                        count   = newCount,
                        history = current.history + "-1 → $newCount"
                    )
                }
                is CounterEvent.Reset -> {
                    current.copy(
                        count   = 0,
                        history = emptyList()   // history cleared on reset
                    )
                }
            }
        }
    }
}