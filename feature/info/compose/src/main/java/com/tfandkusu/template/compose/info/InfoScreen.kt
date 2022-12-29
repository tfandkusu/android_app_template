package com.tfandkusu.template.compose.info

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tfandkusu.template.compose.MyTopAppBar
import com.tfandkusu.template.info.compose.R
import com.tfandkusu.template.viewmodel.info.InfoViewModel

@ExperimentalMaterial3Api
@Composable
fun InfoScreen(viewModel: InfoViewModel, finish: () -> Unit = {}) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = {
                    Text(stringResource(R.string.title_info))
                },
                hasBack = true,
                backContentDescription = stringResource(R.string.back_to_home_screen),
                onBackPressed = {
                    finish()
                }
            )
        }
    ) { padding ->
    }
}
