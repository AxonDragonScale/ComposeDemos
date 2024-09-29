@file:OptIn(ExperimentalMaterial3Api::class)

package com.axondragonscale.compose.demo.slider

import android.content.res.Configuration
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.zIndex
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import java.text.DecimalFormat
import kotlin.math.roundToInt

/**
 * Created by Ronak Harkhani on 30/09/24
 */

@Composable
fun RampSlider(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
    ) {
        var value by remember { mutableFloatStateOf(0.5f) }
        RampSlider(
            modifier = Modifier.padding(32.dp),
            value = value,
            onValueChange = { value = it },
        )

        var value2 by remember { mutableFloatStateOf(0.5f) }
        RampSlider(
            modifier = Modifier.padding(32.dp),
            value = value2,
            onValueChange = { value2 = it },
            steps = 20,
        )
    }
}

// Reference - https://www.sinasamaki.com/custom-material-3-sliders-in-jetpack-compose/

@Composable
private fun RampSlider(
    modifier: Modifier = Modifier,
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    steps: Int = 0,
    thumbSize: Dp = 64.dp,
    thumbText: (Float) -> String = { DecimalFormat("#.##").format(it) },
) {
    val animatedValue by animateFloatAsState(
        targetValue = value,
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "animatedValue",
    )

    val interactionSource = remember { MutableInteractionSource() }
    val isDragging by interactionSource.collectIsDraggedAsState()
    val thumbHeightOffset by animateDpAsState(
        targetValue = if (isDragging) 36.dp else 0.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMediumLow,
        ),
        label = "thumbHeightOffset",
    )

    Slider(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        valueRange = valueRange,
        steps = steps,
        interactionSource = interactionSource,
        thumb = {}, // We will draw thumb ourselves in the track itself
        track = { sliderState ->
            val progress by remember {
                derivedStateOf {
                    val activeRange = animatedValue - sliderState.valueRange.start
                    val totalRange = sliderState.valueRange.endInclusive - sliderState.valueRange.start
                    activeRange / totalRange
                }
            }

            var trackWidth by remember { mutableIntStateOf(0) }
            Box(
                modifier = Modifier
                    .height(thumbSize)
                    .fillMaxWidth()
                    .onSizeChanged { trackWidth = it.width }
            ) {
                Thumb(
                    animatedValue = animatedValue,
                    progress = progress,
                    trackWidth = trackWidth,
                    thumbSize = thumbSize,
                    thumbText = thumbText,
                    thumbHeightOffset = thumbHeightOffset,
                )

                // Track
                val strokeColor = MaterialTheme.colorScheme.onSurface
                Box(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .fillMaxWidth()
                        .drawWithCache {
                            onDrawBehind {
                                drawTrack(
                                    progress = progress,
                                    thumbHeightOffset = thumbHeightOffset.toPx(),
                                    steps = steps,
                                    color = strokeColor
                                )
                            }
                        }
                )
            }
        },
    )
}

@Composable
private fun BoxScope.Thumb(
    modifier: Modifier = Modifier,
    animatedValue: Float,
    progress: Float,
    trackWidth: Int,
    thumbSize: Dp,
    thumbText: (Float) -> String,
    thumbHeightOffset: Dp,
) = Box(
    modifier = modifier
        .zIndex(10f) // To make sure that the thumb is drawn on top of the track
        .align(Alignment.CenterStart)
        .offset {
            IntOffset(
                x = lerp(
                    start = -(thumbSize / 2).toPx(), // Half the thumb is before start
                    stop = trackWidth - (thumbSize / 2).toPx(), // Half the thumb is after end
                    fraction = progress
                ).roundToInt(),
                y = -thumbHeightOffset.toPx().roundToInt()
            )
        }
        .size(thumbSize)
        .padding(8.dp)
        .shadow(elevation = 8.dp, shape = CircleShape)
        .background(color = MaterialTheme.colorScheme.primary, shape = CircleShape),
    contentAlignment = Alignment.Center,
) {
    Text(
        text = thumbText(animatedValue),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onPrimary
    )
}

