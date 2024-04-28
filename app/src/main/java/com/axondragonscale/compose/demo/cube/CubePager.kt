package com.axondragonscale.compose.demo.cube

import android.content.res.Configuration
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.R
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlin.math.absoluteValue

/**
 * Created by Ronak Harkhani on 28/04/24
 */

@Composable
fun CubePager(modifier: Modifier = Modifier) {
    val images = listOf(
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

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        CubePager(
            modifier = Modifier.fillMaxSize(0.9f),
            images = images
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CubePager(
    modifier: Modifier = Modifier,
    images: List<Int>,
) = Box(
    modifier = modifier.fillMaxSize(),
    contentAlignment = Alignment.Center,
) {
    // pagerState.getOffsetFractionForPage(page)
    // If page is on Left -> 1
    // If pages is the Center -> 0
    // If page is on Right -> -1

    val pagerState = rememberPagerState { images.size }

    // Draw a shadow below + behind the pager
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .offset(y = 150.dp)
            .scale(scaleX = .6f, scaleY = .25f)
            .rotate(pagerState.getOffsetFractionForPage(0) * 90f)
            .blur(
                radius = 110.dp,
                edgeTreatment = BlurredEdgeTreatment.Unbounded,
            )
            .background(Color.Black.copy(alpha = .5f))
    )


    HorizontalPager(
        modifier = Modifier.aspectRatio(1f),
        state = pagerState,
    ) { page ->
        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .graphicsLayer {
                    val pageOffset = pagerState.getOffsetFractionForPage(page)
                    // Page position = right if value < 0 , left is value > 0
                    val isRightSide = pageOffset < 0f

                    // Rotate by 90 if its on the right, -90 if its on the left
                    val interpolationFactor =
                        FastOutLinearInEasing.transform(pageOffset.absoluteValue)
                    rotationY = interpolationFactor * if (isRightSide) 90f else -90f

                    // The pivot for rotation is their left edge for images on the right side
                    // and their right edge for images on the left side
                    transformOrigin = TransformOrigin(
                        pivotFractionX = if (isRightSide) 0f else 1f,
                        pivotFractionY = 0.5f
                    )
                }
                .drawWithContent {
                    drawContent() // draws the image

                    // Draw a rect to dim the image as it moves away from center
                    // Offset will be 0 at center, and go to -1/1 as image moves right/left
                    val offset = pagerState.getOffsetFractionForPage(page).absoluteValue
                    val dimmingAlpha = offset * 0.5f
                    drawRect(Color.Black.copy(alpha = dimmingAlpha))
                }
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                bitmap = ImageBitmap.imageResource(id = images[page]),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CubePagerPreview() {
    ComposeDemosTheme {
        Surface {
            CubePager()
        }
    }
}
