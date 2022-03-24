package com.tavoeh.secondfeature.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import com.tavoeh.secondfeature.R

@Composable
fun Counter(
    count: Int,
    increment: () -> Unit = {},
    decrement: () -> Unit = {}
) {
    Column {
        Text(
            text = count.toString(),
            Modifier.testTag("Counter Value")
        )
        Button(
            onClick = increment,
            modifier = Modifier.clearAndSetSemantics { contentDescription = "Increment" }
        ) {
            Text(stringResource(R.string.increment))
        }
        Button(
            onClick = decrement,
            modifier = Modifier.clearAndSetSemantics { contentDescription = "Decrement" }
        ) {
            Text(stringResource(R.string.decrement))
        }
    }
}
