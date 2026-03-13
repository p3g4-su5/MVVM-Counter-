package com.example.mvvmcounter

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun CounterScreen(
    viewModel: CounterViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    // Auto-scroll to the latest history entry
    LaunchedEffect(uiState.history.size) {
        if (uiState.history.isNotEmpty()) {
            listState.animateScrollToItem(uiState.history.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(48.dp))

        // ── Count display ─────────────────────────────────────────────────────
        val countColor = if (uiState.count > 0) Color(0xFF4CAF50) else Color(0xFF9E9E9E)

        Text(
            text       = uiState.count.toString(),
            fontSize   = 96.sp,
            fontWeight = FontWeight.Bold,
            color      = countColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ── Buttons ───────────────────────────────────────────────────────────
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment     = Alignment.CenterVertically
        ) {
            Button(
                onClick  = { viewModel.onEvent(CounterEvent.Decrement) },
                enabled  = uiState.count > 0,
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = Color(0xFFF44336),
                    disabledContainerColor = Color(0xFFBDBDBD)
                ),
                modifier = Modifier.size(72.dp)
            ) {
                Text(text = "-", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }

            Button(
                onClick  = { viewModel.onEvent(CounterEvent.Reset) },
                colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF607D8B)),
                modifier = Modifier.height(72.dp)
            ) {
                Text(text = "Reset", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }

            Button(
                onClick  = { viewModel.onEvent(CounterEvent.Increment) },
                colors   = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50)),
                modifier = Modifier.size(72.dp)
            ) {
                Text(text = "+", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // ── History header ────────────────────────────────────────────────────
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text          = "HISTORY",
                fontSize      = 12.sp,
                fontWeight    = FontWeight.SemiBold,
                color         = Color(0xFF616161),
                letterSpacing = 1.5.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            if (uiState.history.isNotEmpty()) {
                Text(
                    text     = "${uiState.history.size} entries",
                    fontSize = 12.sp,
                    color    = Color(0xFF9E9E9E)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // ── History list ──────────────────────────────────────────────────────
        if (uiState.history.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text       = "No history yet.\nTap + or − to begin.",
                    color      = Color(0xFFBDBDBD),
                    fontSize   = 14.sp,
                    textAlign  = TextAlign.Center,
                    lineHeight = 22.sp
                )
            }
        } else {
            LazyColumn(
                state               = listState,
                modifier            = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                itemsIndexed(uiState.history) { index, entry ->
                    HistoryItem(index = index + 1, entry = entry)
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }
}

// ── History row ───────────────────────────────────────────────────────────────
@Composable
private fun HistoryItem(index: Int, entry: String) {
    val isReset     = entry.startsWith("Reset")
    val isIncrement = entry.startsWith("+")

    val chipColor = when {
        isReset     -> Color(0xFFECEFF1)
        isIncrement -> Color(0xFFE8F5E9)
        else        -> Color(0xFFFFEBEE)
    }
    val textColor = when {
        isReset     -> Color(0xFF607D8B)
        isIncrement -> Color(0xFF2E7D32)
        else        -> Color(0xFFC62828)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(chipColor, RoundedCornerShape(8.dp))
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text       = "#$index",
            fontSize   = 11.sp,
            color      = Color(0xFFBDBDBD),
            modifier   = Modifier.width(32.dp),
            fontFamily = FontFamily.Monospace
        )
        Text(
            text       = entry,
            fontSize   = 14.sp,
            fontWeight = FontWeight.Medium,
            color      = textColor,
            fontFamily = FontFamily.Monospace
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CounterScreenPreview() {
    com.example.mvvmcounter.ui.theme.MVVMCounterTheme {
        CounterScreen()
    }
}