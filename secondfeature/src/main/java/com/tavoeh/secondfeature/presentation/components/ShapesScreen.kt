package com.tavoeh.secondfeature.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tavoeh.secondfeature.presentation.LandingViewModel
import com.tavoeh.secondfeature.presentation.LandingViewModel.UIState.*
import com.tavoeh.secondfeature.presentation.ui.theme.MyPlaygroundTheme

@Composable
fun ShapesScreen(viewModel: LandingViewModel) {
    val state = viewModel.uiState.collectAsState().value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        when (state) {
            is Data -> LazyColumn {
                state.data.forEach { item { Text(it) } }
            }
            is Error -> Text("Error")
            Loading -> Text("Loading")
        }
    }

}

@Composable
fun DifferentShapes() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .wrapContentSize(Alignment.Center)
    ) {
        val roundedCornerShape = RoundedCornerShape(
            topStartPercent = 5,
            topEndPercent = 65,
            bottomStartPercent = 65,
            bottomEndPercent = 10
        )
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.15f)
                .clip(roundedCornerShape)
                .background(Color.Cyan)
                .border(2.dp, Color.Blue, roundedCornerShape)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MyPlaygroundTheme {
        DifferentShapes()
    }
}