package com.axondragonscale.compose.demo.shaders

import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.tooling.preview.Preview
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 10/05/24
 */

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val PositionalShaderPage = ShaderPage(
    title = "Positional Shader",
    desc = "Calculates color of each pixel based on its position",
    content = { PositionalShader() }
)

private val SHADER = """
    uniform float2 size;
    
    half4 main(float2 pos) {
        // Define how to calculate r, g, b, a values for each pixel and return as half4
        // Alt notation: half4((pos / size).xy, 0.75, 1)
        return half4(pos.x / size.x, pos.y / size.y, 0.75, 1);
    }
    
""".trimIndent()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun PositionalShader() {
    val shader = remember { RuntimeShader(SHADER) }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .onSizeChanged {
                shader.setFloatUniform(
                    "size",
                    it.width.toFloat(),
                    it.height.toFloat()
                )
            }
            .graphicsLayer {
                renderEffect = RenderEffect
                    .createShaderEffect(shader)
                    .asComposeRenderEffect()
            }
    ) {}
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShakePreview() {
    ComposeDemosTheme {
        Surface {
            PositionalShader()
        }
    }
}
