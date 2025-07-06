package com.axondragonscale.compose.demo.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import kotlin.math.pow
import kotlin.math.round

/**
 * Created by Ronak Harkhani on 24/10/24
 */

fun Float.round(decimals: Int): Float {
    val factor = 10.0F.pow(decimals)
    return round(this * factor) / factor
}

fun Float.toString(decimals: Int): String {
    val factor = 10.0F.pow(decimals)
    val rounded = this.round(decimals)
    val intPart = rounded.toLong()
    val decimalPart = ((rounded - intPart) * factor).toLong()
    return "$intPart.${decimalPart.toString().padStart(decimals, '0')}"
}

operator fun Size.minus(other: Size) = Size(width - other.width, height - other.height)

fun Size.toOffset() = Offset(width, height)
