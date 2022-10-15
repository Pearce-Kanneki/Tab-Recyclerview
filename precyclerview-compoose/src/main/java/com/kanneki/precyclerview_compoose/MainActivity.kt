package com.kanneki.precyclerview_compoose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kanneki.precyclerview_compoose.compose.ItemCell
import com.kanneki.precyclerview_compoose.compose.TextTabs
import com.kanneki.precyclerview_compoose.data.DemoDataType
import com.kanneki.precyclerview_compoose.ui.theme.PositionRecyclerviewTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class MainActivity : ComponentActivity() {

    companion object {
        // GridCell (一行要顯示幾個)
        const val SPAN_COUNT = 2
    }

    /**
     * ViewModel
     */
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val state = viewModel.state
            val listState = rememberLazyGridState()
            val scrollIndex = remember { mutableStateOf(0) }

            PositionRecyclerviewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Column(modifier = Modifier.fillMaxSize()) {

                        TextTabs(
                            tabIndex = state.tabIndex,
                            names = state.titles,
                            modifier = Modifier
                                .fillMaxWidth(),
                            clickIndexClick = {
                                viewModel.setTabSelectIndex(it)
                            },
                            clickEvent = {
                                scrollIndex.value = it
                            }
                        )

                        LazyVerticalGrid(
                            state = listState,
                            columns = GridCells.Fixed(SPAN_COUNT),
                            contentPadding = PaddingValues(5.dp),
                            verticalArrangement = Arrangement.spacedBy(5.dp),
                            horizontalArrangement = Arrangement.spacedBy(5.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            state.items.forEach {
                                val itemSpan = GridItemSpan(
                                    if (it.type == DemoDataType.TITLE) SPAN_COUNT else 1
                                )
                                item(span = { itemSpan }) {
                                    ItemCell(data = it)
                                }
                            }
                        }

                        //TODO 應該可以在優化
                        LaunchedEffect(listState) {
                            snapshotFlow { listState.firstVisibleItemIndex }
                                .map { it / 11 }
                                .distinctUntilChanged()
                                .collectLatest {
                                    viewModel.setTabSelectIndex(it)
                                }
                        }

                        LaunchedEffect(scrollIndex.value) {
                            // 無動畫
//                                listState.scrollToItem(index = it)
                            // 有動畫
                            listState.animateScrollToItem(index = scrollIndex.value)
                        }
                    }
                }
            }
        }
    }
}
