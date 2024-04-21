package com.axondragonscale.compose.demo.typewriter

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 21/04/24
 */

private const val DefaultText = "Lorem ipsum dolor sit amet. At dignissimos officiis a facilis velit a obcaecati cumque ut distinctio inventore aut libero impedit. Et blanditiis quidem qui nostrum sequi est voluptatem iste vel doloremque beatae est ipsa veritatis et cumque eius eos quisquam aspernatur. Vel quia debitis ut cupiditate optio sed impedit culpa?"

@Composable
fun Typewriter(
    modifier: Modifier = Modifier,
    text: String = DefaultText,
) {
    Box(
        modifier = modifier.fillMaxSize().padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary
            ),
            shape = RoundedCornerShape(16.dp),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                var displayText by remember(text) { mutableStateOf(AnnotatedString("")) }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .typewriterEffect(
                            text = text,
                            onTextUpdate = { displayText = it },
                            onEffectComplete = { }
                        ),
                    text = displayText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TypewriterPreview() {
    ComposeDemosTheme {
        Surface {
            Typewriter()
        }
    }
}
