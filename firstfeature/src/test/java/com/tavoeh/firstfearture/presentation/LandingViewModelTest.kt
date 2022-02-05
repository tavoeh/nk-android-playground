package com.tavoeh.firstfearture.presentation

import app.cash.turbine.test
import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.firstfearture.domain.contract.FirstFeatureRepository
import com.tavoeh.firstfearture.domain.model.FeatureType
import com.tavoeh.firstfearture.presentation.LandingViewModel.ViewState.Data
import com.tavoeh.firstfearture.presentation.LandingViewModel.ViewState.Failure
import com.tavoeh.firstfearture.presentation.LandingViewModel.ViewState.Loading
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.any
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LandingViewModelTest {

    private lateinit var viewModel: LandingViewModel

    @Mock private lateinit var repository: FirstFeatureRepository
    @Mock private lateinit var settings: FirstFeature.Settings

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        `when`(settings.type).thenReturn(FeatureType.FREE)
        viewModel = LandingViewModel(repository, settings)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `GIVEN two numbers WHEN multiply numbers THEN return expected result`() {
        val result = viewModel.multiply(2, 3)
        assertEquals(6, result)
    }

    @Test
    fun `GIVEN feature type WHEN finding features by type THEN return expected features`() {
        val features = listOf("a", "b")
        `when`(repository.getFeaturesByTypeSync(FeatureType.PAID)).thenReturn(features)
        val result = viewModel.getFeaturesByTypeSync(FeatureType.PAID)
        assertEquals(features, result)
    }

    @Test
    fun `WHEN finding features THEN call repository`() {
        `when`(repository.getFeaturesByTypeSync(anyNotNull())).thenReturn(listOf())
        viewModel.getFeaturesByTypeSync(FeatureType.PAID)
        verify(repository).getFeaturesByTypeSync(anyNotNull())
    }

    @Test
    fun `GIVEN feature type WHEN finding features by type async THEN return expected features`() = runTest {
        val error = Exception()
        `when`(repository.getFeaturesByType(anyNotNull())).thenAnswer { throw error }
        `when`(settings.type).thenReturn(FeatureType.PAID)
        assertEquals(listOf(Loading, Failure(error)), viewModel.featuresFlow.toList())
    }

    @Test
    fun `WHEN viewmodel is initialized THEN emit expected state`() = runTest {
        val features = listOf("a", "b")
        `when`(repository.getFeaturesByType(anyNotNull())).thenReturn(features)

        viewModel.featuresStateFlow.test {
            assertEquals(Data(listOf<String>()), awaitItem())
            assertEquals(Loading, awaitItem())
            assertEquals(Data(features), awaitItem())

            verify(repository).getFeaturesByType(anyNotNull())
            cancelAndConsumeRemainingEvents()
        }
    }
}

private fun <T> anyNotNull(): T = any()
