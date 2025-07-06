package com.axondragonscale.compose.demo.paracarousel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import composedemos.composeapp.generated.resources.Res
import composedemos.composeapp.generated.resources.pic1
import composedemos.composeapp.generated.resources.pic10
import composedemos.composeapp.generated.resources.pic2
import composedemos.composeapp.generated.resources.pic3
import composedemos.composeapp.generated.resources.pic4
import composedemos.composeapp.generated.resources.pic5
import composedemos.composeapp.generated.resources.pic6
import composedemos.composeapp.generated.resources.pic7
import composedemos.composeapp.generated.resources.pic8
import composedemos.composeapp.generated.resources.pic9
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.math.absoluteValue

/**
 * Created by Ronak Harkhani on 21/04/24
 */

private val defaultImages = listOf(
    Res.drawable.pic1,
    Res.drawable.pic2,
    Res.drawable.pic3,
    Res.drawable.pic4,
    Res.drawable.pic5,
    Res.drawable.pic6,
    Res.drawable.pic7,
    Res.drawable.pic8,
    Res.drawable.pic9,
    Res.drawable.pic10,
)

private const val FrameHeightFraction = 0.8f
private val FrameHorizontalPadding = 32.dp

@Composable
fun ParallaxCarouselV2(
    modifier: Modifier = Modifier,
    images: List<DrawableResource> = defaultImages,
) = Box {
    // Frame is the abstract area where image should be drawn
    // Width is the screen width i.e. image is drawn outside card as well
    // Height is the card height
    val container = LocalWindowInfo.current.containerSize
    val density = LocalDensity.current
    val frameWidth = with(density) { container.width.toDp() }
    val frameHeight = with(density) { container.height.toDp() } * FrameHeightFraction

    val pagerState = rememberPagerState { images.size }
    HorizontalPager(
        modifier = modifier.fillMaxSize(),
        state = pagerState
    ) { page ->
        Card(
            modifier = Modifier
                .padding(horizontal = FrameHorizontalPadding)
                .fillMaxHeight(FrameHeightFraction),
            shape = RoundedCornerShape(10),
            colors = CardDefaults.cardColors(),
            border = BorderStroke(8.dp, MaterialTheme.colorScheme.primary),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Image(
                modifier = Modifier
                    .wrapContentSize(unbounded = true) // To draw outside card constraints
                    .size(frameWidth, frameHeight)
                    .offset(frameWidth * pagerState.getOffsetDistanceInPages(page) * -1),
                bitmap = imageResource(images[page]),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)
            .fillMaxHeight((1f - FrameHeightFraction)/2),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        images.indices.forEach { index ->
            val offset = pagerState.indicatorOffsetForPage(index)
            Box(
                Modifier
                    .padding(4.dp)
                    .size(lerp(6.dp, 16.dp, offset))
                    .border(
                        width = 3.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape,
                    )
            )
        }
    }
}

private fun PagerState.indicatorOffsetForPage(index: Int) =
    1f - getOffsetDistanceInPages(index).coerceIn(-1f, 1f).absoluteValue

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface {
            ParallaxCarouselV2()
        }
    }
}
