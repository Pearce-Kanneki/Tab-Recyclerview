package com.kanneki.positionrecyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * GridLayoutManager 設定間距
 *
 * @param mSpanCount 每行個數
 * @param mSpacing 間距
 * @param mIncludeEdge 螢幕周圍是否有間距
 * @param mStartFromSize 頭部 不顯示間距的item個數
 * @param mEndFromSize 尾部 不顯示間距的item個數
 *
 * @see <a href="https://juejin.cn/post/6844904070088491016">參考資料</a>
 */
class GridSpaceItemDecoration(
    private var mSpanCount: Int,
    private val mSpacing: Int = 10,
    private val mIncludeEdge: Boolean = true,
    private val mStartFromSize: Int = 0,
    private val mEndFromSize: Int = 1
): RecyclerView.ItemDecoration() {


    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val lastPosition = state.itemCount - 1
        var position = parent.getChildLayoutPosition(view)

        if (mStartFromSize <= position && position <= lastPosition - mEndFromSize) {
            var spanGroupIndex = -1 // 行
            var colum = 0 // 列

            val layoutManager = parent.layoutManager
            if (layoutManager is GridLayoutManager) {

                val gridLayoutManager: GridLayoutManager = layoutManager
                val spanSizeLookup = gridLayoutManager.spanSizeLookup
                val spanCount = gridLayoutManager.spanCount
                // 當前position的spanSize
                val spanSize = spanSizeLookup.getSpanSize(position)
                // 一行幾個
                mSpanCount = spanCount / spanSize
                // 最左邊
                val spanIndex = spanSizeLookup.getSpanIndex(position, spanCount)
                // 列 減去mStartFromSize,得到從0開始的行
                colum = spanIndex / spanSize
                spanGroupIndex = spanSizeLookup.getSpanGroupIndex(position, spanCount) - mStartFromSize
            }

            // 減掉不設置間距的position,得到從0開始的position
            position -= mStartFromSize

            if (mIncludeEdge) {

                outRect.left = mSpacing - colum * mSpacing / mSpanCount
                outRect.right = (colum + 1) * mSpacing / mSpanCount

                // Grid 顯示規則
                if (spanGroupIndex < 1 && position < mSpanCount) {
                    outRect.top = mSpacing
                }

                outRect.bottom = mSpacing
            } else {

                outRect.left = colum * mSpacing / mSpanCount
                outRect.right = mSpacing - (colum + 1) * mSpacing / mSpanCount


                when {
                    spanGroupIndex >= 1 -> {
                        outRect.top = mSpacing
                    }
                    spanGroupIndex < 0 -> {
                        if (position >= mSpanCount) {
                            outRect.top = mSpacing
                        }
                    }
                }
            }
        }
    }
}