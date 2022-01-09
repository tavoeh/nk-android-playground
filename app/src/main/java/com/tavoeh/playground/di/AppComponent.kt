package com.tavoeh.playground.di

import android.app.Application
import com.tavoeh.firstfearture.FirstFeature
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): AppComponent
    }

    val firstFeature: FirstFeature
}
