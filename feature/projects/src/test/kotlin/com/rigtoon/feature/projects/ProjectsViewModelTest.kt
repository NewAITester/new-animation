package com.rigtoon.feature.projects

import androidx.lifecycle.ViewModelStore
import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/** بررسی وضعیت اولیه و جلوگیری از ناوبری تکراری. */
@OptIn(ExperimentalCoroutinesApi::class)
class ProjectsViewModelTest {
    private val store: ViewModelStore = ViewModelStore()
    private lateinit var viewModel: ProjectsViewModel

    @Before
    fun setUp(): Unit {
        Dispatchers.setMain(StandardTestDispatcher())
        viewModel = ProjectsViewModel()
        store.put("projects", viewModel)
    }

    @After
    fun tearDown(): Unit {
        store.clear()
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateHasNoPendingNavigation(): Unit {
        assertFalse(viewModel.state.value.isAboutNavigationPending)
    }

    @Test
    fun repeatedRequestsEmitOneNavigationEffect(): Unit = runTest {
        viewModel.effects.test {
            viewModel.onAction(ProjectsAction.OpenAbout)
            viewModel.onAction(ProjectsAction.OpenAbout)
            assertEquals(ProjectsEffect.NavigateToAbout, awaitItem())
            runCurrent()
            assertTrue(viewModel.state.value.isAboutNavigationPending)
            expectNoEvents()
        }
    }

    @Test
    fun restartingScreenAllowsAnotherNavigation(): Unit = runTest {
        viewModel.effects.test {
            viewModel.onAction(ProjectsAction.OpenAbout)
            assertEquals(ProjectsEffect.NavigateToAbout, awaitItem())
            viewModel.onAction(ProjectsAction.ScreenStarted)
            runCurrent()
            assertFalse(viewModel.state.value.isAboutNavigationPending)
            viewModel.onAction(ProjectsAction.OpenAbout)
            assertEquals(ProjectsEffect.NavigateToAbout, awaitItem())
        }
    }
}
