package com.tfandkusu.template.compose.info.listitem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tfandkusu.template.info.compose.R
import com.tfandkusu.template.ui.theme.MyAppTheme

@Composable
fun InfoListItem(name: String, onClick: () -> Unit = {}) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
    ) {
        Text(
            text = name,
            maxLines = 1,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .padding(all = 16.dp)
        )
    }
}

@Composable
@Preview
fun InfoListItemPreview() {
    MyAppTheme {
        InfoListItem(name = stringResource(R.string.title_oss_license))
    }
}
