package com.axondragonscale.compose.demo.paracarousel

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.R
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

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
fun ParallaxCarouselV2(
    modifier: Modifier = Modifier,
    images: List<Int> = defaultImages,
) {
    // Frame is the abstract area where image should be drawn
    // Width is the screen width i.e. image is drawn outside card as well
    // Height is the card height
    val configuration = LocalConfiguration.current
    val frameWidth = configuration.screenWidthDp.dp
    val frameHeight = configuration.screenHeightDp.dp * FrameHeightFraction

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
            Image(
                modifier = Modifier
                    .wrapContentSize(unbounded = true) // To draw outside card constraints
                    .size(frameWidth, frameHeight)
                    .offset(frameWidth * pagerState.getOffsetFractionForPage(page)),
                bitmap = ImageBitmap.imageResource(id = images[page]),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
    }
}

@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ParallaxCarouselV2Preview() {
    ComposeDemosTheme {
        Surface {
            ParallaxCarouselV2()
        }
    }
}
