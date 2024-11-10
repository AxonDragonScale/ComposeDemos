package com.axondragonscale.compose.demo.util

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

/**
 * Created by Ronak Harkhani on 24/10/24
 */

fun Float.round(decimals: Int) = "%.${decimals}f".format(this).toFloat()
fun Float.toString(decimals: Int) = "%.${decimals}f".format(this)

operator fun Size.minus(other: Size) = Size(width - other.width, height - other.height)

fun Size.toOffset() = Offset(width, height)
