package com.kanneki.precyclerview_compoose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.kanneki.precyclerview_compoose.data.DemoData
import com.kanneki.precyclerview_compoose.data.DemoDataType
import com.kanneki.precyclerview_compoose.data.StateViewData
import com.kanneki.precyclerview_compoose.data.TitleData

class MainViewModel : ViewModel() {

    companion object {
        const val FAKE_LEN = 200
    }

    /**
     * View Data
     */
    var state by mutableStateOf(StateViewData())

    /**
     * Tabs對應LazyColum位置
     */
    private val tabsPositions = mutableListOf<Int>()

    init {
        setFakeData(FAKE_LEN)
    }

    /**
     * 設定假資料
     *
     * @param fakeLen 假資料長度
     */
    private fun setFakeData(fakeLen: Int) {

        val ret = mutableListOf<DemoData>()
        repeat(fakeLen) {
            val data = DemoData(
                id = it,
                title = if (it % 11 == 0) "Title ${it + 1}" else "Detail ${it + 1}",
                des = "Detail ${it + 1}",
                type = if (it % 11 == 0) DemoDataType.TITLE else DemoDataType.DETAIL
            )

            ret.add(data)
        }

        val tabsList = ret.filter { it.type == DemoDataType.TITLE }
            .map { TitleData(name = it.title, position = it.id) }

        state = state.copy(ret, tabsList)

        tabsPositions.clear()
        tabsPositions.addAll(tabsList.map { it.position })
    }

    /**
     * 紀錄tabs選中項目
     *
     * @param index 選中項目
     */
    fun setTabSelectIndex(index: Int) {
        if (index != state.tabIndex)
            state = state.copy(tabIndex = index)
    }
}