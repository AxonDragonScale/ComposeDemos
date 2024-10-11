package com.axondragonscale.compose.demo.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

/**
 * Created by Ronak Harkhani on 21/04/24
 */

@Composable
fun DemoApp() {
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
            Home(navController = navController)
        }

        Route.All.forEach { route ->
            composable(route = route.route) {
                route.composable()
            }
        }
    }
}
