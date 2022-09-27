package com.kanneki.positionrecyclerview

import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import java.lang.ref.WeakReference

class TabLayoutOnScrollListener(tabLayout: TabLayout) : RecyclerView.OnScrollListener() {

    private var previousScrollState = 0
    private var scrollState = 0

    // 是否點擊tab滾動
    var tabClickScroll = false

    // TabLayout中Tab的選中狀態
    var selectedTabPosition = -1

    private val tabLayoutRef: WeakReference<TabLayout> = WeakReference(tabLayout)

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (tabClickScroll) return

        //當前可見的第一個Item

    }

}