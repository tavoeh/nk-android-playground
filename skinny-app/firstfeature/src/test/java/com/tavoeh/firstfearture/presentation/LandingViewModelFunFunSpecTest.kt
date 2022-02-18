package com.tavoeh.firstfearture.presentation

import app.cash.turbine.test
import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.firstfearture.domain.contract.FirstFeatureRepository
import com.tavoeh.firstfearture.domain.model.FeatureType
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldHaveLength
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain

@ExperimentalCoroutinesApi
@ExperimentalKotest
class LandingViewModelFunFunSpecTest : FunSpec({

    beforeSpec { Dispatchers.setMain(StandardTestDispatcher()) }
    afterSpec { Dispatchers.resetMain() }

    lateinit var viewModel: LandingViewModel
    val repository = mockk<FirstFeatureRepository>()
    val settings = mockk<FirstFeature.Settings>()

    beforeTest {
        every { settings.type } returns FeatureType.FREE
        viewModel = LandingViewModel(repository, settings)
    }

    test("this is my first test") {
        val name = "sam"
        name.shouldHaveLength(3)
    }

    test("GIVEN two numbers WHEN multiply numbers THEN return expected result") {
        val result = viewModel.multiply(2, 3)
        result shouldBeExactly 6
    }

    test("GIVEN feature type WHEN finding features by type async THEN return expected features") {
        val error = Exception()
        coEvery { repository.getFeaturesByType(any()) } throws error
        viewModel.featuresFlow.toList() shouldBe listOf(
            LandingViewModel.ViewState.Loading,
            LandingViewModel.ViewState.Failure(error)
        )
    }

    test("WHEN viewmodel is initialized THEN emit expected state") {
        val features = listOf("a", "b")
        coEvery { repository.getFeaturesByType(any()) } returns features
        runTest {
            viewModel.featuresStateFlow.test {
                LandingViewModel.ViewState.Data(listOf<String>()) shouldBe awaitItem()
                LandingViewModel.ViewState.Loading shouldBe awaitItem()
                LandingViewModel.ViewState.Data(features) shouldBe awaitItem()
                cancelAndConsumeRemainingEvents()
            }
        }
    }
})
