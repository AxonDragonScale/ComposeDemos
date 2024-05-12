package com.axondragonscale.compose.demo.shaders

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
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

    val verticalPagerState = rememberPagerState { pages.size }
    VerticalPager(
        modifier = Modifier
            .weight(0.2f)
            .padding(horizontal = 32.dp)
            .padding(bottom = 32.dp),
        state = verticalPagerState,
    ) { page ->
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = pages[page].title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Thin,
            )

            Text(
                text = pages[page].desc,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
            )
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow {
            pagerState.currentPage to pagerState.currentPageOffsetFraction
        }.collect { (page, offset) ->
            verticalPagerState.scrollToPage(page, offset)
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
