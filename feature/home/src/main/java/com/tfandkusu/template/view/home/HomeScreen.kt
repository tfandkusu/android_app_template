package com.tfandkusu.template.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.MutableLiveData
import com.tfandkusu.template.home.R
import com.tfandkusu.template.viewmodel.home.HomeEffect
import com.tfandkusu.template.viewmodel.home.HomeEvent
import com.tfandkusu.template.viewmodel.home.HomeState
import com.tfandkusu.template.viewmodel.home.HomeViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    LaunchedEffect(Unit) {
        viewModel.event(HomeEvent.OnCreate)
        viewModel.event(HomeEvent.Load)
    }
    val state = viewModel.state.observeAsState(HomeState())
    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(stringResource(R.string.app_name))
            })
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (state.value.progress) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen(object : HomeViewModel {
        override val state = MutableLiveData(HomeState())

        override val effect: Flow<HomeEffect>
            get() = flow {}

        override fun event(event: HomeEvent) {
        }
    })
}
