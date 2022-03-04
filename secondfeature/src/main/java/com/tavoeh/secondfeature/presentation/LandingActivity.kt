package com.tavoeh.secondfeature.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.tavoeh.secondfeature.presentation.components.ShapesScreen
import com.tavoeh.secondfeature.presentation.ui.theme.MyPlaygroundTheme

class LandingActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPlaygroundTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    App()
                    val viewModel: LandingViewModel by viewModels()
                    ShapesScreen(viewModel = viewModel)
                }
            }
        }
    }
}

