package com.fetchrewards.codingexercise.di

import androidx.lifecycle.ViewModel
import com.fetchrewards.codingexercise.repository.GetListRepository
import com.fetchrewards.codingexercise.service.ApiService
import com.fetchrewards.codingexercise.usecase.GetListUseCase
import com.fetchrewards.codingexercise.viewmodel.ListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ListModule {
    @Singleton
    @Provides
    fun provideRepository(apiService: ApiService): GetListRepository {
        return GetListRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideGetCountriesUseCase(repository: GetListRepository): GetListUseCase {
        return GetListUseCase(repository)
    }

    @Singleton
    @Provides
    fun provideViewModel(getListUseCase: GetListUseCase): ViewModel {
        return ListViewModel(
            getListUseCase,
        )
    }
}
