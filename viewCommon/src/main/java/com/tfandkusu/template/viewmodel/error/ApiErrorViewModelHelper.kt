package com.tfandkusu.template.viewmodel.error

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tfandkusu.template.error.NetworkErrorException
import com.tfandkusu.template.error.ServerErrorException
import com.tfandkusu.template.viewmodel.update

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

class ApiErrorViewModelHelper {

    private val _state = MutableLiveData(ApiErrorState())

    val state: LiveData<ApiErrorState> = _state

    fun catch(e: Throwable) {
        when (e) {
            is NetworkErrorException -> {
                _state.update {
                    copy(network = true)
                }
            }
            is ServerErrorException -> {
                _state.update {
                    copy(server = ApiServerError(e.code, e.httpMessage))
                }
            }
            else -> {
                _state.update {
                    copy(unknown = true)
                }
            }
        }
    }

    fun release() {
        _state.value = ApiErrorState()
    }
}
