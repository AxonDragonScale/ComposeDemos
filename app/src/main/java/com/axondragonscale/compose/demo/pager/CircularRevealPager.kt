package com.axondragonscale.compose.demo.pager

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.R
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlin.math.absoluteValue
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Created by Ronak Harkhani on 28/04/24
 */

@Composable
fun CircularRevealPager(modifier: Modifier = Modifier) {
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
        CircularRevealPager(
            modifier = Modifier
                .fillMaxSize(0.9f)
                .clip(RoundedCornerShape(24.dp)),
            images = images
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularRevealPager(
    modifier: Modifier = Modifier,
    images: List<Int>,
) {
    // pagerState.getOffsetDistanceInPages(page)
    // If page is on Left -> -1
    // If pages is the Center -> 0
    // If page is on Right -> 1
    // If currentPage is 0 and page is 3 -> 3

    val pagerState = rememberPagerState { images.size }
    var pointerOffsetY by remember { mutableStateOf(0f) }
    HorizontalPager(
        modifier = modifier.pointerInteropFilter {
            pointerOffsetY = it.y

            // Only the start y-axis offset is needed
            // So return false to not receive further updates
            false
        },
        state = pagerState,
    ) { page ->
        Box(
            modifier = Modifier.graphicsLayer {
                val pageOffset = pagerState.getOffsetDistanceInPages(page)

                // Make the page stay in place (compensates for the pager's scroll)
                // +ve offset means right side, so we translate -ve i.e left side
                translationX = size.width * pageOffset * -1

                // Give circular shape to clip and shadow
                shape = CircularRevealShape(
                    revealProgress = 1f - pageOffset,
                    origin = Offset(size.width, pointerOffsetY),
                    scale = 2f,
                )
                shadowElevation = 16f
                clip = true

                // Parallax Scaling
                // Scale the outgoing image from 1f -> Bigger
                // and the incoming image from Bigger -> 1f
                val scale = 1f + (pageOffset.absoluteValue * 0.2f)
                scaleX = scale
                scaleY = scale
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

private class CircularRevealShape(
    private val revealProgress: Float = 1f,
    private val origin: Offset? = null,
    private val scale: Float = 1f,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val center = Offset(x = origin?.x ?: size.center.x, y = origin?.y ?: size.center.y)
        val radius = sqrt(size.height.pow(2) + size.width.pow(2)) / 2

        val circlePath = Path().apply {
            addOval(Rect(center = center, radius = radius * scale * revealProgress))
        }
        return Outline.Generic(circlePath)
    }

}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            CircularRevealPager()
        }
    }
}
