package com.tavoeh.secondfeature.presentation.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.BoxWithConstraintsScope
import androidx.compose.foundation.text.InternalFoundationTextApi
import androidx.compose.foundation.text.TextDelegate
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFontLoader
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.TextUnit

@Composable
fun AutoSizeText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    minLines: Int = Int.MAX_VALUE,
    autoSizeMaxTextSize: TextUnit,
    autoSizeMinTextSize: TextUnit,
    style: TextStyle = LocalTextStyle.current,
    onTextSizeChange: (textSize: String) -> Unit
) {
    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.TopStart,
    ) {

        var combinedTextStyle = (LocalTextStyle.current + style).copy(
            fontSize = autoSizeMaxTextSize
        )
        var lines = minLines
        var fontSize = combinedTextStyle.fontSize

        val annotatedString = AnnotatedString(text).toUpperCase()
        val originalFontSize = combinedTextStyle.fontSize
        onTextSizeChange(originalFontSize.toString())

        while (shouldResize(annotatedString, combinedTextStyle, lines)) {
            fontSize *= 0.9

            if (combinedTextStyle.fontSize <= autoSizeMinTextSize) {
                lines++
                fontSize = originalFontSize
            }

            combinedTextStyle = combinedTextStyle.copy(fontSize = fontSize)
            onTextSizeChange(fontSize.toString())
        }

        Text(
            text = text,
            modifier = Modifier,
            color = color,
            fontSize = TextUnit.Unspecified,
            fontStyle = fontStyle,
            fontWeight = fontWeight,
            fontFamily = fontFamily,
            style = combinedTextStyle,
        )
    }
}

@OptIn(InternalFoundationTextApi::class)
@Composable
private fun BoxWithConstraintsScope.shouldResize(
    text: AnnotatedString,
    textStyle: TextStyle,
    maxLines: Int
): Boolean {
    val textDelegate = TextDelegate(
        text,
        textStyle,
        maxLines,
        true,
        TextOverflow.Clip,
        LocalDensity.current,
        LocalFontLoader.current,
    )

    val textLayoutResult = textDelegate.layout(
        constraints,
        LocalLayoutDirection.current,
    )

    return textLayoutResult.hasVisualOverflow
}
