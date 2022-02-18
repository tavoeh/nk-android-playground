package com.tavoeh.firstfearture.presentation

import app.cash.turbine.test
import com.tavoeh.firstfearture.FirstFeature
import com.tavoeh.firstfearture.domain.contract.FirstFeatureRepository
import com.tavoeh.firstfearture.domain.model.FeatureType
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.BehaviorSpec
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
class LandingViewModelBehaviourSpecTest : BehaviorSpec({

    lateinit var viewModel: LandingViewModel
    val repository = mockk<FirstFeatureRepository>()
    val settings = mockk<FirstFeature.Settings>()

    beforeSpec { Dispatchers.setMain(StandardTestDispatcher()) }
    afterSpec { Dispatchers.resetMain() }

    beforeTest {
        every { settings.type } returns FeatureType.FREE
        viewModel = LandingViewModel(repository, settings)
    }


    Given("name is sam") {
        val name = "sam"

        Then("name should have length 3") {
            name.shouldHaveLength(3)
        }
    }


    Given("two numbers") {
        val a = 2
        val b = 3

        When("multiply numbers") {
            val result = viewModel.multiply(a, b)

            Then("return expected result") {
                result shouldBeExactly 6
            }
        }
    }

    Given("repository throws error") {
        val error = Exception()
        coEvery { repository.getFeaturesByType(any()) } throws error

        When("featuresFlow is observed") {
            val result = viewModel.featuresFlow.toList()

            Then("emit Loading and Failure") {
                result shouldBe listOf(
                    LandingViewModel.ViewState.Loading,
                    LandingViewModel.ViewState.Failure(error)
                )
            }
        }
    }

    Given("list of features") {
        val features = listOf("a", "b")
        coEvery { repository.getFeaturesByType(any()) } returns features

        When("viewmodel is initialized ") {
            // viewmodel init block

            Then("emit expected state") {
                runTest {
                    viewModel.featuresStateFlow.test {
                        LandingViewModel.ViewState.Data(listOf<String>()) shouldBe awaitItem()
                        LandingViewModel.ViewState.Loading shouldBe awaitItem()
                        LandingViewModel.ViewState.Data(features) shouldBe awaitItem()

                        cancelAndConsumeRemainingEvents()
                    }
                }
            }
        }
    }
})


