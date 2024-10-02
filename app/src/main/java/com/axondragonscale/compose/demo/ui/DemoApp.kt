package com.axondragonscale.compose.demo.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.axondragonscale.compose.demo.border.AnimatedBorder
import com.axondragonscale.compose.demo.bottombar.BottomBar
import com.axondragonscale.compose.demo.button.SegmentedButton
import com.axondragonscale.compose.demo.carousel.CardCarousel
import com.axondragonscale.compose.demo.circle.CircleLayout
import com.axondragonscale.compose.demo.counter.Counter
import com.axondragonscale.compose.demo.flip.CardFlip
import com.axondragonscale.compose.demo.fluildfab.FluidFab
import com.axondragonscale.compose.demo.loader.keyframe.Loaders
import com.axondragonscale.compose.demo.loader.morph.MorphLoaders
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
import com.axondragonscale.compose.demo.shimmer.ShimmeringText
import com.axondragonscale.compose.demo.slider.NumberSlider
import com.axondragonscale.compose.demo.slider.RampSlider
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

        Route.All.forEach { route ->
            composable(route = route.route) {
                route.composable()
            }
        }
    }
}
