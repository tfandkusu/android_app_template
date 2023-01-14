package com.tfandkusu.template.viewmodel.error

import com.tfandkusu.template.error.NetworkErrorException
import com.tfandkusu.template.error.ServerErrorException
import com.tfandkusu.template.error.UnknownErrorException
import io.kotest.matchers.shouldBe
import org.junit.Test

class MapToApiErrorStateText {

    @Test
    fun networkError() {
        mapToApiErrorState(NetworkErrorException()) shouldBe ApiErrorState(network = true)
    }

    @Test
    fun serverError() {
        mapToApiErrorState(ServerErrorException(503, "Service Unavailable")) shouldBe ApiErrorState(
            server = ApiServerError(503, "Service Unavailable")
        )
    }

    @Test
    fun unknownError() {
        mapToApiErrorState(UnknownErrorException()) shouldBe ApiErrorState(unknown = true)
    }
}
