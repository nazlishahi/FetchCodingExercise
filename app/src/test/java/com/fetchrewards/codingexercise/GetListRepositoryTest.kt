package com.fetchrewards.codingexercise

import com.fetchrewards.codingexercise.data.remote.ListItem
import com.fetchrewards.codingexercise.repository.GetListRepository
import com.fetchrewards.codingexercise.service.ApiService
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class GetListRepositoryTest {
    @Mock
    lateinit var apiService: ApiService

    private lateinit var getListRepository: GetListRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        getListRepository = GetListRepository(apiService)
    }

    @Test
    fun `fetchCountries should return a list of countries when successful`() =
        runTest {
            val mockList =
                listOf(
                    ListItem(6, 3, null),
                    ListItem(4, 2, "Item 4"),
                    ListItem(5, 3, "Item 5"),
                    ListItem(3, 2, "Item 3"),
                    ListItem(2, 1, "Item 2"),
                    ListItem(1, 1, "Item 1"),
                    ListItem(7, 4, ""),
                )
            whenever(apiService.getList()).thenReturn(mockList)

            val result = getListRepository.fetchList()

            Assert.assertTrue(result.isSuccess)
            Assert.assertEquals(mockList, result.getOrDefault(listOf()))
        }

    @Test
    fun `fetchCountries should throw an error when the API call fails`() =
        runTest {
            whenever(apiService.getList()).thenThrow(RuntimeException("Network Error"))

            val result = getListRepository.fetchList()

            Assert.assertTrue(result.isFailure)
            Assert.assertEquals("Network Error", result.exceptionOrNull()?.message)
        }
}
