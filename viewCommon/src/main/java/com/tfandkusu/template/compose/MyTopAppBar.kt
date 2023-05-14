package com.tfandkusu.template.compose

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun MyTopAppBar(
    title: @Composable () -> Unit,
    hasBack: Boolean = false,
    backContentDescription: String = "",
    actions: @Composable RowScope.() -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    TopAppBar(
        title = title,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme
                .colorScheme
                .surfaceColorAtElevation(2.dp)
        ),
        navigationIcon = if (hasBack) {
            {
                IconButton(onClick = onBackPressed) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = backContentDescription
                    )
                }
            }
        } else {
            {}
        },
        actions = actions
    )
}
