package com.axondragonscale.compose.demo.counter

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Created by Ronak Harkhani on 27/04/24
 */

/**
 * Known Issue:
 * When number is incremented by large amount (say 25) which will change 2 character and
 * the increments are triggered faster than the animation can complete, the last character is not
 * rendered until the animation of the previous character fully completes once.
 */

@Composable
fun <T> AnimatedCounter(
    modifier: Modifier = Modifier,
    number: T,
    convertToString: ((T) -> String) = { it.toString() },
    drawChar: @Composable (Char) -> Unit,
) where T : Number, T : Comparable<T> {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        convertToString(number)
            .map { c -> Digit(c, number) }
            .forEach { digit ->
                AnimatedContent(
                    modifier = Modifier.fillMaxHeight(),
                    targetState = digit,
                    transitionSpec = {
                        if (targetState.fullNumber > initialState.fullNumber)
                            slideInVertically { -it } togetherWith slideOutVertically { it }
                        else
                            slideInVertically { it } togetherWith slideOutVertically { -it }
                    },
                    contentAlignment = Alignment.Center,
                ) { digit ->
                    drawChar(digit.char)
                }
            }
    }
}

private data class Digit<T>(
    val char: Char,
    val fullNumber: T,
) where T : Number, T : Comparable<T> {
    override fun equals(other: Any?): Boolean {
        return other is Digit<*> && other.char == char
    }
}
