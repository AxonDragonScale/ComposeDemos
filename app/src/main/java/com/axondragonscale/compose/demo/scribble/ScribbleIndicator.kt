package com.axondragonscale.compose.demo.scribble

import android.content.res.Configuration
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlin.math.ceil
import kotlin.math.floor

/**
 * Created by Ronak Harkhani on 29/04/24
 */

@Composable
fun ScribbleIndicator(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

        val items = (1..10).toList()
        val pagerState = rememberPagerState { items.size }
        val scope = rememberCoroutineScope()
        val tabSizes = remember { MutableList(items.size) { Size.Zero } }

        ScrollableTabRow(
            modifier = Modifier.padding(top = 8.dp),
            selectedTabIndex = pagerState.currentPage,
            edgePadding = 24.dp,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            divider = { },
            indicator = {
                ScribbleIndicator(
                    tabSizes = tabSizes,
                    pagerState = pagerState,
                )
            }
        ) {
            items.forEachIndexed { index, item ->
                Tab(
                    modifier = Modifier.onSizeChanged {
                        tabSizes[index] = Size(it.width.toFloat(), it.height.toFloat())
                    },
                    selected = index == pagerState.currentPage,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    interactionSource = NoInteraction,
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp),
                        text = "TAB $item",
                    )
                }
            }
        }

        HorizontalPager(state = pagerState) { page ->
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "PAGE ${items[page]}"
                )
            }
        }
    }
}

@Composable
fun ScribbleIndicator(
    modifier: Modifier = Modifier,
    tabSizes: List<Size>,
    pagerState: PagerState,
) {
    val indicatorColor = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp) // Same as the ScrollTabRow's edgePadding
            .drawBehind {
                val sectionLengths = MutableList(tabSizes.size) { 0f }
                val path = Path()
                var pathLength = 0f

                var origin = 0f
                tabSizes.forEachIndexed { index, size ->
                    val top = 10f
                    val bottom = size.height - 20f

                    if (index == 0) path.moveTo(0f, top)
                    path.scribbleLoop(size.width, size.height, top, bottom, origin)
                    origin += size.width

                    val pathMeasure = PathMeasure()
                    pathMeasure.setPath(path, false)

                    val currentPathLength = pathMeasure.length
                    sectionLengths[index] = currentPathLength - pathLength
                    pathLength = currentPathLength
                }

                val progress = -1 * pagerState.getOffsetDistanceInPages(0) // 0 -> lastIndex
                val pageProgress = progress - floor(progress) // progress of current page

                val start = floor(progress).toInt()
                val end = ceil(progress).toInt()

                val activePathLength =
                    sectionLengths[start] * (1 - pageProgress) + sectionLengths[end] * pageProgress

                val phaseOffset =
                    sectionLengths.take(start).sum() + sectionLengths[end] * pageProgress

                drawPath(
                    path = path,
                    color = indicatorColor,
                    style = Stroke(
                        width = 20f,
                        cap = StrokeCap.Round,
                        join = StrokeJoin.Round,
                        pathEffect = PathEffect.dashPathEffect(
                            intervals = floatArrayOf(activePathLength, pathLength),
                            phase = -phaseOffset // Negative coz the effect's mod behavior is weird
                        )
                    )
                )
            }
    )
}

fun Path.scribbleLoop(width: Float, height: Float, top: Float, bottom: Float, origin: Float) {
    quadraticTo(origin + width, top, origin + width, height / 2)
    quadraticTo(origin + width, bottom, origin + width / 2, bottom)
    quadraticTo(origin, bottom, origin, height / 2)
    quadraticTo(origin, top, origin + width, top)
}

object NoInteraction: MutableInteractionSource {
    override val interactions: Flow<Interaction> = flow {}
    override suspend fun emit(interaction: Interaction) {}
    override fun tryEmit(interaction: Interaction): Boolean = false
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            ScribbleIndicator()
        }
    }
}
