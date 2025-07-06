package com.axondragonscale.compose.demo.paracarousel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
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
import kotlin.math.roundToInt

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
fun ParallaxCarousel(
    modifier: Modifier = Modifier,
    images: List<DrawableResource> = defaultImages,
) = Box {
    val density = LocalDensity.current
    val screenWidth = LocalWindowInfo.current.containerSize.width
    val screenHeight = LocalWindowInfo.current.containerSize.height

    // Frame is the abstract area where image should be drawn
    // Width is the screen width i.e. image is drawn outside card as well
    // Height is the card height
    val frameWidth = screenWidth.toFloat()
    val frameHeight = screenHeight * FrameHeightFraction

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
            // Canvas allows drawing outside the card bounds
            val image = imageResource(images[page])
            Canvas(modifier = Modifier.fillMaxSize()) {
                translate(left = -pagerState.getOffsetDistanceInPages(page) * frameWidth) {
                    val (imageSize, imageOffset) =
                        image.calcSizeAndOffset(frameWidth, frameHeight, density)
                    drawImage(image = image, dstOffset = imageOffset, dstSize = imageSize)
                }
            }
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
                    .rotate(135f * offset)
                    .size(lerp(10.dp, 14.dp, offset))
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        shape = RectangleShape,
                    )
            )
        }
    }
}

private fun PagerState.indicatorOffsetForPage(index: Int) =
    1f - getOffsetDistanceInPages(index).coerceIn(-1f, 1f).absoluteValue

private fun ImageBitmap.calcSizeAndOffset(
    frameWidth: Float,
    frameHeight: Float,
    density: Density,
): Pair<IntSize, IntOffset> {
    val frameAspectRatio = frameWidth / frameHeight
    val imageAspectRatio = width.toFloat() / height.toFloat()

    val imageSize = if (frameAspectRatio > imageAspectRatio)
        IntSize(frameWidth.roundToInt(), (frameWidth / imageAspectRatio).roundToInt())
    else
        IntSize((frameHeight * imageAspectRatio).roundToInt(), frameHeight.roundToInt())

    // Image will be drawn considering top left of the card as origin
    // Offset is required to
    // 1. Compensate for the padding on the left of the card
    // 2. Center the image based on diff of image size and frame size
    val horizontalPadding = with(density) { FrameHorizontalPadding.toPx() }
    val xOffset = horizontalPadding + maxOf((imageSize.width - frameWidth) / 2f, 0f)
    val yOffset = maxOf((imageSize.height - frameHeight) / 2f, 0f)
    val imageOffset = IntOffset(-xOffset.roundToInt(), -yOffset.roundToInt())

    return Pair(imageSize, imageOffset)
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface {
            ParallaxCarousel()
        }
    }
}
