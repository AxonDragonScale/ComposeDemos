package com.axondragonscale.compose.demo.slider

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import kotlin.math.abs

/**
 * Created by Ronak Harkhani on 27/07/24
 */

@Composable
fun NumberSlider() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        var value by remember { mutableFloatStateOf(1f) }
        NumberSlider(
            modifier = Modifier.padding(24.dp),
            value = value,
            onValueChange = { value = it },
        )

        var value2 by remember { mutableFloatStateOf(0f) }
        NumberSlider(
            modifier = Modifier.padding(24.dp),
            value = value2,
            valueRange = 0..60,
            onValueChange = { value2 = it },
            step = 1f,
            stepSpacing = 12.dp,
            centerMarker = {
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(24.dp)
                        .background(color = MaterialTheme.colorScheme.onBackground)
                )
            },
            rulerMarker = { number, isSelected ->
                val isProminent = number.toInt() % 10 == 0
                    Box(
                        modifier = Modifier
                            .width(2.dp)
                            .height(if (isProminent) 18.dp else 12.dp)
                            .background(
                                if (isProminent)
                                    MaterialTheme.colorScheme.onBackground
                                else
                                    MaterialTheme.colorScheme.outline
                            )
                    )
            }
        )
    }
}

@Composable
private fun NumberSlider(
    modifier: Modifier = Modifier,
    valueRange: IntRange = 1..10,
    value: Float = valueRange.first.toFloat(),
    onValueChange: (Float) -> Unit,
    step: Float = 0.1f,
    stepSpacing: Dp = 8.dp,
    centerMarker: @Composable (BoxScope.() -> Unit) =
        { DefaultCenterMarker() },
    valueMarker: @Composable (number: Float) -> Unit =
        { number -> DefaultValueMarker(number) },
    rulerMarker: @Composable (BoxScope.(number: Float, isSelected: Boolean) -> Unit) =
        { number, isSelected ->
            if (number.isWholeNumber()) DefaultTextMarker(number.toInt(), isSelected)
            else DefaultRulerMarker(isSelected)
        },
) {
    val density = LocalDensity.current
    val scope = rememberCoroutineScope()
    val numbers = remember(valueRange, step) {
        List(size = valueRange.getNumberOfSteps(step)) { index ->
            valueRange.first + index * step
        }
    }

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = numbers.indexOf(value)
    )
    val flingBehavior = rememberSnapFlingBehavior(listState)
    val centerIndex by remember {
        derivedStateOf {
            listState.getCenterIndex().coerceIn(numbers.indices)
        }
    }

    LaunchedEffect(centerIndex) {
        val newCenterValue = numbers[centerIndex]
        if (newCenterValue != value) onValueChange(newCenterValue)
    }

    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        var width by remember { mutableStateOf(0.dp) }
        Box(
            modifier = Modifier.onGloballyPositioned {
                width = with(density) { it.size.width.toDp() }
            },
            contentAlignment = Alignment.Center
        ) {
            LazyRow(
                state = listState,
                flingBehavior = flingBehavior,
                contentPadding = PaddingValues(horizontal = width / 2),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(stepSpacing),
            ) {
                itemsIndexed(numbers, key = { _, number -> number }) { index, number ->
                    Box(
                        modifier = Modifier.noRippleClickable {
                            scope.launch { listState.centralizeItem(index) }
                        }
                    ) {
                        rulerMarker(number, centerIndex == index)
                    }
                }
            }

            centerMarker()
        }

        valueMarker(value)
    }

}

// Defaults

@Composable
private fun DefaultCenterMarker() {
    Box(
        modifier = Modifier
            .width(2.dp)
            .height(32.dp)
            .background(color = MaterialTheme.colorScheme.outline)
    )
}

@Composable
private fun DefaultValueMarker(number: Float) {
    Text(text = DecimalFormat("#.#").format(number))
}

@Composable
private fun DefaultTextMarker(number: Int, isSelected: Boolean) {
    Text(
        text = number.toString(),
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
    )
}

@Composable
private fun DefaultRulerMarker(isSelected: Boolean) {
    val alpha = if (isSelected) 1f else 0.5f
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(8.dp)
            .background(color = MaterialTheme.colorScheme.outline.copy(alpha = alpha))
    )
}


// Utils

private fun LazyListState.getCenterIndex(): Int {
    val center = layoutInfo.viewportSize.center.x + layoutInfo.viewportStartOffset
    var closestIndex = 0
    var closestDistance = Float.MAX_VALUE
    for (item in layoutInfo.visibleItemsInfo) {
        val itemCenter = item.offset + item.size / 2
        val distance = abs(itemCenter - center)
        if (distance < closestDistance) {
            closestDistance = distance.toFloat()
            closestIndex = item.index
        } else {
            break
        }
    }
    return closestIndex
}

private suspend fun LazyListState.centralizeItem(index: Int) {
    val item = layoutInfo.visibleItemsInfo.firstOrNull { it.index == index }
    if (item == null) animateScrollToItem(index)
    else {
        val itemCenter = item.offset + item.size / 2
        val viewportCenter = layoutInfo.viewportSize.center.x + layoutInfo.viewportStartOffset
        animateScrollBy((itemCenter - viewportCenter).toFloat())
    }
}

private fun IntRange.getNumberOfSteps(step: Float) = ((last - first) / step + 1).toInt()

private fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    this.clickable(
        indication = null,
        interactionSource = remember { MutableInteractionSource() },
        onClick = onClick
    )
}

private fun Float.isWholeNumber() = this % 1 == 0f

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            NumberSlider()
        }
    }
}
