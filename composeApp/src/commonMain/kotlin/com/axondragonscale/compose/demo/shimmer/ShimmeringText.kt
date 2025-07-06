package com.axondragonscale.compose.demo.shimmer

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.TextStyle
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Created by Ronak Harkhani on 19/09/24
 */

@Composable
fun ShimmeringText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ShimmeringText(
            text = "Hello World",
            textStyle = MaterialTheme.typography.displayMedium,
        )

        ShimmeringText(
            text = "Hello World",
            shimmerColor = Color.Red,
            textStyle = MaterialTheme.typography.headlineSmall,
            animationSpec = tween(2000, 500, FastOutSlowInEasing),
        )

        ShimmeringText(
            text = "Hello World",
            shimmerColor = Color.Green,
            textStyle = MaterialTheme.typography.headlineLarge,
        )
    }
}

@Composable
private fun ShimmeringText(
    modifier: Modifier = Modifier,
    text: String,
    shimmerColor: Color = LocalContentColor.current,
    textStyle: TextStyle = LocalTextStyle.current,
    animationSpec: DurationBasedAnimationSpec<Float> = tween(1000, 200, LinearEasing)
) {
    val infiniteTransition = rememberInfiniteTransition("ShimmeringText")
    val shimmerProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animationSpec),
        label = "ShimmerProgress"
    )

    val brush = remember(shimmerProgress) {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val sweepDistance = size.width * 2 // We want to sweep from -50% to 150%
                val currentPosition = shimmerProgress * sweepDistance - size.width

                return LinearGradientShader(
                    colors = listOf(Color.Transparent, shimmerColor, Color.Transparent),
                    from = Offset(currentPosition, 0f),
                    to = Offset(currentPosition + size.width, size.height),
                )
            }
        }
    }

    Text(
        modifier = modifier,
        text = text,
        style = textStyle.copy(brush = brush),
    )
}

@Preview
@Composable
private fun Preview() {
    MaterialTheme {
        Surface {
            ShimmeringText()
        }
    }
}
