package com.tavoeh.secondfeature

import android.app.Application

class SecondFeature(dependencies: Dependencies) {

    interface Dependencies {
        val application: Application
        val settings: Settings
    }

    interface Settings

    interface Provider {
        val secondFeature: SecondFeature
    }
}

internal val Application.secondFeature: SecondFeature
    get() = (applicationContext as SecondFeature.Provider).secondFeature
