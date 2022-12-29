package com.tfandkusu.template.compose.info

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.tfandkusu.template.compose.MyTopAppBar
import com.tfandkusu.template.compose.info.listitem.InfoListItem
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
        LazyColumn(Modifier.padding(padding)) {
            item {
                InfoListItem(stringResource(R.string.title_oss_license)) {
                }
            }
            item {
                InfoListItem(stringResource(R.string.title_about)) {
                }
            }
        }
    }
}
