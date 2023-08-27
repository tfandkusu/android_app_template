package com.tfandkusu.template.viewmodel.home

import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.Test

class HomeStateTest {

    @Test
    fun getLanguageRepositoryCountList() {
        val items = listOf(
            HomeStateItem(
                repo = mockk {
                    every { language } returns "Kotlin"
                }
            ),
            HomeStateItem(
                repo = mockk {
                    every { language } returns "Kotlin"
                }
            ),
            HomeStateItem(
                repo = mockk {
                    every { language } returns "Java"
                }
            ),
            HomeStateItem(
                repo = mockk {
                    every { language } returns "Swift"
                }
            ),
            HomeStateItem(
                repo = mockk {
                    every { language } returns "Go"
                }
            )
        )
        val state = HomeState(items = items)
        state.languageRepositoryCountList shouldBe listOf(
            LanguageRepositoryCount("Kotlin", 2),
            LanguageRepositoryCount("Java", 1),
            LanguageRepositoryCount("Other", 2)
        )
    }
}
