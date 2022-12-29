package com.tfandkusu.template.compose.info

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tfandkusu.template.compose.MyTopAppBar
import com.tfandkusu.template.compose.info.listitem.InfoListItem
import com.tfandkusu.template.info.compose.R
import com.tfandkusu.template.ui.theme.MyAppTheme
import com.tfandkusu.template.viewmodel.info.InfoEffect
import com.tfandkusu.template.viewmodel.info.InfoEvent
import com.tfandkusu.template.viewmodel.info.InfoState
import com.tfandkusu.template.viewmodel.info.InfoViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalMaterial3Api
@Composable
fun InfoScreen(viewModel: InfoViewModel, finish: () -> Unit = {}, callOssLicense: () -> Unit = {}) {
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
                    callOssLicense()
                }
            }
            item {
                InfoListItem(stringResource(R.string.title_about)) {
                }
            }
        }
    }
}

class InfoViewModelPreview(private val previewState: InfoState) : InfoViewModel {

    override fun createDefaultState() = previewState

    override val state: LiveData<InfoState>
        get() = MutableLiveData(createDefaultState())

    override val effect: Flow<InfoEffect>
        get() = flow {}

    override fun event(event: InfoEvent) {
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun InfoScreenPreview() {
    val state = InfoState()
    val viewModel = InfoViewModelPreview(state)
    MyAppTheme {
        InfoScreen(viewModel)
    }
}
