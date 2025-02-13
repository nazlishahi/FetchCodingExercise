package com.fetchrewards.codingexercise.usecase

import com.fetchrewards.codingexercise.data.local.LocalListItemModel
import com.fetchrewards.codingexercise.repository.GetListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetListUseCase
    @Inject
    constructor(
        private val repository: GetListRepository,
    ) {
        suspend operator fun invoke(): Result<Map<Int, List<LocalListItemModel>>> =
            withContext(Dispatchers.IO) {
                repository.fetchList().mapCatching { items ->
                    items
                        .filterNot { it.name.isNullOrEmpty() }
                        .map { LocalListItemModel.fromApiResponse(it) }
                        .groupBy { it.listId }
                        .toSortedMap()
                        .mapValues { entry ->
                            entry.value.sortedWith(
                                compareBy {
                                    it.name.replace(Regex("(\\d+)")) { matchResult ->
                                        matchResult.groupValues[1].padStart(10, '0')
                                    }
                                },
                            )
                        }
                }
            }
    }
