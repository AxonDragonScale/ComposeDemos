package com.axondragonscale.compose.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.navigation.NavController

@Composable
@Preview
fun App() = MaterialTheme {
    val navController = rememberNavController()
    NavHost(
        modifier = Modifier.handleDesktopBackNavigation(navController),
        navController = navController,
        startDestination = Route.Home.route,
    ) {
        composable(route = Route.Home.route) {
            Home(
                navController = navController,
                darkTheme = false,
                onDarkThemeToggle = {}
            )
        }

        Route.Demos.forEach { route ->
            composable(route = route.route) {
                route.composable()
            }
        }
    }
}

// Temporary back press handling for desktop
@Composable
private fun Modifier.handleDesktopBackNavigation(
    navController: NavController
): Modifier = this.onPreviewKeyEvent { event ->
    if (
        (event.key == Key.Escape || event.key == Key.Backspace || event.key == Key.DirectionLeft) &&
        event.type == KeyEventType.KeyUp &&
        navController.previousBackStackEntry != null
    ) {
        navController.popBackStack()
        true
    } else {
        false
    }
}