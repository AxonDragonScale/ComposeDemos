package com.axondragonscale.compose.demo.flip

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 26/04/24
 */

private val Red = Color(0xFFC40C0C)
private val Yellow = Color(0xFFFFC100)
private val Blue = Color(0xFF121481)
private val Green = Color(0xFF1A4D2E)

@Composable
fun CardFlip(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        var isFlipped1 by remember { mutableStateOf(false) }
        FlippableCard(
            modifier = Modifier
                .size(300.dp, 150.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isFlipped1 = !isFlipped1
                },
            isFlipped = isFlipped1,
            flipDuration = 1000,
            frontContent = {
                CardContent(modifier = it.background(Red), text = "FRONT")
            },
            backContent = {
                CardContent(modifier = it.background(Yellow), text = "BACK")
            }
        )

        var isFlipped2 by remember { mutableStateOf(false) }
        FlippableCard(
            modifier = Modifier
                .size(300.dp, 150.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) {
                    isFlipped2 = !isFlipped2
                },
            isFlipped = isFlipped2,
            flipType = FlipType.Vertical,
            frontContent = {
                CardContent(modifier = it.background(Blue), text = "FRONT")
            },
            backContent = {
                CardContent(modifier = it.background(Green), text = "BACK")
            }
        )
    }
}

@Composable
private fun CardContent(modifier: Modifier = Modifier, text: String) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.White
        )
    }
}

@Composable
fun FlippableCard(
    modifier: Modifier = Modifier,
    shape: Shape = CardDefaults.shape,
    colors: CardColors = CardDefaults.cardColors(),
    elevation: CardElevation = CardDefaults.cardElevation(),
    border: BorderStroke? = null,
    isFlipped: Boolean,
    flipType: FlipType = FlipType.Horizontal,
    flipDuration: Int = 500,
    frontContent: @Composable (Modifier) -> Unit,
    backContent: @Composable (Modifier) -> Unit,
) {
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(flipDuration),
        label = "Card Rotation"
    )

    Card(
        modifier = modifier
            .graphicsLayer {
                when (flipType) {
                    FlipType.Horizontal -> rotationY = rotation
                    FlipType.Vertical -> rotationX = rotation
                }
                // Lower the camera distance, the closer it will seem in the z axis during rotation
                cameraDistance = 8 * density
            },
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
    ) {
        if (rotation < 90f)
            frontContent(Modifier)
        else
            backContent(
                Modifier.graphicsLayer {
                    // Rotate the content so it doesn't look flipped
                    when (flipType) {
                        FlipType.Horizontal -> scaleX = -1f
                        FlipType.Vertical -> scaleY = -1f
                    }
                }
            )
    }
}

enum class FlipType {
    Horizontal,
    Vertical
}


@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            CardFlip()
        }
    }
}
