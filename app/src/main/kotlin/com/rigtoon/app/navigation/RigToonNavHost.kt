package com.rigtoon.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rigtoon.app.BuildConfig
import com.rigtoon.feature.projects.ProjectsRoute
import com.rigtoon.feature.projects.ProjectsViewModel

/** گراف فاز ۰؛ مقصد آغازین صفحهٔ پروژه‌ها است. */
@Composable
fun RigToonNavHost(
    projectsViewModel: ProjectsViewModel,
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
): Unit {
    NavHost(
        navController = navController,
        startDestination = Routes.PROJECTS,
        modifier = modifier,
    ) {
        composable(Routes.PROJECTS) {
            ProjectsRoute(
                viewModel = projectsViewModel,
                onOpenAbout = {
                    if (navController.currentDestination?.route == Routes.PROJECTS) {
                        navController.navigate(Routes.ABOUT) {
                            launchSingleTop = true
                        }
                    }
                },
            )
        }

        composable(Routes.ABOUT) {
            AboutScreen(
                versionName = BuildConfig.VERSION_NAME,
                onBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}
