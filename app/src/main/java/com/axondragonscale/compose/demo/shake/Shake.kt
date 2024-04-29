package com.axondragonscale.compose.demo.shake

import android.content.res.Configuration
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 29/04/24
 */

@Composable
fun Shake(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 48.dp),
        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ExampleButton(
            effectText = "Horizontal Shake",
            options = ShakeState.Options(
                iterations = 5,
                translateX = 0.1f,
            )
        )

        ExampleButton(
            effectText = "Vertical Shake",
            options = ShakeState.Options(
                iterations = 10,
                translateY = 0.1f,
            )
        )

        ExampleButton(
            effectText = "Squeeze & Stretch",
            options = ShakeState.Options(
                iterations = 2,
                intensity = 10_000f,
                scaleX = 0.3f,
                scaleY = -0.2f
            )
        )

        ExampleButton(
            effectText = "Rotate Up & Down",
            options = ShakeState.Options(
                iterations = 4,
                intensity = 10_000f,
                rotateZ = 15f
            )
        )

        ExampleButton(
            effectText = "Nod",
            options = ShakeState.Options(
                iterations = 5,
                intensity = 1_000f,
                rotateX = -20f,
                translateY = 0.1f
            )
        )

        ExampleButton(
            effectText = "Nope",
            options = ShakeState.Options(
                iterations = 4,
                intensity = 2_000f,
                rotateY = 30f,
                translateX = 0.1f
            )
        )
    }
}

@Composable
fun ExampleButton(
    modifier: Modifier = Modifier,
    effectText: String,
    options: ShakeState.Options,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = effectText)

        val shakeState = remember { ShakeState() }
        Box(
            modifier = Modifier
                .shake(shakeState)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    shakeState.shake(options.copy(trigger = System.currentTimeMillis()))
                }
                .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Click Me",
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

class ShakeState {

    var options: Options? by mutableStateOf(null)
        private set

    fun shake(options: Options) {
        this.options = options
    }

    data class Options(
        val iterations: Int,
        val intensity: Float = 100_000f,
        val rotateX: Float = 0f,
        val rotateY: Float = 0f,
        val rotateZ: Float = 0f,
        val scaleX: Float = 0f,
        val scaleY: Float = 0f,
        val translateX: Float = 0f,
        val translateY: Float = 0f,

        // To ensure every options object is unique
        val trigger: Long = System.currentTimeMillis(),
    )
}

fun Modifier.shake(state: ShakeState): Modifier {
    val options = state.options ?: return this
    return composed {
        val progress = remember { Animatable(0f) }

        LaunchedEffect(options) {
            (0 until options.iterations).forEach { i ->
                if (i % 2 == 0) progress.animateTo(1f, spring(stiffness = options.intensity))
                else progress.animateTo(-1f, spring(stiffness = options.intensity))
            }
            progress.animateTo(0f)
        }

        this.graphicsLayer {
            rotationX = options.rotateX * progress.value
            rotationY = options.rotateY * progress.value
            rotationZ = options.rotateZ * progress.value

            scaleX = 1f + options.scaleX * progress.value
            scaleY = 1f + options.scaleY * progress.value

            translationX = size.width * options.translateX * progress.value
            translationY = size.height * options.translateY * progress.value
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShakePreview() {
    ComposeDemosTheme {
        Surface {
            Shake()
        }
    }
}
