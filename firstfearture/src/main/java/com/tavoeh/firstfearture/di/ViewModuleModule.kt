package com.tavoeh.firstfearture.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tavoeh.firstfearture.presentation.LandingViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModuleModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LandingViewModel::class)
    abstract fun bindLandingViewModel(viewModel: LandingViewModel): ViewModel
}
