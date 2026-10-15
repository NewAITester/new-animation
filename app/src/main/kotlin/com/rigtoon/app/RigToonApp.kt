package com.rigtoon.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rigtoon.app.navigation.RigToonNavHost
import com.rigtoon.core.ui.theme.RigToonTheme
import com.rigtoon.feature.projects.ProjectsViewModel

/** ریشهٔ رابط Compose برنامه. */
@Composable
fun RigToonApp(
    projectsViewModel: ProjectsViewModel,
): Unit {
    RigToonTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            RigToonNavHost(projectsViewModel = projectsViewModel)
        }
    }
}
