package com.tavoeh.secondfeature.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tavoeh.secondfeature.R
import com.tavoeh.secondfeature.presentation.components.Counter
import com.tavoeh.secondfeature.presentation.ui.theme.MyPlaygroundTheme


@Composable
fun App() {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Compose") }) },
        content = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                var count by remember { mutableStateOf(0) }
                Counter(
                    count = count,
                    increment = { count++ },
                    decrement = { count-- }
                )
            }
        }
    )
}


@Preview(showBackground = false)
@Composable
fun DefaultPreview() {
    MyPlaygroundTheme {
        App()
    }
}