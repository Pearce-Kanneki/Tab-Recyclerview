package com.kanneki.precyclerview_compoose.data

data class StateViewData(
    /**
     * LazyColumn 資料
     */
    val items: List<DemoData> = listOf(),
    /**
     * Tabs 資料
     */
    val titles: List<TitleData> = listOf(),
    /**
     * Tabs 選中項目
     */
    val tabIndex: Int = 0
)
