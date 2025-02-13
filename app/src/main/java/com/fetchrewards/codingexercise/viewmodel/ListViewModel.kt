package com.fetchrewards.codingexercise.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fetchrewards.codingexercise.ui.model.GroupedItem
import com.fetchrewards.codingexercise.usecase.GetListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel
    @Inject
    constructor(
        private val getListUseCase: GetListUseCase,
    ) : ViewModel() {
        private val _progressBarFlag = MutableLiveData<Boolean>()
        val progressBarFlag: LiveData<Boolean> = _progressBarFlag

        private val _uiState = MutableLiveData<UiState>()
        val uiState: LiveData<UiState> get() = _uiState

        private val errorHandler =
            CoroutineExceptionHandler { _, exception ->
                println("ListViewModel exception:$exception")
            }

        @VisibleForTesting
        var ioContext = Dispatchers.IO + errorHandler

        fun getList() {
            viewModelScope.launch(ioContext) {
                _progressBarFlag.postValue(true)
                runCatching {
                    val result = getListUseCase.invoke()
                    when {
                        result.isSuccess -> {
                            result.getOrNull()?.let {
                                val displayList = mutableListOf<GroupedItem>()
                                result.getOrNull()?.forEach { entry ->
                                    displayList.add(GroupedItem.Header(entry.key))
                                    val items = entry.value.map { GroupedItem.Item(it) }
                                    displayList.addAll(items)
                                }
                                _uiState.postValue(UiState.PopulateList(displayList))
                            } ?: run {
                                _uiState.postValue(UiState.Error())
                            }
                        }
                        result.isFailure -> {
                            _uiState.postValue(UiState.Error(result.exceptionOrNull()?.localizedMessage))
                        }
                    }
                }.onFailure {
                    _uiState.postValue(UiState.Error(it.localizedMessage))
                }
                _progressBarFlag.postValue(false)
            }
        }

        sealed class UiState {
            data class PopulateList(val list: List<GroupedItem>) : UiState()

            data class Error(val errorMessage: String? = null) : UiState()
        }
    }
