package com.fetchrewards.codingexercise.ui.model

import com.fetchrewards.codingexercise.data.local.LocalListItemModel

sealed class GroupedItem {
    data class Header(val listId: Int) : GroupedItem() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Header

            return listId == other.listId
        }

        override fun hashCode(): Int {
            return listId
        }
    }

    data class Item(val localItemModel: LocalListItemModel) : GroupedItem() {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Item

            return localItemModel == other.localItemModel
        }

        override fun hashCode(): Int {
            return localItemModel.hashCode()
        }
    }
}
