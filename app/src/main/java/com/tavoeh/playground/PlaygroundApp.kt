package com.tavoeh.playground

import android.app.Application
import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.playground.di.AppComponent
import com.tavoeh.playground.di.DaggerAppComponent
import com.tavoeh.secondfeature.SecondFeature

class PlaygroundApp :
    Application(),
    FirstFeature.Provider,
    SecondFeature.Provider {

    private val component: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)
    }

    override val firstFeature: FirstFeature = component.firstFeature
    override val secondFeature: SecondFeature = component.secondFeature
}
