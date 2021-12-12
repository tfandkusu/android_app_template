package com.tfandkusu.template.view.home.listitem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tfandkusu.template.model.GithubRepo
import java.util.Date

@Composable
fun GitHubRepoListItem(repo: GithubRepo) {
    Column(Modifier.padding(16.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = repo.name,
            style = TextStyle(color = Color(0xff222222), fontSize = 16.sp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            text = repo.description,
            style = TextStyle(color = Color(0xff666666), fontSize = 14.sp),
        )
    }
}

@Composable
@Preview
fun GitHubRepoListItemPreviewNormal() {
    GitHubRepoListItem(
        GithubRepo(
            1L,
            "observe_room",
            listOf(
                "Check how to use Room to observe SQLite database",
                " and reflect the changes in the RecyclerView."
            ).joinToString(separator = ""),
            Date(),
            "Kotlin",
            "",
            false
        )
    )
}

@Composable
@Preview
fun GitHubRepoListItemPreviewLong() {
    GitHubRepoListItem(
        GithubRepo(
            1L,
            "long_repository_" + (0 until 10).joinToString(separator = "_") {
                "long"
            },
            listOf(
                "Check how to use Room to observe SQLite database",
                " and reflect the changes in the RecyclerView."
            ).joinToString(separator = ""),
            Date(),
            "Kotlin",
            "",
            false
        )
    )
}
