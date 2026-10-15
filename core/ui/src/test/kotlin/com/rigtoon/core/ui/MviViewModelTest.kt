package com.rigtoon.core.ui

import androidx.lifecycle.ViewModelStore
import app.cash.turbine.test
import com.rigtoon.core.ui.mvi.MviViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

/** بررسی وضعیت، ترتیب پردازش و اثرهای تک‌مصرفی MVI. */
@OptIn(ExperimentalCoroutinesApi::class)
class MviViewModelTest {
    private val store: ViewModelStore = ViewModelStore()
    private lateinit var viewModel: CounterViewModel

    @Before
    fun setUp(): Unit {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = CounterViewModel()
        store.put("counter", viewModel)
    }

    @After
    fun tearDown(): Unit {
        store.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateIsAvailableImmediately(): Unit = runTest {
        viewModel.state.test {
            assertEquals(0, awaitItem())
            expectNoEvents()
        }
    }

    @Test
    fun actionUpdatesState(): Unit = runTest {
        viewModel.state.test {
            assertEquals(0, awaitItem())
            viewModel.onAction(CounterAction.Add(2))
            assertEquals(2, awaitItem())
        }
    }

    @Test
    fun actionsAreProcessedInOrder(): Unit = runTest {
        viewModel.onAction(CounterAction.Set(4))
        viewModel.onAction(CounterAction.Add(3))
        viewModel.onAction(CounterAction.Multiply(2))
        runCurrent()
        assertEquals(14, viewModel.state.value)
    }

    @Test
    fun effectIsDeliveredOnce(): Unit = runTest {
        viewModel.effects.test {
            viewModel.onAction(CounterAction.Notify)
            assertEquals(CounterEffect.Notified, awaitItem())
            runCurrent()
            expectNoEvents()
        }
        viewModel.effects.test {
            runCurrent()
            expectNoEvents()
        }
    }

    private sealed interface CounterAction {
        data class Set(val value: Int) : CounterAction

        data class Add(val value: Int) : CounterAction

        data class Multiply(val value: Int) : CounterAction

        data object Notify : CounterAction
    }

    private sealed interface CounterEffect {
        data object Notified : CounterEffect
    }

    private class CounterViewModel :
        MviViewModel<Int, CounterAction, CounterEffect>(0) {
        override suspend fun handleAction(action: CounterAction): Unit {
            when (action) {
                is CounterAction.Set -> updateState { action.value }
                is CounterAction.Add -> updateState { it + action.value }
                is CounterAction.Multiply -> updateState { it * action.value }
                CounterAction.Notify -> emitEffect(CounterEffect.Notified)
            }
        }
    }
}
