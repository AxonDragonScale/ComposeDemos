package com.axondragonscale.compose.demo.fluildfab

import android.content.res.Configuration
import android.graphics.ColorMatrixColorFilter
import android.graphics.Shader
import android.os.Build
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RenderEffect
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import android.graphics.RenderEffect as NativeRenderEffect

/**
 * Created by Ronak Harkhani on 26/04/24
 */

@Composable
fun FluidFab(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentAlignment = Alignment.BottomCenter,
        ) {
            var isExpanded by remember { mutableStateOf(false) }
            FluidFabButton(
                isExpanded = isExpanded,
                onClick = { isExpanded = !isExpanded },
                onLeftButtonClick = { isExpanded = !isExpanded },
                onMiddleButtonClick = { isExpanded = !isExpanded },
                onRightButtonClick = { isExpanded = !isExpanded },
            )
        }
    }
}

@Composable
fun FluidFabButton(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    duration: Int = 1000,
    onClick: () -> Unit,
    onLeftButtonClick: () -> Unit,
    onMiddleButtonClick: () -> Unit,
    onRightButtonClick: () -> Unit,
) {
    val fluidChainRenderEffect = remember { fluidChainRenderEffect() }
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

        // Two ExpandableFabButton are needed here
        // The first one provides the background, blur and fluid effect
        // But the blur restricts it from rendering the icons properly
        // Also drawing icons affects the fluid chain color
        // So we draw a second ExpandableFabButton on top of it without render effect
        // that renders the icons

        ExpandableFabButton(
            isExpanded = isExpanded,
            fluidChainRenderEffect = fluidChainRenderEffect,
            renderIcons = false,
            duration = duration,
        )

        ExpandableFabButton(
            isExpanded = isExpanded,
            fluidChainRenderEffect = null,
            duration = duration,
            onClick = onClick,
            onLeftButtonClick = onLeftButtonClick,
            onMiddleButtonClick = onMiddleButtonClick,
            onRightButtonClick = onRightButtonClick,
        )

        val outlineProgress by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            animationSpec = tween(
                durationMillis = (duration * 0.4).toInt(),
                delayMillis = if (isExpanded) (duration * 0.4).toInt() else 0,
                easing = LinearEasing
            ),
        )
        ExpandedFabOutline(
            scale = outlineProgress * 0.8f,
            alpha = outlineProgress * 0.7f,
        )
    }
}

@Composable
private fun ExpandableFabButton(
    modifier: Modifier = Modifier,
    isExpanded: Boolean,
    fluidChainRenderEffect: RenderEffect?,
    renderIcons: Boolean = true,
    duration: Int,
    onClick: () -> Unit = {},
    onLeftButtonClick: () -> Unit = {},
    onMiddleButtonClick: () -> Unit = {},
    onRightButtonClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .graphicsLayer {
                renderEffect = fluidChainRenderEffect
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        val expandedButtonBottomPadding = 40.dp
        val expandedButtonHorizontalPadding = 160.dp

        val leftFabProgress by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            animationSpec = tween(
                durationMillis = (duration * 0.8).toInt(),
                delayMillis = if (isExpanded) 0 else (duration * 0.2).toInt(),
                easing = FastOutSlowInEasing
            )
        )
        AnimatedFab(
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = expandedButtonBottomPadding * leftFabProgress,
                    end = expandedButtonHorizontalPadding * leftFabProgress,
                )
            ),
            icon = if (renderIcons) Icons.Default.Home else null,
            iconAlpha = leftFabProgress,
            onClick = onLeftButtonClick,
        )

        val middleFabProgress by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            animationSpec = tween(
                durationMillis = (duration * 0.8).toInt(),
                delayMillis = (duration * 0.1).toInt(),
                easing = FastOutSlowInEasing
            )
        )
        AnimatedFab(
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = expandedButtonBottomPadding * 2 * middleFabProgress
                )
            ),
            icon = if (renderIcons) Icons.Default.Settings else null,
            iconAlpha = middleFabProgress,
            onClick = onMiddleButtonClick,
        )

        val rightFabProgress by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            animationSpec = tween(
                durationMillis = (duration * 0.8).toInt(),
                delayMillis = if (isExpanded) (duration * 0.2).toInt() else 0,
                easing = FastOutSlowInEasing
            )
        )
        AnimatedFab(
            modifier = Modifier.padding(
                PaddingValues(
                    bottom = expandedButtonBottomPadding * rightFabProgress,
                    start = expandedButtonHorizontalPadding * rightFabProgress,
                )
            ),
            icon = if (renderIcons) Icons.Default.CameraAlt else null,
            iconAlpha = rightFabProgress,
            onClick = onRightButtonClick,
        )

        val backgroundScale by animateFloatAsState(
            targetValue = if (isExpanded) 0f else 1f,
            animationSpec = tween(
                durationMillis = if (isExpanded) (duration * 0.6).toInt() else (duration * 0.3).toInt(),
                delayMillis = if (isExpanded) (duration * 0.4).toInt() else (duration * 0.1).toInt(),
                easing = FastOutSlowInEasing
            )
        )
        AnimatedFab(
            modifier = Modifier.scale(backgroundScale),
            icon = null,
        )

        val iconRotation by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            animationSpec = tween(
                durationMillis = (duration * 0.6).toInt(),
                delayMillis = (duration * 0.2).toInt(),
                easing = FastOutSlowInEasing
            )
        )
        AnimatedFab(
            modifier = Modifier.rotate(45 * 5 * iconRotation),
            icon = if (renderIcons) Icons.Default.Add else null,
            backgroundColor = Color.Transparent,
            onClick = onClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnimatedFab(
    modifier: Modifier = Modifier,
    backgroundColor: Color = FloatingActionButtonDefaults.containerColor,
    icon: ImageVector?,
    iconAlpha: Float = 1f,
    onClick: () -> Unit = {},
) {
    CompositionLocalProvider(LocalRippleConfiguration provides null) {
        FloatingActionButton(
            modifier = modifier,
            shape = CircleShape,
            containerColor = backgroundColor,
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
            onClick = onClick,
        ) {
            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    tint = LocalContentColor.current.copy(alpha = iconAlpha)
                )
            }
        }
    }
}

@Composable
fun ExpandedFabOutline(
    modifier: Modifier = Modifier,
    scale: Float,
    alpha: Float,
) {
    Box(
        modifier = Modifier.size(56.dp),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = modifier
                .size(56.dp) // Fab Default Size
                .scale(scale)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = alpha),
                    shape = CircleShape
                )
        )
    }
}

private fun fluidChainRenderEffect(): RenderEffect? {
    // RenderEffect not supported below android 12
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) return null

    // The radius can be provided as an argument if button size is changed
    val blur = NativeRenderEffect.createBlurEffect(
        80f, 80f, Shader.TileMode.DECAL
    )
    val alpha = NativeRenderEffect.createColorFilterEffect(
        ColorMatrixColorFilter(
            floatArrayOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 50f, -5000f
            )
        )
    )

    return NativeRenderEffect.createChainEffect(alpha, blur).asComposeRenderEffect()
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            FluidFab()
        }
    }
}
