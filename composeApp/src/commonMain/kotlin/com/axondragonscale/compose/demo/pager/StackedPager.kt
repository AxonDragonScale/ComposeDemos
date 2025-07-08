package com.axondragonscale.compose.demo.pager

//import android.content.res.Configuration
//import android.graphics.RenderEffect
//import android.graphics.Shader
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.pager.HorizontalPager
//import androidx.compose.foundation.pager.VerticalPager
//import androidx.compose.foundation.pager.rememberPagerState
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.snapshotFlow
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.ImageBitmap
//import androidx.compose.ui.graphics.RenderEffect
//import androidx.compose.ui.graphics.asComposeRenderEffect
//import androidx.compose.ui.graphics.graphicsLayer
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.imageResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.zIndex
//import com.axondragonscale.compose.demo.R
//import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
//import composedemos.composeapp.generated.resources.Res
//import composedemos.composeapp.generated.resources.pic1
//import composedemos.composeapp.generated.resources.pic10
//import composedemos.composeapp.generated.resources.pic2
//import composedemos.composeapp.generated.resources.pic3
//import composedemos.composeapp.generated.resources.pic4
//import composedemos.composeapp.generated.resources.pic5
//import composedemos.composeapp.generated.resources.pic6
//import composedemos.composeapp.generated.resources.pic7
//import composedemos.composeapp.generated.resources.pic8
//import composedemos.composeapp.generated.resources.pic9
//import org.jetbrains.compose.resources.DrawableResource
//import org.jetbrains.compose.resources.imageResource
//import kotlin.math.absoluteValue
//
///**
// * Created by Ronak Harkhani on 28/04/24
// */
//
//@Composable
//fun StackedPager(modifier: Modifier = Modifier) {
//    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return
//
//    val images = listOf(
//        Res.drawable.pic1,
//        Res.drawable.pic2,
//        Res.drawable.pic3,
//        Res.drawable.pic4,
//        Res.drawable.pic5,
//        Res.drawable.pic6,
//        Res.drawable.pic7,
//        Res.drawable.pic8,
//        Res.drawable.pic9,
//        Res.drawable.pic10,
//    )
//
//    Box(
//        modifier = modifier.fillMaxSize(),
//        contentAlignment = Alignment.Center,
//    ) {
//        StackedPager(
//            modifier = Modifier.fillMaxSize(),
//            images = images
//        )
//    }
//}
//
///**
// * Known Issue:
// * The images beyond left and right images are not pre loaded even though beyondBoundsPageCount is 5
// * This leads to pop ins
// */
//
//@RequiresApi(Build.VERSION_CODES.S)
//@Composable
//fun StackedPager(
//    modifier: Modifier = Modifier,
//    images: List<DrawableResource>,
//) = Column(modifier = modifier) {
//    // pagerState.getOffsetDistanceInPages(page)
//    // If page is on Left -> -1
//    // If pages is the Center -> 0
//    // If page is on Right -> 1
//
//    val pagerState = rememberPagerState { images.size }
//    HorizontalPager(
//        modifier = Modifier
//            .weight(1f)
//            .padding(vertical = 64.dp),
//        state = pagerState,
//        pageSpacing = 1.dp,
//        beyondViewportPageCount = 2,
//    ) { page ->
//        val pageOffset = pagerState.getOffsetDistanceInPages(page)
//        // Calculate offset relative to current page
//        // 1f for cur page, 0 for left and right pages, -1 for left and right after that, ...
//        val curPivotOffset = (1 - pageOffset.absoluteValue)
//
//        Box(
//            modifier = Modifier
//                .zIndex(curPivotOffset * 10f)
//                .graphicsLayer {
//                    // Show part of the left and right pages
//                    translationX = -1 * size.width * pageOffset / 2
//
//                    // Alpha = 1f for cur page, 0.9f for rest
//                    // alpha = (9f + curPivotOffset) / 10f
//
//                    // Blur radius must be > 0
//                    val blurRadius = (pageOffset.absoluteValue * 10f).coerceAtLeast(0.1f)
//                    renderEffect = RenderEffect
//                        .createBlurEffect(blurRadius, blurRadius, Shader.TileMode.DECAL)
//                        .asComposeRenderEffect()
//
//                    // Scale = 1f for cur page, 0.9f for rest
//                    val scale = (9f + curPivotOffset) / 10f
//                    scaleX = scale
//                    scaleY = scale
//                }
//                .padding(horizontal = 64.dp)
//                .clip(RoundedCornerShape(24.dp)),
//            contentAlignment = Alignment.Center,
//        ) {
//            Image(
//                modifier = Modifier.fillMaxSize(),
//                bitmap = imageResource(images[page]),
//                contentScale = ContentScale.Crop,
//                contentDescription = null
//            )
//        }
//    }
//
//    val verticalPagerState = rememberPagerState { images.size }
//    VerticalPager(
//        modifier = Modifier
//            .weight(0.2f)
//            .padding(horizontal = 32.dp)
//            .padding(bottom = 32.dp),
//        state = verticalPagerState,
//    ) { page ->
//        Column(
//            modifier = Modifier.fillMaxHeight(),
//            verticalArrangement = Arrangement.Center,
//        ) {
//            Text(
//                text = "Image ${(page + 1)}",
//                style = MaterialTheme.typography.titleLarge,
//                fontWeight = FontWeight.Thin,
//            )
//
//            Text(
//                text = "Description ${(page + 1)}",
//                style = MaterialTheme.typography.labelLarge,
//                fontWeight = FontWeight.Bold,
//            )
//        }
//    }
//
//    LaunchedEffect(Unit) {
//        snapshotFlow {
//            pagerState.currentPage to pagerState.currentPageOffsetFraction
//        }.collect { (page, offset) ->
//            verticalPagerState.scrollToPage(page, offset)
//        }
//    }
//}
//
//@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun Preview() {
//    ComposeDemosTheme {
//        Surface {
//            StackedPager()
//        }
//    }
//}
