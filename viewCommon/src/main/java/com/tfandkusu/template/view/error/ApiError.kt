package com.tfandkusu.template.view.error

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tfandkusu.template.ui.theme.MyAppTheme
import com.tfandkusu.template.viewcommon.R
import com.tfandkusu.template.viewmodel.error.ApiErrorState
import com.tfandkusu.template.viewmodel.error.ApiServerError

@Composable
fun ApiError(apiErrorState: ApiErrorState, modifier: Modifier = Modifier, reload: () -> Unit) {
    val errorMessage = if (apiErrorState.network) {
        stringResource(R.string.error_network)
    } else if (apiErrorState.server != null) {
        stringResource(
            R.string.error_server_error,
            apiErrorState.server.code,
            apiErrorState.server.message
        )
    } else {
        stringResource(R.string.error_unknown)
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .then(modifier)
    ) {
        Text(
            errorMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = reload
        ) {
            Text(stringResource(R.string.reload))
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ApiErrorPreviewNetwork() {
    MyAppTheme {
        ApiError(ApiErrorState(network = true)) {
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ApiErrorPreviewServerError() {
    MyAppTheme {
        ApiError(
            ApiErrorState(
                server = ApiServerError(
                    503,
                    "Service Unavailable"
                )
            )
        ) {
        }
    }
}
