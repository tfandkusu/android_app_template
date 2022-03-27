package com.tfandkusu.template.compose.home.listitem

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.text.format.DateFormat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.compose.ClickableItem
import com.tfandkusu.template.home.compose.R
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.ui.theme.MyAppTheme
import java.util.Date

@Composable
fun GitHubRepoListItem(repo: GithubRepo) {
    val context = LocalContext.current
    ClickableItem(
        onClick = {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(repo.htmlUrl)
            context.startActivity(intent)
        }
    ) {
        Row {
            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                modifier = Modifier.width(64.dp).height(64.dp),
                model = "https://placehold.jp/60/ccc/ffffff/192x192.png?text=" + repo.name[0],
                contentDescription = null
            )
            Column(modifier = Modifier.weight(1f)) {
                NameAndForkLabel(repo.name, repo.fork)
                Description(repo.description)
                LangageLabelAndUpdateTime(repo.language, repo.updatedAt)
            }
        }
    }
}

@Composable
private fun NameAndForkLabel(name: String, fork: Boolean) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        // Name
        Text(
            text = name,
            modifier = Modifier.weight(1f, false),
            style = TextStyle(
                fontSize = 16.sp,
                color = colorResource(R.color.textHE)
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Spacer(modifier = Modifier.width(12.dp))
        // Fork label
        if (fork) {
            Box(
                modifier = Modifier
                    .background(
                        color = colorResource(R.color.forkBackground),
                        shape = RoundedCornerShape(4.dp, 4.dp, 4.dp, 4.dp)
                    )
                    .padding(horizontal = 8.dp, vertical = 2.dp)
                    .height(18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.fork),
                    style = TextStyle(
                        color = colorResource(R.color.white),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
private fun Description(description: String) {
    if (description.isNotEmpty()) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            text = description,
            style = TextStyle(
                fontSize = 14.sp,
                color = colorResource(R.color.textME)
            ),
        )
    }
    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
private fun LangageLabelAndUpdateTime(language: String, updatedAt: Date) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Language label
        if (language.isNotEmpty()) {
            LanguageLabel(language)
        }
        // Update time
        val format = DateFormat.getDateFormat(context)
        val dateString = format.format(updatedAt)
        Text(
            dateString,
            modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
            style = TextStyle(
                fontSize = 12.sp,
                color = colorResource(R.color.textME),
                textAlign = TextAlign.End
            )
        )
    }
}

@Composable
private fun LanguageLabel(language: String) {
    val languages = stringArrayResource(R.array.programing_languages)
    val colorCodes = integerArrayResource(R.array.programing_language_colors)
    var color = colorResource(R.color.other)
    val languageIndex = languages.indexOf(language)
    if (languageIndex >= 0) {
        color = Color(colorCodes[languageIndex])
    }
    Box(modifier = Modifier.padding(horizontal = 16.dp)) {
        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(14.dp, 14.dp, 14.dp, 14.dp)
                )
                .padding(horizontal = 16.dp)
                .height(28.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = language,
                style = TextStyle(
                    color = colorResource(R.color.white),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun GitHubRepoListItemPreviewNormal() {
    MyAppTheme {
        GitHubRepoListItem(
            GitHubRepoCatalog.getList().first()
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun GitHubRepoListItemPreviewDark() {
    MyAppTheme {
        GitHubRepoListItem(
            GitHubRepoCatalog.getList().first()
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GitHubRepoListItemPreviewLong() {
    MyAppTheme {
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
                true
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GitHubRepoListItemNoDescription() {
    MyAppTheme {
        GitHubRepoListItem(
            GithubRepo(
                1L,
                "no_description",
                "",
                Date(),
                "Kotlin",
                "",
                true
            )
        )
    }
}
