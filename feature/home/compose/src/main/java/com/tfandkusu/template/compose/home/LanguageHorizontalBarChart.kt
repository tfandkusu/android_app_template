package com.tfandkusu.template.compose.home

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tfandkusu.template.home.compose.R
import com.tfandkusu.template.ui.theme.MyAppTheme

@Composable
fun LanguageHorizontalBarChart() {
    val color = colorResource(id = R.color.kotlin)
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(16.dp)
            .drawWithCache {
                onDrawBehind {
                    drawRect(
                        topLeft = Offset(x = 16f, y = 0f),
                        color = color,
                        size = Size(width = 100f, height = size.height)
                    )
                }
            }
    )
}

@Composable
@Preview
fun LanguageHorizontalBarChartPreview() {
    MyAppTheme {
        LanguageHorizontalBarChart()
    }
}
