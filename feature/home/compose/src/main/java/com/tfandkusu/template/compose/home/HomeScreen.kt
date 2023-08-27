package com.tfandkusu.template.compose.home

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.compose.MyTopAppBar
import com.tfandkusu.template.compose.RunWhenLaunched
import com.tfandkusu.template.compose.home.listitem.GitHubRepoListItem
import com.tfandkusu.template.home.compose.R
import com.tfandkusu.template.ui.theme.MyAppTheme
import com.tfandkusu.template.view.error.ApiError
import com.tfandkusu.template.viewmodel.home.HomeEffect
import com.tfandkusu.template.viewmodel.home.HomeEvent
import com.tfandkusu.template.viewmodel.home.HomeState
import com.tfandkusu.template.viewmodel.home.HomeStateItem
import com.tfandkusu.template.viewmodel.home.HomeViewModel
import com.tfandkusu.template.viewmodel.use
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

private const val CONTENT_TYPE_REPO = 1

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalMaterial3Api
@Composable
fun HomeScreen(viewModel: HomeViewModel, callInfoScreen: () -> Unit = {}) {
    val (state, _, dispatch) = use(viewModel)
    RunWhenLaunched {
        dispatch(HomeEvent.OnCreate)
    }
    LaunchedEffect(Unit) {
        dispatch(HomeEvent.Load)
    }
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = {
                    Text(stringResource(R.string.app_name))
                },
                actions = {
                    IconButton(onClick = {
                        callInfoScreen()
                    }) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = stringResource(R.string.action_information)
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (state.error.noError()) {
            if (state.progress) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    stickyHeader {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            LanguageHorizontalBarChart()
                            Divider()
                        }
                    }
                    items(
                        state.items,
                        contentType = { CONTENT_TYPE_REPO },
                        key = { item -> item.repo.id }
                    ) {
                        GitHubRepoListItem(it) { id, on ->
                            dispatch(HomeEvent.Favorite(id, on))
                        }
                    }
                }
            }
        } else {
            ApiError(state.error, modifier = Modifier.padding(padding)) {
                viewModel.event(HomeEvent.Load)
            }
        }
    }
}

class HomeViewModelPreview(private val previewState: HomeState) : HomeViewModel {

    override fun createDefaultState() = previewState

    override val state: LiveData<HomeState>
        get() = MutableLiveData(createDefaultState())

    override val effect: Flow<HomeEffect>
        get() = flow {}

    override fun event(event: HomeEvent) {
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun HomeScreenPreviewProgress() {
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(HomeState()))
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview
fun HomeScreenPreviewList() {
    val repos = GitHubRepoCatalog.getList()
    val state = HomeState(
        progress = false,
        items = repos.map {
            HomeStateItem(it)
        }
    )
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(state))
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenPreviewDarkProgress() {
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(HomeState()))
    }
}

@ExperimentalMaterial3Api
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun HomeScreenPreviewDarkList() {
    val repos = GitHubRepoCatalog.getList()
    val state = HomeState(
        progress = false,
        items = repos.map {
            HomeStateItem(it)
        }
    )
    MyAppTheme {
        HomeScreen(HomeViewModelPreview(state))
    }
}
