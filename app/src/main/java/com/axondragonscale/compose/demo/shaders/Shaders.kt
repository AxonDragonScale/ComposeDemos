package com.axondragonscale.compose.demo.shaders

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.axondragonscale.compose.demo.ui.theme.ComposeDemosTheme

/**
 * Created by Ronak Harkhani on 10/05/24
 */

data class ShaderPage(
    val title: String,
    val desc: String,
    val content: @Composable () -> Unit,
)

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private val pages = listOf(
    PositionalShaderPage,
    TextShaderPage,
    ImageShaderPage,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Shaders(modifier: Modifier = Modifier) = Column(modifier = modifier.fillMaxSize()) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return@Column

    val pagerState = rememberPagerState { pages.size }
    Box(
        modifier = Modifier
            .weight(1f)
            .padding(vertical = 64.dp, horizontal = 32.dp)
    ) {
        pages[pagerState.currentPage].content()
        HorizontalPager(modifier = Modifier.fillMaxSize(), state = pagerState) {}
    }

    Row(
        modifier = Modifier.padding(bottom = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        IconButton(
            onClick = { },
            enabled = pagerState.currentPage != 0
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBackIos, contentDescription = "")
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = pages[pagerState.currentPage].title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Thin,
            )

            Text(
                text = pages[pagerState.currentPage].desc,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
            )
        }

        IconButton(
            onClick = { },
            enabled = pagerState.currentPage != pages.lastIndex,
        ) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos, contentDescription = "")
        }
    }
}

@Preview(name = "Light", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview(name = "Dark", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ShakePreview() {
    ComposeDemosTheme {
        Surface {
            Shaders()
        }
    }
}
