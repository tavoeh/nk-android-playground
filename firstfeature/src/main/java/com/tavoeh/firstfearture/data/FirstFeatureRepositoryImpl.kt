package com.tavoeh.firstfearture.data

import com.tavoeh.firstfearture.domain.contract.FirstFeatureRepository
import com.tavoeh.firstfearture.domain.model.FeatureType
import kotlinx.coroutines.delay
import javax.inject.Inject

class FirstFeatureRepositoryImpl @Inject constructor() : FirstFeatureRepository {

    override suspend fun getFeaturesByType(type: FeatureType): List<String> {
        delay(2000)
        return when (type) {
            FeatureType.FREE -> listOf("One account")
            FeatureType.PAID -> listOf("Multiples accounts", "Offline support")
        }
    }

    override fun getFeaturesByTypeSync(type: FeatureType): List<String> {
        return when (type) {
            FeatureType.FREE -> listOf("One account")
            FeatureType.PAID -> listOf("Multiples accounts", "Offline support")
        }
    }
}
