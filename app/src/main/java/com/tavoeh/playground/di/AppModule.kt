package com.tavoeh.playground.di

import android.app.Application
import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.firstfearture.domain.model.FeatureType
import com.tavoeh.secondfeature.SecondFeature
import dagger.Module
import dagger.Provides

@Module
object AppModule {

    @Provides
    fun provideFirstFeature(application: Application): FirstFeature {
        val settings = object : FirstFeature.Settings {
            override val version: String = "v.1.0.0"
            override val type = FeatureType.PAID
        }

        val dependencies = object : FirstFeature.Dependencies {
            override val application: Application = application
            override val settings: FirstFeature.Settings = settings
        }

        return FirstFeature(dependencies)
    }

    @Provides
    fun providesSecondFeature(application: Application): SecondFeature {
        val settings = object : SecondFeature.Settings {}
        val dependencies = object : SecondFeature.Dependencies {
            override val application: Application = application
            override val settings: SecondFeature.Settings = settings
        }
        return SecondFeature(dependencies)
    }
}
