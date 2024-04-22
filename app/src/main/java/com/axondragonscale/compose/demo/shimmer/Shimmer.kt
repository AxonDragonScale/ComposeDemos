package com.axondragonscale.compose.demo.shimmer

import android.content.res.Configuration
import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 22/04/24
 */

@Composable
fun Shimmer(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .shimmer(
                                primaryColor = MaterialTheme.colorScheme.primary,
                                backgroundColor = MaterialTheme.colorScheme.background,
                            )
                    )

                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(24.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .shimmer(
                                    primaryColor = MaterialTheme.colorScheme.primary,
                                    backgroundColor = Color.Cyan.copy(alpha = 0.5f),
                                    durationMillis = 2000,
                                )
                        )

                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp)
                                .fillMaxWidth(0.5f)
                                .height(16.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .shimmer(
                                    primaryColor = MaterialTheme.colorScheme.primary,
                                    backgroundColor = Color.Green.copy(alpha = 0.5f),
                                )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmer(
                                primaryColor = MaterialTheme.colorScheme.primary,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                slant = Slant.Left,
                            )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmer(
                                primaryColor = MaterialTheme.colorScheme.primary,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                slant = Slant.None,
                            )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .shimmer(
                                primaryColor = MaterialTheme.colorScheme.primary,
                                backgroundColor = MaterialTheme.colorScheme.background,
                                slant = Slant.Right,
                            )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                repeat(2) {
                    Box(
                        modifier = Modifier
                            .padding(vertical = 4.dp)
                            .fillMaxWidth()
                            .height(14.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .shimmer(
                                gradientColors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primary,
                                ),
                            )
                    )
                }
            }
        }
    }
}

enum class Slant {
    Left,
    None,
    Right
}

fun Modifier.shimmer(
    primaryColor: Color,
    backgroundColor: Color,
    slant: Slant = Slant.Right,
    isEnabled: Boolean = true,
    durationMillis: Int = 1000,
    animation: DurationBasedAnimationSpec<Float> = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
    ),
): Modifier =
    this.shimmer(
        gradientColors = listOf(primaryColor, backgroundColor, primaryColor),
        slant = slant,
        isEnabled = isEnabled,
        durationMillis = durationMillis,
        animation = animation
    )

fun Modifier.shimmer(
    gradientColors: List<Color>,
    slant: Slant = Slant.Right,
    isEnabled: Boolean = true,
    durationMillis: Int = 1000,
    animation: DurationBasedAnimationSpec<Float> = tween(
        durationMillis = durationMillis,
        easing = LinearEasing
    ),
): Modifier {
    if (!isEnabled) return this
    return composed {
        var size by remember { mutableStateOf(IntSize.Zero) }

        val transition = rememberInfiniteTransition("")
        val xOffset by transition.animateFloat(
            label = "Shimmer",
            initialValue = -size.width.toFloat(),
            targetValue = size.width.toFloat(),
            animationSpec = infiniteRepeatable(
                animation = animation,
                repeatMode = RepeatMode.Restart,
            ),
        )

        this
            .onGloballyPositioned { size = it.size }
            .background(
                brush = Brush.linearGradient(
                    colors = gradientColors,
                    start = Offset(xOffset, 0f),
                    end = Offset(
                        x = xOffset + size.width.toFloat(),
                        y = when (slant) {
                            Slant.None -> 0f
                            Slant.Left -> -size.height.toFloat()
                            Slant.Right -> size.height.toFloat()
                        }
                    )
                ),
            )
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShimmerPreview() {
    ComposeDemosTheme {
        Surface {
            Shimmer()
        }
    }
}
