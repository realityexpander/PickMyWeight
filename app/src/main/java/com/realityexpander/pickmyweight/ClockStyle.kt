package com.realityexpander.pickmyweight

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class ClockStyle(
    val radius: Dp = 130.dp,

    val hourHandColor: Color = Color.LightGray,
    val minuteHandColor: Color = Color.Green,
    val secondHandColor: Color = Color.Black,

    val hourHandLength: Dp = 75.dp,
    val minuteHandLength: Dp = 95.dp,
    val secondHandLength: Dp = 115.dp,

    val hourHandWidth: Dp = 8.dp,
    val minuteHandWidth: Dp = 4.dp,
    val secondHandWidth: Dp = 1.dp,

    val hourMarkerColor: Color = Color.Black,
    val minuteMarkerColor: Color = Color.Green,

    val hourMarkerLength: Dp = 15.dp,
    val minuteMarkerLength: Dp = 10.dp,

)

