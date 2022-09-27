package com.kanneki.positionrecyclerview.ui

import androidx.lifecycle.ViewModel
import com.kanneki.positionrecyclerview.DemoData
import com.kanneki.positionrecyclerview.DemoDataType
import kotlinx.coroutines.flow.MutableStateFlow

class MainViewModel: ViewModel() {

    // 儲存假資料陣列 by flow
    private val _fakeData = MutableStateFlow<List<DemoData>>(listOf())
    val fakeData get() = _fakeData

    // 儲存tabLayout tab陣列 by flow
    private val _tabList = MutableStateFlow<List<Pair<Int, String>>>(listOf())
    val tabList get() = _tabList

    /**
     * 設定假資料
     *
     * @param len 假資料數量
     */
    fun setFakeDataList(len: Int) {

        // 假資料
        val ret = mutableListOf<DemoData>()
        repeat(len) {
            val data = DemoData(
                id = it,
                title = if (it % 11 == 0) "Title ${it + 1}" else "Detail ${it + 1}",
                des = "Detail ${it + 1}",
                type = if (it % 11 == 0) DemoDataType.TITLE else DemoDataType.DETAIL
            )

            ret.add(data)
        }

        _fakeData.tryEmit(ret)

        val tmpTitle = ret.filter { it.type == DemoDataType.TITLE }
            .map { Pair(it.id, it.title) }
        _tabList.tryEmit(tmpTitle)
    }
}