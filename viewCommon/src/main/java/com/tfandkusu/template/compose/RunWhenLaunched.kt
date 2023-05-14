package com.tfandkusu.template.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun RunWhenLaunched(black: () -> Unit) {
    var launched by rememberSaveable {
        mutableStateOf(true)
    }
    LaunchedEffect(Unit) {
        if (launched) {
            black()
            launched = false
        }
    }
}
