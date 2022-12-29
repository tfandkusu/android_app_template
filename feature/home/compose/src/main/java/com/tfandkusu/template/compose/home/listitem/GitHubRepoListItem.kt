package com.tfandkusu.template.compose.home.listitem

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.text.format.DateFormat
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import com.tfandkusu.template.catalog.GitHubRepoCatalog
import com.tfandkusu.template.home.compose.R
import com.tfandkusu.template.model.GithubRepo
import com.tfandkusu.template.ui.theme.MyAppTheme
import com.tfandkusu.template.viewmodel.home.HomeStateItem
import java.util.Date

@Composable
fun GitHubRepoListItem(
    item: HomeStateItem,
    onClick: (id: Long, on: Boolean) -> Unit = { _, _ -> }
) {
    val repo = item.repo
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(repo.htmlUrl))
                context.startActivity(intent)
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                // Name
                Text(
                    text = repo.name,
                    modifier = Modifier
                        .weight(1f, false)
                        .padding(start = 16.dp, end = 12.dp, top = 16.dp, bottom = 16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                // Fork label
                if (repo.fork) {
                    Surface(
                        shape = MaterialTheme.shapes.extraSmall,
                        color = MaterialTheme.colorScheme.outline
                    ) {
                        Text(
                            text = stringResource(R.string.fork),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = colorResource(
                                    id = R.color.white
                                )
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            IconToggleButton(
                checked = repo.favorite,
                onCheckedChange = {
                    onClick(repo.id, it)
                }
            ) {
                val tint = animateColorAsState(
                    if (repo.favorite) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer
                    }
                )
                Icon(
                    Icons.Default.Favorite,
                    contentDescription =
                    if (repo.favorite) {
                        stringResource(R.string.favorite_on)
                    } else {
                        stringResource(R.string.favorite_off)
                    },
                    tint = tint.value
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
        }

        // Description
        if (repo.description.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                text = repo.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Language label
            if (repo.language.isNotEmpty()) {
                LanguageLabel(repo.language)
            }
            // Update time
            val format = DateFormat.getDateFormat(context)
            val dateString = format.format(repo.updatedAt)
            Text(
                dateString,
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.End
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Divider()
    }
}

@Composable
fun LanguageLabel(language: String) {
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
            HomeStateItem(GitHubRepoCatalog.getList().first())
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GitHubRepoListItemPreviewFavorite() {
    MyAppTheme {
        GitHubRepoListItem(
            HomeStateItem(GitHubRepoCatalog.getList().first().copy(favorite = true))
        )
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun GitHubRepoListItemPreviewDark() {
    MyAppTheme {
        GitHubRepoListItem(
            HomeStateItem(GitHubRepoCatalog.getList().first())
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GitHubRepoListItemPreviewLong() {
    MyAppTheme {
        GitHubRepoListItem(
            HomeStateItem(
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
                    true,
                    false
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = true)
fun GitHubRepoListItemNoDescription() {
    MyAppTheme {
        GitHubRepoListItem(
            HomeStateItem(
                GithubRepo(
                    1L,
                    "no_description",
                    "",
                    Date(),
                    "Kotlin",
                    "",
                    true,
                    false
                )
            )
        )
    }
}
