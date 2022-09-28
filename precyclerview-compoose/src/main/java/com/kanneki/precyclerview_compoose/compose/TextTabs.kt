package com.kanneki.precyclerview_compoose.compose

import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kanneki.precyclerview_compoose.data.TitleData

/**
 * Tabs 設置
 *
 * @param tabIndex 選中項目index
 * @param names 要顯示的名稱
 * @param clickIndexClick 回傳點擊選中位置
 * @param modifier Layout樣式設置
 * @param clickEvent 回傳點擊選中position [TitleData]裡的position
 */
@Composable
fun TextTabs(
    tabIndex: Int,
    names: List<TitleData>,
    modifier: Modifier = Modifier,
    clickIndexClick: (Int) -> Unit,
    clickEvent: (Int) -> Unit
) {

    ScrollableTabRow(
        selectedTabIndex = tabIndex,
        modifier = modifier,
        edgePadding = 0.dp,
    ) {
        names.forEachIndexed { index, item ->
            Tab(
                selected = (tabIndex == index),
                text = {
                    Text(text = item.name, style = MaterialTheme.typography.subtitle1)
                },
                onClick = {
                    clickIndexClick(index)
                    clickEvent(item.position)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TextTabsPreview() {
    val titles = listOf(
        TitleData("Music", 1),
        TitleData("Market", 2),
        TitleData("Films", 3),
        TitleData("Books", 4)
    )
    TextTabs(
        tabIndex = 0,
        names = titles,
        clickIndexClick = {},
        clickEvent = {}
    )
}