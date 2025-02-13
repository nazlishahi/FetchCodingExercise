package com.fetchrewards.codingexercise

import com.fetchrewards.codingexercise.data.local.LocalListItemModel
import com.fetchrewards.codingexercise.data.remote.ListItem
import com.fetchrewards.codingexercise.repository.GetListRepository
import com.fetchrewards.codingexercise.usecase.GetListUseCase
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetListUseCaseTest {
    @Mock
    lateinit var getListRepository: GetListRepository

    private lateinit var getListUseCase: GetListUseCase

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getListUseCase = GetListUseCase(getListRepository)
    }

    @Test
    fun testReturnsGroupedItems_FilteringNullOrEmptyNames_GroupedByListId_SortedByListId_thenSortedByName_OnRepoSuccess() =
        runTest {
            val mockList =
                listOf(
                    ListItem(6, 3, null),
                    ListItem(0, 1, "Item 0"),
                    ListItem(4, 2, "Item 4"),
                    ListItem(11, 1, "Item 11"),
                    ListItem(5, 3, "Item 5"),
                    ListItem(2, 1, "Item 2"),
                    ListItem(3, 2, "Item 3"),
                    ListItem(1, 1, "Item 1"),
                    ListItem(7, 4, ""),
                )
            val expectedResult =
                mapOf(
                    1 to
                        listOf(
                            LocalListItemModel("Item 0", 1, 0),
                            LocalListItemModel("Item 1", 1, 1),
                            LocalListItemModel("Item 2", 1, 2),
                            LocalListItemModel("Item 11", 1, 11),
                        ),
                    2 to
                        listOf(
                            LocalListItemModel("Item 3", 2, 3),
                            LocalListItemModel("Item 4", 2, 4),
                        ),
                    3 to
                        listOf(
                            LocalListItemModel("Item 5", 3, 5),
                        ),
                )

            whenever(getListRepository.fetchList()).thenReturn(Result.success(mockList))

            val result = getListUseCase.invoke()
            Assert.assertEquals(expectedResult, result.getOrNull())
        }

    @Test
    fun testReturnsException_onRepoFailure() =
        runTest {
            val mockThrowable = mock<Throwable>()
            whenever(getListRepository.fetchList()).thenReturn(Result.failure(mockThrowable))

            val result = getListUseCase.invoke()
            Assert.assertEquals(mockThrowable, result.exceptionOrNull())
        }
}
