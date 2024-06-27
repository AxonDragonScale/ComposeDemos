package com.axondragonscale.compose.demo.shaders

import android.content.res.Configuration
import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import com.axondragonscale.compose.demo.R
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Created by Ronak Harkhani on 11/05/24
 */

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val TimeShaderPage = ShaderPage(
    title = "Time Shader",
    desc = "A Fractal shader that creates an effect with time",
    content = { TimeShader() }
)

private val SHADER = """
    uniform float2 size;
    uniform float time;
    uniform shader composable;
    
    float f(float3 p) {
        p.z -= time * 5.;
        float a = p.z * .1;
        p.xy *= mat2(cos(a), sin(a), -sin(a), cos(a));
        return .1 - length(cos(p.xy) + sin(p.yz));
    }
    
    half4 main(float2 pos) { 
        float3 d = .5 - pos.xy1 / size.y;
        float3 p = float3(0);
        for (int i = 0; i < 32; i++) {
          p += f(p) * d;
        }
        return ((sin(p) + float3(2, 5, 12)) / length(p)).xyz1;
    }

""".trimIndent()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun TimeShader(modifier: Modifier = Modifier) {
    var time by remember { mutableFloatStateOf(0f) }
    val shader = remember { RuntimeShader(SHADER) }

    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        scope.launch {
            while (true) {
                time = (System.currentTimeMillis() % 100_000L) / 1_000f
                delay(10)
            }
        }
    }

    Surface(
        modifier = modifier
            .fillMaxSize()
            .onSizeChanged {
                shader.setFloatUniform(
                    "size",
                    it.width.toFloat(),
                    it.height.toFloat()
                )
            }
            .graphicsLayer {
                clip = true

                shader.setFloatUniform("time", time)
                renderEffect = RenderEffect
                    .createRuntimeShaderEffect(shader, "composable")
                    .asComposeRenderEffect()
            },
    ) {}
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun Preview() {
    ComposeDemosTheme {
        Surface {
            TimeShader()
        }
    }
}
