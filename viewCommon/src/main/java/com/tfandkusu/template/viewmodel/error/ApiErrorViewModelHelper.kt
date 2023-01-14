package com.tfandkusu.template.viewmodel.error

import com.tfandkusu.template.error.NetworkErrorException
import com.tfandkusu.template.error.ServerErrorException

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

fun mapToApiErrorState(e: Throwable): ApiErrorState {
    return when (e) {
        is NetworkErrorException -> {
            ApiErrorState(network = true)
        }
        is ServerErrorException -> {
            ApiErrorState(server = ApiServerError(e.code, e.httpMessage))
        }
        else -> {
            ApiErrorState(unknown = true)
        }
    }
}