private fun DrawScope.drawTrack(
    progress: Float,
    thumbHeightOffset: Float,
    steps: Int,
    color: Color,
) {
    val path = Path()
    val activeWidth = size.width * progress
    val trackHeight = size.height / 2   // Track is centered in the Box
    val rampHeight = trackHeight - thumbHeightOffset
    val rampWidth = 64.dp.toPx() // Width of the ramp on one side of the top

    val beyondBounds = size.width * 2   // Some offscreen width

    // Far beyond the right edge
    path.moveTo(x = beyondBounds, y = trackHeight)
    // Line from right to the end of right ramp
    path.lineTo(x = activeWidth + rampWidth, y = trackHeight)
    // Curve to top of the right ramp
    path.cubicTo(
        x1 = activeWidth + (rampWidth / 2), y1 = trackHeight,   // control point for start
        x2 = activeWidth + (rampWidth / 2), y2 = rampHeight,    // control point for end
        x3 = activeWidth,                   y3 = rampHeight,    // end of curve
    )
    // Curve from top to the start of the left ramp
    path.cubicTo(
        x1 = activeWidth - (rampWidth / 2), y1 = rampHeight,    // control point for start
        x2 = activeWidth - (rampWidth / 2), y2 = trackHeight,   // control point for end
        x3 = activeWidth - rampWidth,       y3 = trackHeight,   // end of curv
    )
    // Line from start of the left ramp to the left offscreen
    path.lineTo(x = -beyondBounds, y = trackHeight)

    // Now we loop the path back since we need a closed path
    // We add a variation so its slightly different
    val variation = 0.1f

    // Line just slightly below the previous point
    path.lineTo(x = -beyondBounds, y = trackHeight + variation)
    // Line to the start of the left ramp
    path.lineTo(x = activeWidth - rampWidth, y = trackHeight + variation)
    // Curve from start of the left ramp to the top
    path.cubicTo(
        x1 = activeWidth - (rampWidth / 2), y1 = trackHeight + variation,
        x2 = activeWidth - (rampWidth / 2), y2 = rampHeight + variation,
        x3 = activeWidth,                   y3 = rampHeight + variation,
    )
    // Curve to the end of the right ramp
    path.cubicTo(
        x1 = activeWidth + (rampWidth / 2), y1 = rampHeight + variation,
        x2 = activeWidth + (rampWidth / 2), y2 = trackHeight + variation,
        x3 = activeWidth + rampWidth,       y3 = trackHeight + variation,
    )
    // Line to the right offscreen
    path.lineTo(x = beyondBounds, y = trackHeight + variation)

    // We need to exclude the parts from -beyondBounds to start, and from end to beyondBounds
    // The top and bottom are -beyondBounds and beyondBounds since we just need a high value
    val exclude = Path().apply {
        addRect(Rect(left = -beyondBounds, top = -beyondBounds, right = 0f, bottom = beyondBounds))
        addRect(Rect(left = size.width, top = -beyondBounds, right = beyondBounds, bottom = beyondBounds))
    }

    val trimmedPath = Path()
    trimmedPath.op(path, exclude, PathOperation.Difference)

    val pathMeasure = PathMeasure()
    pathMeasure.setPath(trimmedPath, false)
    val pathLength = pathMeasure.length / 2     // Only half the length, since our path loops

    val stepMarkers = steps + 1
    val height = 10f
    (0..stepMarkers).forEach { i ->
        val pos = pathMeasure.getPosition((i / stepMarkers.toFloat()) * pathLength)
        // Draw circles at start and end. Vertical lines at other steps
        if (i == 0 || i == stepMarkers)
            drawCircle(color = color, radius = height, center = pos)
        else
            drawLine(
                color = color,
                start = pos + Offset(0f, height),
                end = pos + Offset(0f, -height),
                strokeWidth = if (pos.x < activeWidth) 4f else 2f,
            )
    }

    val stroke = Stroke(width = 10f, cap = StrokeCap.Round, join = StrokeJoin.Round)
    // Draw Active Track
    clipRect(left = -beyondBounds, top = -beyondBounds, right = activeWidth, bottom = beyondBounds) {
        drawPath(path = trimmedPath, color = color, style = stroke)
    }
    clipRect(left = activeWidth, top = -beyondBounds, right = beyondBounds, bottom = beyondBounds) {
        drawPath(path = trimmedPath, color = color.copy(alpha = 0.2f), style = stroke)
    }
}


@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            RampSlider()
        }
    }
}
