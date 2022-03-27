package com.tfandkusu.template.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ClickableItem(onClick: () -> Unit, content: @Composable () -> Unit) {
    Column(
        modifier = Modifier.clickable {
            onClick()
        }
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        content()
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
    }
}
