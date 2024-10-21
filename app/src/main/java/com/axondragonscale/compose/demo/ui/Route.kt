package com.axondragonscale.compose.demo.ui

import androidx.compose.runtime.Composable
import com.axondragonscale.compose.demo.border.AnimatedBorder
import com.axondragonscale.compose.demo.bottombar.BottomBar
import com.axondragonscale.compose.demo.button.SegmentedButton
import com.axondragonscale.compose.demo.carousel.CardCarousel
import com.axondragonscale.compose.demo.circle.CircleLayout
import com.axondragonscale.compose.demo.counter.Counter
import com.axondragonscale.compose.demo.effect.GlowingEffect
import com.axondragonscale.compose.demo.flip.CardFlip
import com.axondragonscale.compose.demo.fab.FluidFab
import com.axondragonscale.compose.demo.fab.ExpandingFab
import com.axondragonscale.compose.demo.loader.HexagonLoader
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
import com.axondragonscale.compose.demo.slider.StretchySlider
import com.axondragonscale.compose.demo.typewriter.Typewriter

/**
 * Created by Ronak Harkhani on 21/04/24
 */

sealed class Route(
    val route: String,
    val title: String,
    val description: String,
    val composable: @Composable () -> Unit,
) {

    companion object {
        val All = Route::class.sealedSubclasses
            .filter { it != Home::class }
            .mapNotNull { it.objectInstance }
    }

    data object Home: Route(
        route = "home",
        title = "Home",
        description = "",
        composable = { },
    )

    data object ParallaxCarousel: Route(
        route = "ParallaxCarousel",
        title = "Parallax Carousel",
        description = "An image carousel with parallax effect. Uses Canvas to render the images with custom size and offset calculations.",
        composable = { ParallaxCarousel() },
    )

    data object ParallaxCarouselV2: Route(
        route = "ParallaxCarouselV2",
        title = "Parallax Carousel V2",
        description = "A simplified version of Parallax Carousel. Doesn't use Canvas to render the images.",
        composable = { ParallaxCarouselV2() }
    )

    data object Typewriter: Route(
        route = "Typewriter",
        title = "Typewriter",
        description = "New text appears with a typewriter like animation.",
        composable = { Typewriter() }
    )

    data object Shimmer: Route(
        route = "Shimmer",
        title = "Shimmer",
        description = "Various Shimmer animations.",
        composable = { Shimmer() }
    )

    data object Loaders: Route(
        route = "Loaders",
        title = "Loaders",
        description = "Various Loader animations.",
        composable = { Loaders() }
    )

    data object CircleLayout: Route(
        route = "CircleLayout",
        title = "Circle Layout",
        description = "A layout that arranges the child elements in a circle.",
        composable = { CircleLayout() }
    )

    data object RadialList: Route(
        route = "RadialList",
        title = "Radial List",
        description = "A list with a radial curve.",
        composable = { RadialList() }
    )

    data object AnimatedBorder: Route(
        route = "AnimatedBorder",
        title = "Animated Border",
        description = "A Card with Animated Borders and a modifier to apply animated border to any composable.",
        composable = { AnimatedBorder() }
    )

    data object CardFlip: Route(
        route = "CardFlip",
        title = "Card Flip",
        description = "An Animation of a Card being flipped.",
        composable = { CardFlip() }
    )

    data object FluidFab: Route(
        route = "FluidFab",
        title = "Fluid FAB",
        description = "An expandable Floating Action Button that expands into 3 buttons with a fluid liquidy animation.",
         composable = { FluidFab() }
    )

    data object Counter: Route(
        route = "Counter",
        title = "Animated Counter",
        description = "A simple counter which animates the digits of the number when it changes.",
        composable = { Counter() }
    )

    data object CubePager: Route(
        route = "CubePager",
        title = "Cube Pager",
        description = "A Pager with cube like transition effect.",
        composable = { CubePager() }
    )

    data object CircularRevealPager: Route(
        route = "CircularRevealPager",
        title = "Circular Reveal Pager",
        description = "A Pager where the next page is revealed in an expanding circular transition.",
        composable = { CircularRevealPager() }
    )

    data object StackedPager: Route(
        route = "StackedPager",
        title = "Stacked Pager",
        description = "A layered pager, the current page is raised and prominent. The left and right pages appear blurred and scaled down behind it.",
        composable = { StackedPager() }
    )

    data object Shake: Route(
        route = "Shake",
        title = "Shake",
        description = "A modifier that gives a shake animation to the composable.",
        composable = { Shake() }
    )

    data object ScribbleIndicator: Route(
        route = "ScribbleIndicator",
        title = "Scribble Tab Indicator",
        description = "A custom hand drawn looped Tab Indicator.",
        composable = { ScribbleIndicator() }
    )

    data object Shaders: Route(
        route = "Shaders",
        title = "Shaders",
        description = "A whole lot of fun shader effects.",
        composable = { Shaders() }
    )

    data object ShapesAndMorph: Route(
        route = "ShapesAndMorph",
        title = "Shapes and Morph",
        description = "Just a bit of Shapes and Morphing.",
        composable = { ShapesAndMorph() }
    )

    data object BottomBar: Route(
        route = "BottomBar",
        title = "Bottom Bars",
        description = "Some fun bottom bars.",
        composable = { BottomBar() }
    )

    data object ProgressBar: Route(
        route = "ProgressBar",
        title = "Progress Bars",
        description = "Bcoz progress shouldn't be like watching paint dry.",
        composable = { ProgressBar() }
    )

    data object NumberSlider: Route(
        route = "NumberSlider",
        title = "Number Slider",
        description = "Hmm, Wouldn't it be nice if the slider had numbers.",
        composable = { NumberSlider() }
    )

    data object CardCarousel: Route(
        route = "CardCarousel",
        title = "Card Carousel",
        description = "A carousel with cards",
        composable = { CardCarousel() }
    )

    data object ShimmeringText: Route(
        route = "ShimmeringText",
        title = "Shimmering Text",
        description = "A shimmering text effect.",
        composable = { ShimmeringText() }
    )

    data object SegmentedButton: Route(
        route = "SegmentedButton",
        title = "Segmented Button",
        description = "A Segmented Button with movement animation for selected item using Lookahead.",
        composable = { SegmentedButton() }
    )

    data object RampSlider: Route(
        route = "RampSlider",
        title = "Ramp Slider",
        description = "A slider where the portion being dragged is raised as a curve.",
        composable = { RampSlider() }
    )

    data object MorphLoaders: Route(
        route = "MorphLoaders",
        title = "Morph Loaders",
        description = "Fun loaders created with shape morphing.",
        composable = { MorphLoaders() }
    )

    data object ExpandingFab: Route(
        route = "ExpandingFab",
        title = "Expanding FAB",
        description = "A vertically expanding FAB.",
        composable = { ExpandingFab() }
    )

    data object StretchySlider: Route(
        route = "StretchySlider",
        title = "Stretchy Slider",
        description = "A slider that stretches when being dragged.",
        composable = { StretchySlider() }
    )

    data object HexagonLoader: Route(
        route = "HexagonLoader",
        title = "Hexagon Loader",
        description = "A custom drawn hexagon shaped Loader.",
        composable = { HexagonLoader() }
    )

    data object GlowEffect: Route(
        route = "GlowEffect",
        title = "Glow Effect",
        description = "A Glow Effect.",
        composable = { GlowingEffect() }
    )

}
