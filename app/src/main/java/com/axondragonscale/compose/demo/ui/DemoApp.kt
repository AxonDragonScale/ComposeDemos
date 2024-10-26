package com.axondragonscale.compose.demo.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 21/04/24
 */

@Composable
fun DemoApp() {
    var darkTheme by remember { mutableStateOf<Boolean?>(null) }
    ComposeDemosTheme(darkTheme = darkTheme ?: isSystemInDarkTheme()) {
        Surface {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = Route.Home.route,
                enterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                exitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left)
                },
                popEnterTransition = {
                    slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                },
                popExitTransition = {
                    slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right)
                }
            ) {
                composable(route = Route.Home.route) {
                    Home(
                        navController = navController,
                        darkTheme = darkTheme ?: isSystemInDarkTheme(),
                        onDarkThemeToggle = { darkTheme = it }
                    )
                }

                Route.All.forEach { route ->
                    composable(route = route.route) {
                        route.composable()
                    }
                }
            }
        }
    }
}
