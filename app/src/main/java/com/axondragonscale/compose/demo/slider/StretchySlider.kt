package com.axondragonscale.compose.demo.slider

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import com.axondragonscale.compose.demo.util.thenIf
import kotlinx.coroutines.launch

/**
 * Created by Ronak Harkhani on 19/10/24
 */

// Inspired by - https://www.sinasamaki.com/implementing-overslide-slider-interaction-in-jetpack-compose/

@Composable
fun StretchySlider(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        var value by remember { mutableFloatStateOf(0.2f) }
        StretchySlider(
            modifier = Modifier.padding(horizontal = 100.dp),
            value = value,
            onValueChange = { value = it }
        )

        Spacer(modifier = Modifier.height(300.dp))

        var value2 by remember { mutableFloatStateOf(0.6f) }
        StretchySlider(
            modifier = Modifier.padding(horizontal = 100.dp),
            value = value2,
            onValueChange = { value2 = it },
            orientation = Orientation.Vertical,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StretchySlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    orientation: Orientation = Orientation.Horizontal,
) {
    val density = LocalDensity.current
    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = spring(stiffness = Spring.StiffnessHigh)
    )

    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var translateX by remember { mutableFloatStateOf(0f) }
    var transformOrigin by remember { mutableStateOf(TransformOrigin.Center) }

    Slider(
        modifier = modifier
            .thenIf(orientation == Orientation.Vertical) {
                this.rotate(-90f)
            }
            .graphicsLayer {
                this.transformOrigin = transformOrigin
                this.translationX = translateX
                this.scaleX = scaleX
                this.scaleY = scaleY
            },
        value = value,
        onValueChange = onValueChange,
        thumb = {},
        track = { sliderState ->
            val progress by remember {
                derivedStateOf {
                    (animatedValue - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                }
            }

            Box(
                modifier = Modifier
                    .trackOverslide(value) { overslide ->
                        transformOrigin = TransformOrigin(
                            pivotFractionX = if (progress < 0.5f) 2f else -1f,
                            pivotFractionY = 0.5f
                        )

                        translateX = overslide * with(density) { 16.dp.toPx() }
                        if (progress < 0.5) {
                            scaleX = 1f - overslide * 0.2f
                            scaleY = 1f + overslide * 0.2f
                        } else {
                            scaleX = 1f + overslide * 0.2f
                            scaleY = 1f - overslide * 0.2f
                        }
                    }
                    .fillMaxWidth()
                    .height(48.dp)
                    .trackBorderAndBackground()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(progress)
                        .fillMaxHeight()
                        .background(MaterialTheme.colorScheme.onSurface)
                )
            }
        }
    )
}

private fun Modifier.trackBorderAndBackground() = composed {
    this
        .border(
            width = 1.dp,
            brush = Brush.verticalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                    MaterialTheme.colorScheme.onSurface.copy(alpha = .2f),
                )
            ),
            shape = RoundedCornerShape(16.dp)
        )
        .padding(4.dp)
        .clip(shape = RoundedCornerShape(12.dp))
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                    MaterialTheme.colorScheme.onSurface.copy(alpha = .3f),
                )
            )
        )
}

private fun Modifier.trackOverslide(
    value: Float,
    onOverslideChange: (Float) -> Unit,
) = composed {
    val value by rememberUpdatedState(value)
    val scope = rememberCoroutineScope()
    val overslideAnimatable = remember { Animatable(0f, 0.0001f) }
    var length by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        snapshotFlow { overslideAnimatable.value }.collect {
            onOverslideChange(CustomEasing.transform(it / length))
        }
    }

    this
        .onSizeChanged { length = it.width.toFloat() }
        .pointerInput(Unit) {
            awaitEachGesture {
                val down = awaitFirstDown() // User touch
                awaitHorizontalTouchSlopOrCancellation(down.id) { _, _ -> } // Wait for min drag to be called a scroll

                var overslide = 0f
                horizontalDrag(down.id) { pointerInputChange ->
                    val deltaX = pointerInputChange.positionChange().x
                    overslide = when (value) {
                        0f -> (overslide + deltaX).coerceAtMost(0f)
                        1f -> (overslide + deltaX).coerceAtLeast(1f)
                        else -> 0f
                    }

                    scope.launch {
                        overslideAnimatable.animateTo(overslide)
                    }
                }

                // horizontal drag over. Remove overslide with a bounce
                scope.launch {
                    overslideAnimatable.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }
}

private val CustomEasing = CubicBezierEasing(0.5f, 0.5f, 1.0f, 0.25f)

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            StretchySlider()
        }
    }
}
