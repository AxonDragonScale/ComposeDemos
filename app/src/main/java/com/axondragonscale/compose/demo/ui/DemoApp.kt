package com.axondragonscale.compose.demo.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.axondragonscale.compose.demo.border.AnimatedBorder
import com.axondragonscale.compose.demo.bottombar.BottomBar
import com.axondragonscale.compose.demo.circle.CircleLayout
import com.axondragonscale.compose.demo.counter.Counter
import com.axondragonscale.compose.demo.flip.CardFlip
import com.axondragonscale.compose.demo.fluildfab.FluidFab
import com.axondragonscale.compose.demo.loader.Loaders
import com.axondragonscale.compose.demo.morph.ShapesAndMorph
import com.axondragonscale.compose.demo.pager.CircularRevealPager
import com.axondragonscale.compose.demo.pager.CubePager
import com.axondragonscale.compose.demo.pager.StackedPager
import com.axondragonscale.compose.demo.paracarousel.ParallaxCarousel
import com.axondragonscale.compose.demo.paracarousel.ParallaxCarouselV2
import com.axondragonscale.compose.demo.progressbar.ProgressBar
import com.axondragonscale.compose.demo.radial.RadialList
import com.axondragonscale.compose.demo.scribble.ScribbleIndicator
import com.axondragonscale.compose.demo.shaders.Shaders
import com.axondragonscale.compose.demo.shake.Shake
import com.axondragonscale.compose.demo.shimmer.Shimmer
import com.axondragonscale.compose.demo.slider.NumberSlider
import com.axondragonscale.compose.demo.typewriter.Typewriter

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

        composable(route = Route.CircleLayout.route) {
            CircleLayout()
        }

        composable(route = Route.RadialList.route) {
            RadialList()
        }

        composable(route = Route.AnimatedBorder.route) {
            AnimatedBorder()
        }

        composable(route = Route.CardFlip.route) {
            CardFlip()
        }

        composable(route = Route.FluidFab.route) {
            FluidFab()
        }

        composable(route = Route.Counter.route) {
            Counter()
        }

        composable(route = Route.CubePager.route) {
            CubePager()
        }

        composable(route = Route.CircularRevealPager.route) {
            CircularRevealPager()
        }

        composable(route = Route.StackedPager.route) {
            StackedPager()
        }

        composable(route = Route.Shake.route) {
            Shake()
        }

        composable(route = Route.ScribbleIndicator.route) {
            ScribbleIndicator()
        }

        composable(route = Route.Shaders.route) {
            Shaders()
        }

        composable(route = Route.ShapesAndMorph.route) {
            ShapesAndMorph()
        }

        composable(route = Route.BottomBar.route) {
            BottomBar()
        }

        composable(route = Route.ProgressBar.route) {
            ProgressBar()
        }

        composable(route = Route.NumberSlider.route) {
            NumberSlider()
        }
    }
}
