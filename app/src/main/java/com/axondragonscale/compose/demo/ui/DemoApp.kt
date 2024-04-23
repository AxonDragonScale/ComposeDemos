package com.axondragonscale.compose.demo.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.axondragonscale.compose.demo.loader.Loaders
import com.axondragonscale.compose.demo.paracarousel.ParallaxCarousel
import com.axondragonscale.compose.demo.paracarousel.ParallaxCarouselV2
import com.axondragonscale.compose.demo.shimmer.Shimmer
import com.axondragonscale.compose.demo.typewriter.Typewriter
import com.axondragonscale.compose.demo.ui.home.Home

/**
 * Created by Ronak Harkhani on 21/04/24
 */

@Composable
fun DemoApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Route.Home.route
    ) {
        composable(route = Route.Home.route) {
            Home(navController = navController)
        }

        composable(route = Route.ParallaxCarousel.route) {
            ParallaxCarousel()
        }

        composable(route = Route.ParallaxCarouselV2.route) {
            ParallaxCarouselV2()
        }

        composable(route = Route.Typewriter.route) {
            Typewriter()
        }

        composable(route = Route.Shimmer.route) {
            Shimmer()
        }

        composable(route = Route.Loaders.route) {
            Loaders()
        }
    }
}
