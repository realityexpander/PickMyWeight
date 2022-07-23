package com.realityexpander.pickmyweight.ClockWidget

import android.graphics.Color
import android.graphics.Paint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.time.LocalTime
import kotlin.math.cos
import kotlin.math.sin

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ClockWidget(
    modifier: Modifier = Modifier,
    style: ClockStyle = ClockStyle(),
) {
    val radius = style.radius
    var center by remember { mutableStateOf(Offset.Zero) } // center of the canvas
    var hour by remember { mutableStateOf(LocalTime.now().hour) }
    var minute by remember { mutableStateOf(LocalTime.now().minute) }
    var second by remember { mutableStateOf(LocalTime.now().second) }
    var update by remember { mutableStateOf(0) }

    LaunchedEffect(update) {
        delay(900L)

        hour = LocalTime.now().hour
        minute = LocalTime.now().minute
        second = LocalTime.now().second
        update++
    }

    Canvas(
        modifier = modifier
    ) {
        center = this.center

        val outerRadius = radius.toPx()

        // Compose doesn't have shadows yet, so we must use the native canvas to draw the shadow
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                center.x,
                center.y,
                radius.toPx(),
                Paint().apply {
                    color = Color.WHITE
                    setShadowLayer(
                        50f,
                        0f,
                        0f,
                        Color.argb(50, 0, 0, 0)
                    )
                }

            )
        }

        val innerMinuteRadius = radius.toPx() - style.minuteMarkerLength.toPx()
        val innerHourRadius = radius.toPx() - style.hourMarkerLength.toPx()

        // draw the minute markers
        for (i in 0..59) {
            val angleInDegrees = i * 6f
            val angleInRadians = angleInDegrees * (Math.PI / 180f).toFloat()

            val x = center.x + (outerRadius * cos(angleInRadians))
            val y = center.y + (outerRadius * sin(angleInRadians))

            if (i % 5 == 0) {
                // hour marker
                val x2 = center.x + (innerHourRadius * cos(angleInRadians))
                val y2 = center.y + (innerHourRadius * sin(angleInRadians))

                drawLine(
                    color = style.hourMarkerColor,
                    start = Offset(x,y),
                    end = Offset(x2,y2),
                    strokeWidth = 1.dp.toPx()
                )
            } else {
                // minute marker
                val x2 = center.x + (innerMinuteRadius * cos(angleInRadians))
                val y2 = center.y + (innerMinuteRadius * sin(angleInRadians))

                drawLine(
                    color = style.minuteMarkerColor,
                    start = Offset(x,y),
                    end = Offset(x2,y2),
                    strokeWidth = 1.dp.toPx()
                )
            }
        }

        // draw the hour hand
        val hourAngle = (hour * 30f) + (minute * 0.5f) - 90f
        val hourAngleInRadians = hourAngle * (Math.PI / 180f).toFloat()
        val hourX = center.x + (style.hourHandLength.toPx() * cos(hourAngleInRadians))
        val hourY = center.y + (style.hourHandLength.toPx() * sin(hourAngleInRadians))
        drawLine(
            color = style.hourHandColor,
            start = Offset(center.x, center.y),
            end = Offset(hourX, hourY),
            strokeWidth = style.hourHandWidth.toPx(),
            cap = StrokeCap.Round
        )

        // draw the minute hand
        val minuteAngle = (minute * 6) + (second * 0.1f) - 90f
        val minuteAngleInRadians = minuteAngle * (Math.PI / 180f).toFloat()
        val minuteX = center.x + (style.minuteHandLength.toPx() * cos(minuteAngleInRadians))
        val minuteY = center.y + (style.minuteHandLength.toPx() * sin(minuteAngleInRadians))
        drawLine(
            color = style.minuteHandColor,
            start = Offset(center.x, center.y),
            end = Offset(minuteX, minuteY),
            strokeWidth = style.minuteHandWidth.toPx(),
            cap = StrokeCap.Round
        )

        // draw the minute hand
        val secondAngle = (second * 6) - 90f
        val secondAngleInRadians = secondAngle * (Math.PI / 180f).toFloat()
        val secondX = center.x + (style.secondHandLength.toPx() * cos(secondAngleInRadians))
        val secondY = center.y + (style.secondHandLength.toPx() * sin(secondAngleInRadians))
        drawLine(
            color = style.secondHandColor,
            start = Offset(center.x, center.y),
            end = Offset(secondX, secondY),
            strokeWidth = style.secondHandWidth.toPx(),
            cap = StrokeCap.Round
        )
    }
}