package com.tfandkusu.template.viewmodel

import com.tfandkusu.template.util.MyViewModelTestRule
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ReduxViewModelTest {

    @get:Rule
    val rule = MyViewModelTestRule(this)

    private sealed class TestEvent {
        object Load : TestEvent()

        object ClickInfo : TestEvent()
    }

    private sealed class TestAction {
        object StartLoad : TestAction()

        object ClickInfo : TestAction()
    }

    private data class TestState(
        val progress: Boolean = false
    )

    private sealed class TestEffect {
        object ShowInfo : TestEffect()
    }

    private class TestActionCreator : ActionCreator<TestEvent, TestAction> {
        override suspend fun event(event: TestEvent, dispatcher: Dispatcher<TestAction>) {
            when (event) {
                TestEvent.Load -> {
                    dispatcher.dispatch(TestAction.StartLoad)
                }
                TestEvent.ClickInfo -> {
                    dispatcher.dispatch(TestAction.ClickInfo)
                }
            }
        }
    }

    private class TestReducer : Reducer<TestAction, TestState, TestEffect> {
        override fun reduce(
            state: TestState,
            action: TestAction
        ): StateEffect<TestState, TestEffect> {
            return when (action) {
                TestAction.StartLoad -> {
                    StateEffect(state.copy(progress = true))
                }
                TestAction.ClickInfo -> {
                    StateEffect(state, TestEffect.ShowInfo)
                }
            }
        }
    }

    private interface TestViewModel : UnidirectionalViewModel<TestEvent, TestEffect, TestState>

    private class TestViewModelImpl(
        actionCreator: TestActionCreator,
        reducer: TestReducer
    ) : TestViewModel, ReduxViewModel<TestEvent, TestAction, TestState, TestEffect>(
        actionCreator,
        reducer
    ) {
        override fun createDefaultState(): TestState {
            return TestState()
        }
    }

    private lateinit var viewModel: TestViewModel

    @Before
    fun setUp() {
        viewModel = TestViewModelImpl(TestActionCreator(), TestReducer())
    }

    @ExperimentalCoroutinesApi
    @Test
    fun load() = runTest {
        viewModel.state.value shouldBe TestState()
        viewModel.event(TestEvent.Load)
        viewModel.state.value shouldBe TestState(progress = true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun clickInfo() = runTest {
        viewModel.state.value shouldBe TestState()
        viewModel.event(TestEvent.ClickInfo)
        viewModel.state.value shouldBe TestState()
        viewModel.effect.first() shouldBe TestEffect.ShowInfo
    }
}
