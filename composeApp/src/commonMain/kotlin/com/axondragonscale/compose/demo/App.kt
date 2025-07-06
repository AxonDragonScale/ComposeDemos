package com.axondragonscale.compose.demo

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {

        val navController = rememberNavController()
        NavHost(
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
}