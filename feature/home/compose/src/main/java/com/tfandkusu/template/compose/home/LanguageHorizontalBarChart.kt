package com.tfandkusu.template.compose.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.integerArrayResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tfandkusu.template.home.compose.R
import com.tfandkusu.template.ui.theme.MyAppTheme
import com.tfandkusu.template.viewmodel.home.LanguageRepositoryCount

@Composable
fun LanguageHorizontalBarChart(
    totalRepositoryCount: Int,
    languageRepositoryCountList: List<LanguageRepositoryCount>
) {
    val languages = stringArrayResource(R.array.programing_languages).asList()
    val colors = integerArrayResource(R.array.programing_language_colors).map {
        Color(it)
    }
    val defaultColor = colorResource(R.color.other)
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .drawWithCache {
                onDrawBehind {
                    draw(
                        languages = languages,
                        colors = colors,
                        defaultColor = defaultColor,
                        totalRepositoryCount = totalRepositoryCount,
                        languageRepositoryCountList = languageRepositoryCountList
                    )
                }
            }
    )
}

private fun DrawScope.draw(
    languages: List<String>,
    colors: List<Color>,
    defaultColor: Color,
    totalRepositoryCount: Int,
    languageRepositoryCountList: List<LanguageRepositoryCount>
) {
    var v = 0f
    languageRepositoryCountList.forEach {
        val index = languages.indexOf(it.language)
        val color = if (index >= 0) {
            colors[index]
        } else {
            defaultColor
        }
        drawRect(
            topLeft = Offset(x = v * size.width / totalRepositoryCount, y = 0f),
            color = color,
            size = Size(width = it.count * size.width / totalRepositoryCount, height = size.height)
        )
        v += it.count
    }
}

@Composable
@Preview
fun LanguageHorizontalBarChartPreview() {
    MyAppTheme {
        LanguageHorizontalBarChart(
            totalRepositoryCount = 6,
            languageRepositoryCountList = listOf(
                LanguageRepositoryCount("Kotlin", 3),
                LanguageRepositoryCount("Java", 1),
                LanguageRepositoryCount("Other", 2)
            )
        )
    }
}
