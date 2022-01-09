package com.tavoeh.firstfearture

import android.app.Application
import com.tavoeh.firstfearture.di.DaggerFeatureComponent
import com.tavoeh.firstfearture.di.FeatureComponent
import com.tavoeh.firstfearture.domain.model.FeatureType

class FirstFeature(dependencies: Dependencies) {

    val component: FeatureComponent by lazy {
        DaggerFeatureComponent.factory().create(dependencies)
    }

    interface Dependencies {
        val application: Application
        val settings: Settings
    }

    interface Provider {
        val firstFeature: FirstFeature
    }

    interface Settings {
        val version: String
        val type: FeatureType
    }
}

internal val Application.firstFeature: FirstFeature
    get() = (applicationContext as FirstFeature.Provider).firstFeature
