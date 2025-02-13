package com.fetchrewards.codingexercise.data.local

import com.fetchrewards.codingexercise.data.remote.ListItem

data class LocalListItemModel(
    val name: String,
    val listId: Int,
    val id: Int,
) {
    companion object {
        fun fromApiResponse(listItem: ListItem): LocalListItemModel {
            return LocalListItemModel(
                name = listItem.name.orEmpty(),
                listId = listItem.listId,
                id = listItem.id,
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as LocalListItemModel

        if (name != other.name) return false
        if (listId != other.listId) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + listId
        result = 31 * result + id
        return result
    }

    override fun toString(): String {
        return "name='$name', listId=$listId, id=$id"
    }
}
