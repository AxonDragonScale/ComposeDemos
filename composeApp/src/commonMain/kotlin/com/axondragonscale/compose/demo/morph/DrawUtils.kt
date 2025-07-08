package com.axondragonscale.compose.demo.morph

//import android.graphics.Matrix
//import androidx.compose.ui.geometry.Offset
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Path
//import androidx.compose.ui.graphics.drawscope.DrawScope
//import androidx.compose.ui.graphics.drawscope.Stroke
//import androidx.graphics.shapes.Cubic
//import androidx.graphics.shapes.Morph
//import androidx.graphics.shapes.RoundedPolygon
//import androidx.graphics.shapes.TransformResult
//import androidx.graphics.shapes.transformed
//
///**
// * Created by Ronak Harkhani on 24/06/24
// */
//
//internal enum class DrawType {
//    Shape,
//    Bezier
//}
//
//internal fun squareVertices() = floatArrayOf(1f, 1f, -1f, 1f, -1f, -1f, 1f, -1f)
//
//internal fun RoundedPolygon.rotate(degrees: Float): RoundedPolygon {
//    val matrix = Matrix()
//    matrix.setRotate(degrees, this.centerX, this.centerY)
//    return this.transformed(matrix)
//}
//
//internal fun RoundedPolygon.scale(scale: Float): RoundedPolygon {
//    val matrix = Matrix()
//    matrix.setScale(scale, scale, this.centerX, this.centerY)
//    return this.transformed(matrix)
//}
//
//internal fun RoundedPolygon.recenter(center: Offset): RoundedPolygon {
//    val matrix = Matrix()
//    matrix.setTranslate(center.x, center.y)
//    return this.transformed(matrix)
//}
//
//
//internal fun DrawScope.drawShape(morph: Morph, progress: Float, scale: Float = 1f, color: Color) {
//    val path = Path()
//    var isFirst = true
//    morph.forEachCubic(progress) { cubic ->
//        cubic.transform { x, y -> TransformResult(x * scale, y * scale) }
//        if (isFirst) {
//            path.moveTo(cubic.anchor0X, cubic.anchor0Y)
//            isFirst = false
//        }
//
//        path.cubicTo(
//            cubic.control0X, cubic.control0Y,
//            cubic.control1X, cubic.control1Y,
//            cubic.anchor1X, cubic.anchor1Y,
//        )
//    }
//    path.close()
//    drawPath(path, color)
//}
//
//internal fun DrawScope.drawBeziers(morph: Morph, progress: Float, scale: Float = 1f, color: Color) {
//    val path = Path()
//    var isFirst = true
//    morph.forEachCubic(progress) { cubic ->
//        cubic.transform { x, y -> TransformResult(x * scale, y * scale) }
//        if (isFirst) {
//            path.moveTo(cubic.anchor0X, cubic.anchor0Y)
//            isFirst = false
//        }
//
//        path.cubicTo(
//            cubic.control0X, cubic.control0Y,
//            cubic.control1X, cubic.control1Y,
//            cubic.anchor1X, cubic.anchor1Y,
//        )
//
//        // Draw red circles for start and end.
//        drawCircle(Color.Red, radius = 6f, center = cubic.anchor0(), style = Stroke(2f))
//        drawCircle(Color.Magenta, radius = 8f, center = cubic.anchor1(), style = Stroke(2f))
//
//        // Draw a circle for the first control point, and a line from start to it.
//        // The curve will start in this direction
//        drawLine(Color.Cyan, cubic.anchor0(), cubic.control0(), strokeWidth = 2f)
//        drawCircle(Color.Cyan, radius = 4f, center = cubic.control0(), style = Stroke(2f))
//
//        // Draw a circle for the second control point, and a line from it to the end.
//        // The curve will end in this direction
//        drawLine(Color.Cyan, cubic.control1(), cubic.anchor1(), strokeWidth = 2f)
//        drawCircle(Color.Cyan, radius = 4f, center = cubic.control1(), style = Stroke(2f))
//    }
//    path.close()
//
//    drawPath(path = path, color = color, style = Stroke(2f))
//}
//
//internal fun DrawScope.drawBeziers(shape: RoundedPolygon, scale: Float = 1f, color: Color) {
//    val path = Path()
//    var isFirst = true
//    shape.cubics.forEach {
//        val cubic = it.transformed { x, y -> TransformResult(x * scale, y * scale) }
//        if (isFirst) {
//            path.moveTo(cubic.anchor0X, cubic.anchor0Y)
//            isFirst = false
//        }
//
//        path.cubicTo(
//            cubic.control0X, cubic.control0Y,
//            cubic.control1X, cubic.control1Y,
//            cubic.anchor1X, cubic.anchor1Y,
//        )
//
//        // Draw red circles for start and end.
//        drawCircle(Color.Red, radius = 6f, center = cubic.anchor0(), style = Stroke(2f))
//        drawCircle(Color.Magenta, radius = 8f, center = cubic.anchor1(), style = Stroke(2f))
//
//        // Draw a circle for the first control point, and a line from start to it.
//        // The curve will start in this direction
//        drawLine(Color.Cyan, cubic.anchor0(), cubic.control0(), strokeWidth = 2f)
//        drawCircle(Color.Cyan, radius = 4f, center = cubic.control0(), style = Stroke(2f))
//
//        // Draw a circle for the second control point, and a line from it to the end.
//        // The curve will end in this direction
//        drawLine(Color.Cyan, cubic.control1(), cubic.anchor1(), strokeWidth = 2f)
//        drawCircle(Color.Cyan, radius = 4f, center = cubic.control1(), style = Stroke(2f))
//    }
//    path.close()
//
//    drawPath(path = path, color = color, style = Stroke(2f))
//}
//
//private fun Cubic.anchor0() = Offset(anchor0X, anchor0Y)
//private fun Cubic.control0() = Offset(control0X, control0Y)
//private fun Cubic.control1() = Offset(control1X, control1Y)
//private fun Cubic.anchor1() = Offset(anchor1X, anchor1Y)
