package com.axondragonscale.compose.demo.util

/**
 * Created by Ronak Harkhani on 24/10/24
 */

fun Float.round(decimals: Int) = "%.${decimals}f".format(this).toFloat()
fun Float.toString(decimals: Int) = "%.${decimals}f".format(this)
