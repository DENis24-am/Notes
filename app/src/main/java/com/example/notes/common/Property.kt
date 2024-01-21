package com.example.notes.common

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalProperty = compositionLocalOf { Dimensions() }

data class Dimensions(
    val smallPadding: Dp = 8.dp,
    val topPadding: Dp = 10.dp,
    val normalPadding: Dp = 16.dp,
    val iconSize: Dp = 30.dp,
    val spacerSizeSmall: Dp = 3.dp,
    val spacerSizeLarge: Dp = 24.dp,
    val maxLines: Int = 9
)