package com.tfandkusu.template.compose.info

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tfandkusu.template.compose.MyTopAppBar
import com.tfandkusu.template.compose.info.listitem.InfoListItem
import com.tfandkusu.template.info.compose.R
import com.tfandkusu.template.model.AppInfo
import com.tfandkusu.template.ui.theme.MyAppTheme
import com.tfandkusu.template.viewmodel.info.InfoEffect
import com.tfandkusu.template.viewmodel.info.InfoEvent
import com.tfandkusu.template.viewmodel.info.InfoState
import com.tfandkusu.template.viewmodel.info.InfoViewModel
import com.tfandkusu.template.viewmodel.use
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@ExperimentalMaterial3Api
@Composable
fun InfoScreen(
    viewModel: InfoViewModel,
    finish: () -> Unit = {},
    callOssLicensesActivity: () -> Unit = {}
) {
    val (state, effect, dispatch) = use(viewModel)
    LaunchedEffect(Unit) {
        effect.collect {
            when (it) {
                InfoEffect.CallOssLicensesActivity -> {
                    callOssLicensesActivity()
                }
            }
        }
    }
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
                    dispatch(InfoEvent.OnClickOssLicense)
                }
            }
            item {
                InfoListItem(stringResource(R.string.title_about)) {
                    dispatch(InfoEvent.OnClickAbout)
                }
            }
        }
    }
    state.numberOfStarts?.let {
        val sb = StringBuilder()
        sb.append(stringResource(R.string.app_name))
        sb.append('\n')
        sb.append(stringResource(R.string.version))
        sb.append(' ')
        sb.append(AppInfo.versionName)
        sb.append("\n\n")
        sb.append(stringResource(R.string.number_of_starts, it))
        sb.append("\n\n")
        sb.append(stringResource(R.string.copyright))
        sb.append(' ')
        sb.append(stringResource(R.string.author_name))
        AlertDialog(onDismissRequest = { dispatch(InfoEvent.CloseAbout) }, title = {
            Text(stringResource(R.string.title_about))
        }, text = {
                Text(sb.toString())
            }, confirmButton = {
                TextButton(
                    onClick = {
                        dispatch(InfoEvent.CloseAbout)
                    }
                ) {
                    Text(stringResource(R.string.ok))
                }
            })
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

@ExperimentalMaterial3Api
@Composable
@Preview
fun InfoScreenPreviewAbout() {
    val state = InfoState(numberOfStarts = 3)
    val viewModel = InfoViewModelPreview(state)
    MyAppTheme {
        InfoScreen(viewModel)
    }
}
