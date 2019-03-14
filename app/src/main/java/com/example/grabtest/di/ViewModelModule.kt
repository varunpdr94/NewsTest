package com.example.grabtest.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.example.grabtest.ui.newsHome.viewModel.NewsHomeFragmentViewModel
import com.example.grabtest.utils.TestViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: TestViewModelFactory): ViewModelProvider.Factory


    @Binds
    @IntoMap
    @ViewModelKey(NewsHomeFragmentViewModel::class)
    abstract fun bindHomeViewModel(viewModel: NewsHomeFragmentViewModel): ViewModel


}
