package com.axondragonscale.compose.demo.loader.morph

//import android.content.res.Configuration
//import androidx.compose.animation.core.LinearEasing
//import androidx.compose.animation.core.RepeatMode
//import androidx.compose.animation.core.animateFloat
//import androidx.compose.animation.core.infiniteRepeatable
//import androidx.compose.animation.core.rememberInfiniteTransition
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Surface
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.drawWithCache
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Matrix
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.PathMeasure
//import androidx.compose.ui.graphics.StrokeCap
//import androidx.compose.ui.graphics.asComposePath
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.compose.ui.graphics.drawscope.rotate
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.graphics.shapes.CornerRounding
//import androidx.graphics.shapes.Morph
//import androidx.graphics.shapes.RoundedPolygon
//import androidx.graphics.shapes.circle
//import androidx.graphics.shapes.star
//import androidx.graphics.shapes.toPath
//import com.axondragonscale.compose.demo.morph.rotate
//import com.axondragonscale.compose.demo.morph.scale
//import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme
//
///**
// * Created by Ronak Harkhani on 02/10/24
// */
//
//private val star1 = RoundedPolygon.star(
//    numVerticesPerRadius = 8,
//    innerRadius = 0.5f,
//    rounding = CornerRounding(0.3f),
//).normalized()
//
//private val star2 = RoundedPolygon.star(
//    numVerticesPerRadius = 8,
//    innerRadius = 0.75f,
//    rounding = CornerRounding(0.3f),
//).normalized()
//
//private val star3 = RoundedPolygon.star(
//    numVerticesPerRadius = 4,
//    innerRadius = 0.1f,
//).normalized()
//
//private val star4 = RoundedPolygon.star(
//    numVerticesPerRadius = 4,
//    innerRadius = 0.1f,
//).normalized()
//    .scale(0.5f)
//
//private val star5 = RoundedPolygon
//    .star(
//        numVerticesPerRadius = 8,
//        innerRadius = 0.8f,
//        rounding = CornerRounding(0.15f),
//        innerRounding = CornerRounding(0.15f),
//    )
//    .normalized()
//
//private val pentagon = RoundedPolygon(
//    numVertices = 5,
//    rounding = CornerRounding(0.2f),
//)
//    .rotate(-18f)
//    .normalized()
//    .scale(0.5f)
//
//private val circle1 = RoundedPolygon.circle(
//    numVertices = 8,
//).normalized()
//
//private val circle2 = RoundedPolygon.circle(
//    numVertices = 8,
//    radius = 0.5f
//).normalized()
//    .scale(0.5f)
//
//private val colors = listOf(
//    Color(0xFF3FCEBC),
//    Color(0xFF3CBCEB),
//    Color(0xFF5F96E7),
//    Color(0xFF816FE3),
//    Color(0xFF9F5EE2),
//    Color(0xFFBD4CE0),
//    Color(0xFFDE589F),
//    Color(0xFF3FCEBC),
//)
//
//private val brush = Brush.sweepGradient(colors)
//
//@Composable
//fun MorphLoaders(modifier: Modifier = Modifier) {
//    Column(
//        modifier = modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
//    ) {
//        MorphLoader(
//            modifier = Modifier.size(64.dp),
//            morph = Morph(star1, circle1),
//            brush = brush,
//            durationMillis = 4000,
//        )
//
//        MorphLoader(
//            modifier = Modifier.size(64.dp),
//            morph = Morph(star2, circle1),
//            brush = brush,
//            durationMillis = 4000,
//            progressiveDraw = false,
//        )
//
//        MorphLoader(
//            modifier = Modifier.size(64.dp),
//            morph = Morph(star3, circle1),
//            brush = brush,
//            durationMillis = 1000,
//            shouldRotate = false,
//            progressiveDraw = false,
//        )
//
//        MorphLoader(
//            modifier = Modifier.size(64.dp),
//            morph = Morph(star4, star3),
//            brush = brush,
//            durationMillis = 1000,
//            shouldRotate = false,
//            progressiveDraw = false,
//        )
//
//        MorphLoader(
//            modifier = Modifier.size(64.dp),
//            morph = Morph(pentagon, star5),
//            brush = brush,
//            durationMillis = 1000,
//            shouldRotate = false,
//            progressiveDraw = false,
//        )
//
//        MorphLoader(
//            modifier = Modifier.size(64.dp),
//            morph = Morph(circle2, circle1),
//            brush = brush,
//            durationMillis = 1000,
//            shouldRotate = true,
//            progressiveDraw = false,
//        )
//    }
//}
//
//@Composable
//private fun MorphLoader(
//    modifier: Modifier = Modifier,
//    morph: Morph,
//    brush: Brush,
//    durationMillis: Int,
//    shouldRotate: Boolean = true,
//    progressiveDraw: Boolean = true,
//) {
//    val infiniteTransition = rememberInfiniteTransition()
//    val progress by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 1f,
//        animationSpec = infiniteRepeatable(
//            tween(durationMillis, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse,
//        ),
//    )
//    val rotation by infiniteTransition.animateFloat(
//        initialValue = 0f,
//        targetValue = 360f,
//        animationSpec = infiniteRepeatable(
//            tween(durationMillis, easing = LinearEasing),
//            repeatMode = RepeatMode.Reverse,
//        )
//    )
//
//    val matrix = remember { Matrix() }
//
//    Box(
//        modifier = modifier
//            .fillMaxSize()
//            .drawWithCache {
//                val path = morph.toPath(progress).asComposePath()
//
//                val finalPath = if (progressiveDraw) {
//                    val pathMeasure = PathMeasure().apply { setPath(path, false) }
//                    val finalPath = Path()
//                    pathMeasure.getSegment(0f, pathMeasure.length * progress, finalPath)
//                    finalPath
//                } else {
//                    path
//                }
//
//                matrix.reset()
//                matrix.scale(size.minDimension, size.minDimension)
//                finalPath.transform(matrix)
//
//                onDrawBehind {
//                    rotate(if (shouldRotate) rotation else 0f) {
//                        drawPath(
//                            path = finalPath,
//                            brush = brush,
//                            style = Stroke(4.dp.toPx(), cap = StrokeCap.Round),
//                        )
//                    }
//                }
//            }
//    )
//}
//
//@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
//@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
//@Composable
//private fun Preview() {
//    ComposeDemosTheme {
//        Surface {
//            MorphLoaders()
//        }
//    }
//}
