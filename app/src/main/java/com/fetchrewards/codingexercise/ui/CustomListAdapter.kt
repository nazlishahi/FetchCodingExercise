package com.fetchrewards.codingexercise.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.fetchrewards.codingexercise.R
import com.fetchrewards.codingexercise.data.local.LocalListItemModel
import com.fetchrewards.codingexercise.databinding.ViewHeaderItemBinding
import com.fetchrewards.codingexercise.databinding.ViewListItemBinding
import com.fetchrewards.codingexercise.ui.model.GroupedItem

class CustomListAdapter : ListAdapter<GroupedItem, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            HEADER_ITEM_VIEW_TYPE -> HeaderItemViewHolder(inflater.inflate(R.layout.view_header_item, parent, false))
            LIST_ITEM_VIEW_TYPE -> ListItemViewHolder(inflater.inflate(R.layout.view_list_item, parent, false))
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is GroupedItem.Header -> {
                (holder as HeaderItemViewHolder).bind(item.listId)
            }
            is GroupedItem.Item -> {
                (holder as ListItemViewHolder).bind(item.localItemModel)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        return when (item) {
            is GroupedItem.Header -> HEADER_ITEM_VIEW_TYPE
            is GroupedItem.Item -> LIST_ITEM_VIEW_TYPE
        }
    }

    class HeaderItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewHeaderItemBinding.bind(itemView)

        fun bind(listId: Int) {
            binding.headerTextView.text = itemView.resources.getString(R.string.text_header_item_information_TEMPLATE, listId)
        }
    }

    class ListItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ViewListItemBinding.bind(itemView)

        fun bind(listItem: LocalListItemModel) {
            binding.textView.text =
                itemView.resources.getString(
                    R.string.text_list_item_information_TEMPLATE,
                    listItem.name,
                    listItem.id,
                    listItem.listId,
                )
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<GroupedItem>() {
        override fun areItemsTheSame(
            oldItem: GroupedItem,
            newItem: GroupedItem,
        ): Boolean {
            return when {
                oldItem is GroupedItem.Header && newItem is GroupedItem.Header -> oldItem == newItem
                oldItem is GroupedItem.Item && newItem is GroupedItem.Item -> oldItem == newItem
                else -> false
            }
        }

        override fun areContentsTheSame(
            oldItem: GroupedItem,
            newItem: GroupedItem,
        ): Boolean {
            return oldItem == newItem
        }
    }

    companion object {
        private const val HEADER_ITEM_VIEW_TYPE = 0
        private const val LIST_ITEM_VIEW_TYPE = 1
    }
}
