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
import androidx.compose.runtime.remember
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

/**
 * Created by Ronak Harkhani on 11/05/24
 */

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
val ImageShaderPage = ShaderPage(
    title = "Image Shader",
    desc = "Apply shader to an image.",
    content = { ImageShader() }
)

private val SHADER = """
    uniform float2 size;
    uniform shader composable;
    
    half4 main(float2 pos) {
        // Show the image normally. Just the return the pixel from composable    
        // return composable.eval(pos);
        // return composable.eval(pos).rgba;
        
        // Switch the colors
        // return composable.eval(pos).rbga;
        // return composable.eval(pos).grba;
        // return composable.eval(pos).brga;
        // return composable.eval(pos).bgra;
        
        float scale = 0.009;
        float2 scaledPos = pos * scale;
        
        float2 center = size * 0.5 * scale;
        float distanceFromCenter = distance(scaledPos, center);
        float2 dirFromCenter = scaledPos - center;
        float2 offset = dirFromCenter * sin(distanceFromCenter * 70);
        return composable.eval((scaledPos + offset/20) / scale);
    }
    
""".trimIndent()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun ImageShader(modifier: Modifier = Modifier) {
    val shader = remember { RuntimeShader(SHADER) }
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Image(
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
            bitmap = ImageBitmap.imageResource(id = R.drawable.pic1),
            contentScale = ContentScale.Crop,
            contentDescription = null,
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
            ImageShader()
        }
    }
}
