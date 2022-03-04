package com.tavoeh.firstfearture.presentation

import app.cash.turbine.test
import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.firstfearture.domain.contract.FirstFeatureRepository
import com.tavoeh.firstfearture.domain.model.FeatureType
import com.tavoeh.firstfearture.presentation.LandingViewModel.ViewState.Data
import com.tavoeh.firstfearture.presentation.LandingViewModel.ViewState.Failure
import com.tavoeh.firstfearture.presentation.LandingViewModel.ViewState.Loading
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class LandingViewModelMockkTest {

    private lateinit var viewModel: LandingViewModel
    val repository = mockk<FirstFeatureRepository>()
    val settings = mockk<FirstFeature.Settings>()

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        every { settings.type } returns FeatureType.FREE
        viewModel = LandingViewModel(repository, settings)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `WHEN finding features THEN call repository`() {
        coEvery { repository.getFeaturesByTypeSync(any()) } returns listOf()
        viewModel.getFeaturesByTypeSync(FeatureType.PAID)
        verify { repository.getFeaturesByTypeSync(any()) }
    }

    @Test
    fun `GIVEN error type WHEN finding features by type async THEN return expected error`() {
        val error = Exception()
        coEvery { repository.getFeaturesByType(any()) } throws error
        runTest {
            viewModel.featuresFlow.toList() shouldBe listOf(Loading, Failure(error))
        }
    }

    @Test
    fun `WHEN viewmodel is initialized THEN emit expected state`() {
        val features = listOf("a", "b")
        coEvery { repository.getFeaturesByType(any()) } returns features
        runTest {
            viewModel.featuresStateFlow.test {
                awaitItem() shouldBe Data(listOf())
                awaitItem() shouldBe Loading
                awaitItem() shouldBe Data(features)
                cancelAndConsumeRemainingEvents()
            }
        }
    }
}
