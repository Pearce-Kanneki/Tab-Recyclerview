package com.kanneki.positionrecyclerview.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.kanneki.positionrecyclerview.DemoAdapter
import com.kanneki.positionrecyclerview.DemoDataType
import com.kanneki.positionrecyclerview.GridSpaceItemDecoration
import com.kanneki.positionrecyclerview.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    companion object {
        // 假資料數量
        const val FAKE_DATA_LEN = 200
        // Recyclerview一行有幾個
        const val SPAN_COUNT = 2
    }

    // Layout
    private lateinit var binding: ActivityMainBinding

    // ViewModel
    private val viewModel by viewModels<MainViewModel>()

    // RecyclerView Adapter
    private val mAdapter = DemoAdapter {
        Toast.makeText(this, "Click: $it", Toast.LENGTH_SHORT).show()
    }

    // RecyclerView LayoutManager
    private val mLayoutManager by lazy {
        GridLayoutManager(
            this,
            SPAN_COUNT,
            GridLayoutManager.VERTICAL,
            false
        )
    }

    // tabLayout Item 對應Recyclerview位置
    private val tabIndexList = mutableListOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding.recyclerview) {
            layoutManager = mLayoutManager
            adapter = mAdapter
            addItemDecoration(GridSpaceItemDecoration(SPAN_COUNT))
        }
    }

    override fun onStart() {
        super.onStart()

        flowObserve()
        viewModel.setFakeDataList(FAKE_DATA_LEN)

        // 監聽TabLayout點擊,讓Recyclerview可以跳到特定位置
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab) {
                tab.tag.toString().toIntOrNull()?.let {
                    // 注意: scrollToPositionWithOffset 需帶入大於0的值(start Index = 0)
                    // 這邊是確認不會小於0所以沒做多餘的判斷
                    mLayoutManager.scrollToPositionWithOffset(it, 0)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })

        // 監聽Recyclerview使得TabLayout可以跳到特定位置
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                //因為是特殊排版(不是所有項目都在tab裡),所以要特別挑選項目的position出來
                tabIndexList.indexOf(mLayoutManager.findFirstVisibleItemPosition()).also {
                    if (it >= 0) {
                        binding.tabLayout.setScrollPosition(
                            it,
                            0F,
                            true
                        )
                    }
                }
            }
        })
    }

    /**
     * 被觀察者設定
     */
    private fun flowObserve() {

        // 觀察假資料
        lifecycleScope.launch {
            viewModel.fakeData.collect {

                // 排版
                mLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        return if (it[position].type == DemoDataType.TITLE) SPAN_COUNT else 1
                    }

                }

                //塞入假資料
                mAdapter.submitList(it)
            }
        }

        // 觀察TabLayout動態資料
        lifecycleScope.launch {
            viewModel.tabList.collect { list ->
                tabAddItem(list)
                tabIndexList.clear()
                tabIndexList.addAll(list.map { it.first })
            }
        }
    }

    /**
     * 將動態資料塞入tabs
     *
     * @param tabs 要塞的資料first -> tag, second -> text
     */
    private fun tabAddItem(tabs: List<Pair<Int, String>>) {
        tabs.forEach {
            val item = binding.tabLayout.newTab().apply {
                text = it.second
                tag = it.first
            }
            binding.tabLayout.addTab(item)
        }
    }
}