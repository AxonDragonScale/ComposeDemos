package com.axondragonscale.compose.demo.morph

import android.graphics.Matrix
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.transformed

/**
 * Created by Ronak Harkhani on 24/06/24
 */

internal enum class DrawType {
    Shape,
    Bezier
}

internal fun squareVertices() = floatArrayOf(1f, 1f, -1f, 1f, -1f, -1f, 1f, -1f)

internal fun RoundedPolygon.rotate(degrees: Float): RoundedPolygon {
    val matrix = Matrix()
    matrix.setRotate(degrees, this.centerX, this.centerY)
    return this.transformed(matrix)
}

internal fun RoundedPolygon.scale(scale: Float): RoundedPolygon {
    val matrix = Matrix()
    matrix.setScale(scale, scale, this.centerX, this.centerY)
    return this.transformed(matrix)
}

internal fun RoundedPolygon.recenter(center: Offset): RoundedPolygon {
    val matrix = Matrix()
    matrix.setTranslate(center.x, center.y)
    return this.transformed(matrix)
}

internal fun Morph.toComposePath(progress: Float, scale: Float = 1f): Path {
    val path = Path()
    var isFirst = true
    this.forEachCubic(progress) { cubic ->
        if (isFirst) {
            path.moveTo(cubic.anchor0X * scale, cubic.anchor0Y * scale)
            isFirst = false
        }

        path.cubicTo(
            cubic.control0X * scale, cubic.control0Y * scale,
            cubic.control1X * scale, cubic.control1Y * scale,
            cubic.anchor1X * scale, cubic.anchor1Y * scale,
        )
    }
    path.close()
    return path
}
