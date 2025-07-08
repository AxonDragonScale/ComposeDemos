package com.axondragonscale.compose.demo.progressbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by Ronak Harkhani on 29/06/24
 */

@Composable
fun ProgressBar(modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxSize()) {
        var progress by remember { mutableFloatStateOf(0.5f) }

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterVertically)
        ) {
            RoundedProgressBar(
                progress = progress,
                trackHeight = 28.dp,
                activeTrackHeight = 20.dp,
                trackShape = RoundedCornerShape(14.dp),
                activeTrackShape = RoundedCornerShape(14.dp),
                trackColor = Color(red = 244, green = 193, blue = 66),
                activeTrackColor = Color(red = 238, green = 138, blue = 81)
            )

            RoundedProgressBar(
                progress = progress,
                trackHeight = 20.dp,
                activeTrackHeight = 16.dp,
                trackShape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
                activeTrackShape = CutCornerShape(topStart = 8.dp, bottomEnd = 8.dp),
                trackColor = Color.Blue.copy(alpha = 0.3f),
                activeTrackColor = Color.Blue,
            )

            RoundedGradientProgressBar(
                progress = progress
            )

            RoundedGradientProgressBar(
                progress = progress,
                trackHeight = 20.dp,
                activeTrackHeight = 12.dp,
                trackShape = RoundedCornerShape(10.dp),
                activeTrackShape = RoundedCornerShape(6.dp),
                brush = Brush.linearGradient(listOf(Color.Red, Color.Blue))
            )

            DashLineProgressBar(
                progress = progress,
            )

            DashLineProgressBar(
                progress = progress,
                trackShape = CutCornerShape(bottomStart = 8.dp, topEnd = 8.dp),
                activeTrackShape = CutCornerShape(bottomStart = 7.dp, topEnd = 7.dp),
                dashWidth = 6.dp,
                dashSpacing = 2.dp,
                activeTrackHorizontalPadding = 3.dp,
                activeTrackVerticalPadding = 3.dp,
                borderColor = Color.Green,
                dashColor = Color.Green,
            )
        }

        Controls(
            modifier = Modifier.padding(8.dp),
            progress = progress,
            onProgressChange = { progress = it }
        )
    }
}


@Composable
private fun RoundedProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    trackHeight: Dp = 16.dp,
    activeTrackHeight: Dp = trackHeight,
    trackColor: Color = MaterialTheme.colorScheme.surfaceVariant,
    activeTrackColor: Color = MaterialTheme.colorScheme.primary,
    trackShape: Shape = RoundedCornerShape(8.dp),
    activeTrackShape: Shape = trackShape,
    activeTrackHorizontalPadding: Dp = (trackHeight - activeTrackHeight) / 2,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(trackHeight)
            .background(
                color = trackColor,
                shape = trackShape
            ),
        contentAlignment = Alignment.CenterStart
    ) {
        Box(
            modifier = Modifier
                .padding(activeTrackHorizontalPadding)
                .height(activeTrackHeight)
                .fillMaxWidth(progress)
                .background(
                    color = activeTrackColor,
                    shape = activeTrackShape
                )
        )
    }
}

@Composable
private fun RoundedGradientProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    trackHeight: Dp = 16.dp,
    activeTrackHeight: Dp = trackHeight,
    trackShape: RoundedCornerShape = RoundedCornerShape(8.dp),
    activeTrackShape: RoundedCornerShape = trackShape,
    brush: Brush = Brush.linearGradient(listOf(Color.Blue, Color.Magenta)),
    activeTrackHorizontalPadding: Dp = (trackHeight - activeTrackHeight) / 2,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 4.dp, bottom = 2.dp),
            text = (progress * 100).toInt().toString() + "% Completed",
            style = MaterialTheme.typography.labelMedium.copy(brush = brush),
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = trackShape
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .padding(activeTrackHorizontalPadding)
                    .height(activeTrackHeight)
                    .fillMaxWidth(progress)
                    .background(
                        brush = brush,
                        shape = activeTrackShape
                    )
            )
        }
    }
}

@Composable
fun DashLineProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    dashWidth: Dp = 8.dp,
    dashSpacing: Dp = 2.dp,
    dashColor: Color = LocalContentColor.current,
    trackHeight: Dp = 16.dp,
    activeTrackHeight: Dp = trackHeight,
    activeTrackVerticalPadding: Dp = dashSpacing,
    activeTrackHorizontalPadding: Dp = dashSpacing,
    borderColor: Color = dashColor,
    borderWidth: Dp = 1.dp,
    trackShape: Shape = RectangleShape,
    activeTrackShape: Shape = trackShape,
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.padding(start = 2.dp, bottom = 2.dp),
            text = (progress * 100).toInt().toString() + "%",
            style = MaterialTheme.typography.labelMedium,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(trackHeight)
                .border(borderWidth, borderColor, trackShape),
            contentAlignment = Alignment.CenterStart
        ) {

            Box(
                modifier = Modifier
                    .padding(
                        horizontal = activeTrackHorizontalPadding,
                        vertical = activeTrackVerticalPadding
                    )
                    .height(activeTrackHeight)
                    .fillMaxWidth(progress)
                    .clip(activeTrackShape),
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(dashSpacing)) {
                    repeat(100) {
                        Box(
                            modifier = Modifier
                                .size(width = dashWidth, height = activeTrackHeight)
                                .background(dashColor)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Controls(
    modifier: Modifier = Modifier,
    progress: Float,
    onProgressChange: (Float) -> Unit,
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = "Progress",
            style = MaterialTheme.typography.labelLarge,
        )
        Slider(
            modifier = Modifier.height(32.dp),
            value = progress,
            onValueChange = { onProgressChange(it) },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface {
            ProgressBar()
        }
    }
}
