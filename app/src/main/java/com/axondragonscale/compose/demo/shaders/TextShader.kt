package com.axondragonscale.compose.demo.shaders

import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 11/05/24
 */

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val TextShaderPage = ShaderPage(
    title = "Text Shader",
    desc = "Calculates color of text pixels based on its position and doesn't changes other pixels.",
    content = { TextShader() }
)

private val SHADER = """
    uniform float2 size;
    uniform shader composable;
    
    half4 main(float2 pos) {
        // Get pixel at pos in composable (text) and use its alpha for shader's alpha
        // So Shader is applied to text pixels, and alpha is 0 elsewhere
        float alpha = composable.eval(pos).a;
        if(alpha > 0)
            return half4(pos.x / size.x, pos.y / size.y, 0.75, alpha);
        else
            return composable.eval(pos);
    }
    
""".trimIndent()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TextShader(modifier: Modifier = Modifier) {
    val shader = remember { RuntimeShader(SHADER) }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            modifier = Modifier
                .onSizeChanged {
                    shader.setFloatUniform(
                        "size",
                        it.width.toFloat(),
                        it.height.toFloat()
                    )
                }
                .graphicsLayer {
                    clip = true
                    renderEffect = RenderEffect
                        .createRuntimeShaderEffect(shader, "composable")
                        .asComposeRenderEffect()
                },
            text = "AGSL",
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShakePreview() {
    ComposeDemosTheme {
        Surface {
            TextShader()
        }
    }
}
