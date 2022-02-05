package com.tavoeh.firstfearture.domain.contract

import com.tavoeh.firstfearture.domain.model.FeatureType

interface FirstFeatureRepository {

    suspend fun getFeaturesByType(type: FeatureType): List<String>
    fun getFeaturesByTypeSync(type: FeatureType): List<String>
}
