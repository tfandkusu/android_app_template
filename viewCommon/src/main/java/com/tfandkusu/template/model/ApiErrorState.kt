package com.tfandkusu.template.model

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.tfandkusu.template.viewmodel.ApiErrorViewModelHelper

data class ApiServerError(
    val code: Int,
    val message: String
)

data class ApiErrorState(
    val network: Boolean = false,
    val server: ApiServerError? = null,
    val unknown: Boolean = false
) {
    fun noError(): Boolean {
        return !network && server == null && !unknown
    }
}

@Composable
fun useErrorState(apiErrorViewModelHelper: ApiErrorViewModelHelper): ApiErrorState {
    return apiErrorViewModelHelper.state.observeAsState(ApiErrorState()).value
}
