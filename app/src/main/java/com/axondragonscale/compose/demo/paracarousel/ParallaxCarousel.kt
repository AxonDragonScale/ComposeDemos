package com.axondragonscale.compose.demo.paracarousel

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.R
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlin.math.roundToInt

/**
 * Created by Ronak Harkhani on 21/04/24
 */

private val defaultImages = listOf(
    R.drawable.pic1,
    R.drawable.pic2,
    R.drawable.pic3,
    R.drawable.pic4,
    R.drawable.pic5,
    R.drawable.pic6,
    R.drawable.pic7,
    R.drawable.pic8,
    R.drawable.pic9,
    R.drawable.pic10,
)

private const val FrameHeightFraction = 0.8f
private val FrameHorizontalPadding = 32.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ParallaxCarousel(
    modifier: Modifier = Modifier,
    images: List<Int> = defaultImages,
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val screenWidth = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeight = with(density) { configuration.screenHeightDp.dp.toPx() }

    // Frame is the abstract area where image should be drawn
    // Width is the screen width i.e. image is drawn outside card as well
    // Height is the card height
    val frameWidth = screenWidth
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
            border = BorderStroke(8.dp, MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            // Canvas allows drawing outside the card bounds
            val image = ImageBitmap.imageResource(id = images[page])
            Canvas(modifier = Modifier.fillMaxSize()) {
                translate(left = pagerState.getOffsetFractionForPage(page) * frameWidth) {
                    val (imageSize, imageOffset) =
                        image.calcSizeAndOffset(frameWidth, frameHeight, density)
                    drawImage(image = image, dstOffset = imageOffset, dstSize = imageSize)
                }
            }
        }
    }
}

fun ImageBitmap.calcSizeAndOffset(
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

@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ParallaxCarouselPreview() {
    ComposeDemosTheme {
        Surface {
            ParallaxCarousel()
        }
    }
}
