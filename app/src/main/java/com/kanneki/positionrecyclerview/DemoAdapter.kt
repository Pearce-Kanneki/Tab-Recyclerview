package com.kanneki.positionrecyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.kanneki.positionrecyclerview.databinding.ItemDetailBinding
import com.kanneki.positionrecyclerview.databinding.ItemEmptyBinding
import com.kanneki.positionrecyclerview.databinding.ItemTitleBinding

/**
 * RecyclerView Adapter
 *
 * @param callback 回傳資料(String)
 */
class DemoAdapter(val callback: (String) -> Unit) :
    ListAdapter<DemoData, DemoAdapter.DemoViewHolder>(diffUtil) {

    companion object {
        const val TYPE_NONE = 100
        const val TYPE_TITLE = 101
        const val TYPE_DETAIL = 102
    }

    /**
     * 判斷item要顯示哪種Layout
     */
    override fun getItemViewType(position: Int): Int {
        return when(getItem(position).type) {
            DemoDataType.NONE -> TYPE_NONE
            DemoDataType.TITLE -> TYPE_TITLE
            DemoDataType.DETAIL -> TYPE_DETAIL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DemoViewHolder {
        val inference = LayoutInflater.from(parent.context)

        return when(viewType) {
            TYPE_TITLE -> {
                // 顯示Title Layout(沒有圖片)
                val view = ItemTitleBinding.inflate(inference, parent, false)
                DemoViewHolder(view)
            }
            TYPE_DETAIL -> {
                // 顯示Detail Layout(有圖片得那個)
                val view = ItemDetailBinding.inflate(inference, parent, false)
                DemoViewHolder(view)
            }
            else -> {
                // 空白Layout(理論上不應該出現此Layout)
                // 若出現此Layout代表哪裡一定有寫錯
                val view = ItemEmptyBinding.inflate(inference, parent, false)
                DemoViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: DemoViewHolder, position: Int) {
        holder.setData(getItem(position))
    }

    inner class DemoViewHolder(private val binding: ViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * 塞入item 資料
         *
         * @param data item 資料
         */
        fun setData(data: DemoData) {
            when (binding) {
                is ItemTitleBinding -> {
                    // Title Layout
                    binding.titleValue.text = data.title
                }
                is ItemDetailBinding -> {
                    // Detail Layout
                    binding.message.text = data.des
                    binding.root.setOnClickListener { callback(data.title) }
                }
            }
        }

    }
}

val diffUtil = object : DiffUtil.ItemCallback<DemoData>() {

    override fun areItemsTheSame(oldItem: DemoData, newItem: DemoData): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: DemoData, newItem: DemoData): Boolean {
        return oldItem == newItem
    }

}