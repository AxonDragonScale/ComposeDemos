package com.axondragonscale.compose.demo.morph

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 12/06/24
 */

private enum class Tab(val tabName: String) {
    ShapeEditor("EDITOR"),
    ShapeMorpher("MORPHER");
}

@Composable
fun ShapesAndMorph(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom
    ) {
        var selectedTab by remember { mutableStateOf(Tab.ShapeEditor) }

        when (selectedTab) {
            Tab.ShapeEditor -> ShapeEditor(modifier = Modifier.weight(1f))
            Tab.ShapeMorpher -> ShapeMorpher(modifier = Modifier.weight(1f))
        }

        TabBar(
            tabs = Tab.entries,
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
    }
}

@Composable
private fun TabBar(
    modifier: Modifier = Modifier,
    tabs: List<Tab>,
    selectedTab: Tab,
    onTabSelected: (Tab) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        tabs.forEach { tab ->
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clickable { onTabSelected(tab) },
                contentAlignment = Alignment.Center
            ) {
                val isSelected = selectedTab == tab

                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .height(4.dp)
                        .fillMaxWidth(0.7f)
                        .background(
                            MaterialTheme.colorScheme.primary
                                .copy(alpha = if (isSelected) 1f else 0f)
                        )
                )

                Text(
                    modifier = Modifier.padding(top = 2.dp),
                    text = tab.tabName,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                        .copy(alpha = if (isSelected) 1f else 0.6f),
                    fontFamily = FontFamily.SansSerif,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MorphPreview() {
    ComposeDemosTheme {
        Surface {
            ShapesAndMorph()
        }
    }
}
