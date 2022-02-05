package com.tavoeh.firstfearture.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tavoeh.firstfearture.databinding.ActivityLandingBinding
import com.tavoeh.firstfearture.firstFeature
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class LandingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLandingBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LandingViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        application.firstFeature.component.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.version.observe(this) {
            binding.message.text = it
        }

        lifecycleScope.launchWhenCreated {
            viewModel.featuresFlow.collect { handleState(it) }
//            viewModel.featuresStateFlow.collect { handleState(it) }
        }
    }

    private fun handleState(state: LandingViewModel.ViewState<List<String>>) {
        binding.features.text = when (state) {
            is LandingViewModel.ViewState.Data -> state.data.joinToString(separator = ", ")
            is LandingViewModel.ViewState.Failure -> "Error"
            LandingViewModel.ViewState.Loading -> "Loading"
        }
    }
}
