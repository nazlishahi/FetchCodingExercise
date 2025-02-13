package com.fetchrewards.codingexercise

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.fetchrewards.codingexercise.data.local.LocalListItemModel
import com.fetchrewards.codingexercise.ui.model.GroupedItem
import com.fetchrewards.codingexercise.usecase.GetListUseCase
import com.fetchrewards.codingexercise.viewmodel.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.KArgumentCaptor
import org.mockito.kotlin.argumentCaptor
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class ListViewModelTest {
    private lateinit var viewModel: ListViewModel

    @Mock
    lateinit var progressFlagObserver: Observer<Boolean>

    @Mock
    lateinit var uiStateObserver: Observer<ListViewModel.UiState>

    @Mock
    lateinit var getListUseCase: GetListUseCase

    private lateinit var uiStateCaptor: KArgumentCaptor<ListViewModel.UiState>

    private lateinit var progressFlagCaptor: KArgumentCaptor<Boolean>

    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    val rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = ListViewModel(getListUseCase)
        viewModel.ioContext = testDispatcher

        uiStateCaptor = argumentCaptor<ListViewModel.UiState>()
        progressFlagCaptor = argumentCaptor<Boolean>()

        viewModel.progressBarFlag.observeForever(progressFlagObserver)
        viewModel.uiState.observeForever(uiStateObserver)
    }

    @After
    fun tearDown() {
        viewModel.progressBarFlag.removeObserver(progressFlagObserver)
        viewModel.uiState.removeObserver(uiStateObserver)
        Dispatchers.resetMain()
    }

    @Test
    fun testGetList_success() =
        runTest {
            val mockItem1 = LocalListItemModel("Item 1", 1, 1)
            val mockItem2 = LocalListItemModel("Item 2", 1, 2)
            val mockItem3 = LocalListItemModel("Item 10", 2, 10)
            val mockItem4 = LocalListItemModel("Item 20", 2, 20)

            val mockMap =
                mapOf(
                    1 to
                        listOf(
                            mockItem1,
                            mockItem2,
                        ),
                    2 to
                        listOf(
                            mockItem3,
                            mockItem4,
                        ),
                )

            val expectedDisplayList =
                listOf(
                    GroupedItem.Header(1),
                    GroupedItem.Item(mockItem1),
                    GroupedItem.Item(mockItem2),
                    GroupedItem.Header(2),
                    GroupedItem.Item(mockItem3),
                    GroupedItem.Item(mockItem4),
                )
            whenever(getListUseCase.invoke()).thenReturn(Result.success(mockMap))

            viewModel.getList()

            Mockito.verify(progressFlagObserver, times(2))
                .onChanged(progressFlagCaptor.capture())

            Mockito.verify(getListUseCase, times(1)).invoke()

            Mockito.verify(uiStateObserver, times(1))
                .onChanged(uiStateCaptor.capture())

            Assert.assertEquals(progressFlagCaptor.allValues.size, 2)
            Assert.assertEquals(progressFlagCaptor.allValues[0], true)
            Assert.assertEquals(progressFlagCaptor.allValues[1], false)

            Assert.assertEquals(uiStateCaptor.allValues.size, 1)
            Assert.assertTrue(uiStateCaptor.allValues[0] is ListViewModel.UiState.PopulateList)
            val state = uiStateCaptor.allValues[0] as ListViewModel.UiState.PopulateList
            Assert.assertEquals(expectedDisplayList, state.list)
        }

    @Test
    fun testGetList_failure() =
        runTest {
            val mockThrowable = mock<Throwable>()
            whenever(getListUseCase.invoke()).thenReturn(Result.failure(mockThrowable))

            viewModel.getList()

            Mockito.verify(progressFlagObserver, times(2))
                .onChanged(progressFlagCaptor.capture())

            Mockito.verify(getListUseCase, times(1)).invoke()

            Mockito.verify(uiStateObserver, times(1))
                .onChanged(uiStateCaptor.capture())

            Assert.assertEquals(progressFlagCaptor.allValues.size, 2)
            Assert.assertEquals(progressFlagCaptor.allValues[0], true)
            Assert.assertEquals(progressFlagCaptor.allValues[1], false)

            Assert.assertEquals(uiStateCaptor.allValues.size, 1)
            Assert.assertTrue(uiStateCaptor.allValues[0] is ListViewModel.UiState.Error)
        }
}
