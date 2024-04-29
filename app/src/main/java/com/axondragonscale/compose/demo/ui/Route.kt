package com.axondragonscale.compose.demo.ui

/**
 * Created by Ronak Harkhani on 21/04/24
 */

sealed class Route(
    val route: String,
    val title: String,
    val description: String,
) {

    data object Home: Route(
        route = "home",
        title = "Home",
        description = ""
    )

    data object ParallaxCarousel: Route(
        route = "ParallaxCarousel",
        title = "Parallax Carousel",
        description = "An image carousel with parallax effect. Uses Canvas to render the images with custom size and offset calculations."
    )

    data object ParallaxCarouselV2: Route(
        route = "ParallaxCarouselV2",
        title = "Parallax Carousel V2",
        description = "A simplified version of Parallax Carousel. Doesn't use Canvas to render the images."
    )

    data object Typewriter: Route(
        route = "Typewriter",
        title = "Typewriter",
        description = "New text appears with a typewriter like animation."
    )

    data object Shimmer: Route(
        route = "Shimmer",
        title = "Shimmer",
        description = "Various Shimmer animations."
    )

    data object Loaders: Route(
        route = "Loaders",
        title = "Loaders",
        description = "Various Loader animations."
    )

    data object CircleLayout: Route(
        route = "CircleLayout",
        title = "Circle Layout",
        description = "A layout that arranges the child elements in a circle."
    )

    data object RadialList: Route(
        route = "RadialList",
        title = "Radial List",
        description = "A list with a radial curve."
    )

    data object AnimatedBorder: Route(
        route = "AnimatedBorder",
        title = "Animated Border",
        description = "A Card with Animated Borders and a modifier to apply animated border to any composable."
    )

    data object CardFlip: Route(
        route = "CardFlip",
        title = "Card Flip",
        description = "An Animation of a Card being flipped."
    )

    data object FluidFab: Route(
        route = "FluidFab",
        title = "Fluid FAB",
        description = "An expandable Floating Action Button that expands into 3 buttons with a fluid liquidy animation."
    )

    data object Counter: Route(
        route = "Counter",
        title = "Animated Counter",
        description = "A simple counter which animates the digits of the number when it changes."
    )

    data object CubePager: Route(
        route = "CubePager",
        title = "Cube Pager",
        description = "A Pager with cube like transition effect."
    )

    data object CircularRevealPager: Route(
        route = "CircularRevealPager",
        title = "Circular Reveal Pager",
        description = "A Pager where the next page is revealed in an expanding circular transition."
    )

    data object StackedPager: Route(
        route = "StackedPager",
        title = "Stacked Pager",
        description = "A layered pager, the current page is raised and prominent. The left and right pages appear blurred and scaled down behind it."
    )

    data object Shake: Route(
        route = "Shake",
        title = "Shake",
        description = "A modifier that gives a shake animation to the composable."
    )
}
