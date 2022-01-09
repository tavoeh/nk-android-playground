package com.tavoeh.firstfearture.di

import com.tavoeh.firstfearture.data.FirstFeatureRepositoryImpl
import com.tavoeh.firstfearture.domain.contract.FirstFeatureRepository
import dagger.Binds
import dagger.Module

@Module
abstract class FeatureModule {

    @Binds
    abstract fun bindFirstFeatureRepository(repositoryImpl: FirstFeatureRepositoryImpl): FirstFeatureRepository
}
