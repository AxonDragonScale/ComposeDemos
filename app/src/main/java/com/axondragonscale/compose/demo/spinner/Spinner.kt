package com.axondragonscale.compose.demo.spinner

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.AnchoredDraggableState
import androidx.compose.foundation.gestures.DraggableAnchors
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.anchoredDraggable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import com.axondragonscale.compose.demo.util.debugBorder

/**
 * Created by Ronak Harkhani on 25/10/24
 */

@Composable
fun Spinner(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var selectedIndex by remember { mutableIntStateOf(0) }
        val items = remember {
            List(30) { "Item $it" }
        }

        Spinner(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .fillMaxHeight(0.5f)
                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(12.dp)) // Remove border?
                .shadow(4.dp, RoundedCornerShape(12.dp))
                .background(
                    color = MaterialTheme.colorScheme.background,
                    shape = RoundedCornerShape(12.dp)
                ),
            items = items,
            onItemSelected = { selectedIndex = it },
            renderItem = SpinnerDefaults.StringItemRenderer,
        )

        Text(
            modifier = Modifier.padding(top = 48.dp),
            text = "Selected: ${items[selectedIndex]}"
        )
    }
}

@Composable
private fun <I> Spinner(
    modifier: Modifier = Modifier
        .shadow(2.dp, RoundedCornerShape(12.dp))
        .background(
            color = MaterialTheme.colorScheme.background,
            shape = RoundedCornerShape(12.dp)
        ),
    items: List<I>,
    onItemSelected: (index: Int) -> Unit,
    renderItem: @Composable (index: Int, item: I) -> Unit,
    itemHeight: Dp = SpinnerDefaults.ItemHeight,
    selectionLineWidthPercent: Float = SpinnerDefaults.SelectionLineWidthPercent,
    selectionLineStrokeWidth: Dp = SpinnerDefaults.SelectionLineStrokeWidth,
    selectionLineColor: Color = SpinnerDefaults.SelectionLineColor,
) {
    val isCountEven = remember(items) { items.size % 2 == 0 }
    val itemHeightPx = with(LocalDensity.current) { itemHeight.toPx() }

    val state = remember(items) {
        val initialValue = if (items.size % 2 == 0) items.size / 2 else items.size / 2 + 1
        onItemSelected(initialValue)
        AnchoredDraggableState(
            initialValue = initialValue,
            anchors = DraggableAnchors {
                val offset = if (isCountEven) itemHeightPx / 2 else 0f
                items.indices.forEach {
                    it.at(itemHeightPx * (items.size / 2 - it) - offset)
                }
            },
            confirmValueChange = { value ->
                onItemSelected(value)
                true
            }
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .anchoredDraggable(
                state = state,
                orientation = Orientation.Vertical,
            )
            .drawWithContent {
                drawContent()
                val offsetPercent = (1 - selectionLineWidthPercent) / 2f
                val startOffset = size.width * offsetPercent
                val endOffset = size.width * (1 - offsetPercent)
                val halfItemHeight = itemHeight.toPx() / 2f
                drawLine(
                    color = selectionLineColor,
                    start = Offset(startOffset, center.y - halfItemHeight),
                    end = Offset(endOffset, center.y - halfItemHeight),
                    strokeWidth = selectionLineStrokeWidth.toPx(),
                )
                drawLine(
                    color = selectionLineColor,
                    start = Offset(startOffset, center.y + halfItemHeight),
                    end = Offset(endOffset, center.y + halfItemHeight),
                    strokeWidth = selectionLineStrokeWidth.toPx(),
                )
            }
            .graphicsLayer {
                clip = true
            }
    ) {
        DraggableColumn(
            modifier = Modifier
                .fillMaxWidth()
                .offset { IntOffset(0, state.offset.toInt()) }

        ) {
            items.forEachIndexed { index, item ->
                Box(
                    modifier = Modifier.height(itemHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    renderItem(index, item)
                }
            }
        }
    }
}

@Composable
private fun DraggableColumn(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) = Layout(
    modifier = modifier,
    content = content,
) { measurables, constraints ->
    val placeables = measurables.map { it.measure(constraints) }
    val requiredHeight = placeables.sumOf { it.height }

    layout(constraints.maxWidth, requiredHeight) {
        var y = 0
        placeables.forEach { placeable ->
            placeable.placeRelative(0, y)
            y += placeable.height
        }
    }
}

object SpinnerDefaults {
    val ItemHeight = 48.dp

    val SelectionLineWidthPercent = 0.9f
    val SelectionLineStrokeWidth = 1.dp
    val SelectionLineColor: Color
        @Composable
        get() = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)

    val StringItemRenderer = @Composable { index: Int, item: String ->
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = item,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            Spinner()
        }
    }
}
