package com.tavoeh.firstfearture.di

import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.firstfearture.presentation.LandingActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [FeatureModule::class, ViewModuleModule::class],
    dependencies = [FirstFeature.Dependencies::class]
)
interface FeatureComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: FirstFeature.Dependencies): FeatureComponent
    }

    fun inject(landingActivity: LandingActivity)
}
