package com.tfandkusu.template.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tfandkusu.template.home.R
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.util.parseUTC
import com.tfandkusu.template.view.home.listitem.GitHubRepoListItem
import com.tfandkusu.template.viewmodel.home.HomeEffect
import com.tfandkusu.template.viewmodel.home.HomeEvent
import com.tfandkusu.template.viewmodel.home.HomeState
import com.tfandkusu.template.viewmodel.home.HomeViewModel
import com.tfandkusu.template.viewmodel.useState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.event(HomeEvent.OnCreate)
        viewModel.event(HomeEvent.Load)
    }
    val state = useState(viewModel)
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.app_name))
            })
        }
    ) {
        if (state.progress) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn {
                state.repos.map {
                    item(key = it.id) {
                        GitHubRepoListItem(it)
                    }
                }
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

@Composable
@Preview
fun HomeScreenPreviewProgress() {
    HomeScreen(HomeViewModelPreview(HomeState()))
}

@Composable
@Preview
fun HomeScreenPreviewList() {
    val repo1 = GithubRepo(
        229475311L,
        "observe_room",
        listOf(
            "Check how to use Room to observe SQLite database ",
            "and reflect the changes in the RecyclerView."
        ).joinToString(separator = ""),
        parseUTC("2021-10-29T00:15:46Z"),
        "Kotlin",
        "https://github.com/tfandkusu/observe_room",
        false
    )
    val repo2 = GithubRepo(
        320900929L,
        "groupie_sticky_header_sample",
        "Sample app for sticky header on the groupie",
        parseUTC("2021-01-19T19:46:27Z"),
        "Java",
        "https://github.com/tfandkusu/groupie_sticky_header_sample",
        false
    )
    val repo3 = GithubRepo(
        343133709L,
        "conference-app-2021",
        "The Official App for DroidKaigi 2021",
        parseUTC("2021-09-21T16:56:04Z"),
        "Kotlin",
        "https://github.com/tfandkusu/conference-app-2021",
        true
    )
    val repos = listOf(repo1, repo2, repo3)
    val state = HomeState(
        progress = false,
        repos = repos
    )
    HomeScreen(HomeViewModelPreview(state))
}
