package com.realityexpander.pickmyweight

import android.graphics.Color
import android.graphics.Color.argb
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.graphics.withRotation
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun ScaleWidget(
    modifier: Modifier = Modifier,
    style: ScaleStyle = ScaleStyle(),
    minWeight: Int = 20,
    maxWeight: Int = 250,
    initialWeight: Int = 80,
    onWeightChange: (Int) -> Unit,
) {
    val radius = style.radius
    val scaleWidth = style.scaleWidth
    var center by remember { mutableStateOf(Offset.Zero) } // center of the canvas
    var scaleCircleCenter by remember { mutableStateOf(Offset.Zero) } // center of the scale (off the bottom of the canvas)

    var weightAngle by remember { mutableStateOf(0f) }

    var dragStartAngle by remember { mutableStateOf(0f) }
    var dragStopAngle by remember { mutableStateOf(0f) }

    Canvas(
        modifier = modifier
            .pointerInput(true) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragStartAngle =
                            atan2(
                                scaleCircleCenter.y - offset.y,
                                scaleCircleCenter.x - offset.x,
                            ) * (180f / Math.PI.toFloat())
                    },
                    onDragEnd = {
                        dragStopAngle = weightAngle
                    }
                ) { change, dragAmount ->
                    val touchAngle =
                        atan2(
                        scaleCircleCenter.y - change.position.y,
                        scaleCircleCenter.x - change.position.x,
                    ) * (180f / Math.PI.toFloat())

                    val newAngle = dragStopAngle + (touchAngle - dragStartAngle)
                    weightAngle = newAngle.coerceIn(
                        initialWeight - maxWeight.toFloat(),
                        initialWeight - minWeight.toFloat()
                    )
                    onWeightChange((initialWeight - weightAngle).roundToInt())
                }
            }
    ) {
        center = this.center
        scaleCircleCenter = Offset(center.x, scaleWidth.toPx() / 2f + radius.toPx())
        val outerScaleRadius = radius.toPx() + scaleWidth.toPx() / 2f // outer edge of the scale
        val innerScaleRadius = radius.toPx() - scaleWidth.toPx() / 2f // inner edge of the scale

        // Compose doesn't have shadows yet, so we must use the native canvas to draw the shadow
        drawContext.canvas.nativeCanvas.apply {
            drawCircle(
                scaleCircleCenter.x,
                scaleCircleCenter.y,
                radius.toPx(),
                Paint().apply {
                    strokeWidth = scaleWidth.toPx()
                    color = Color.WHITE
                    setStyle(Paint.Style.STROKE)
                    setShadowLayer(
                        60f,
                        0f,
                        0f,
                        argb(50, 0, 0, 0)
                    )
                }

            )
        }

        // Draw weight lines
        for (i in minWeight..maxWeight) {
            val angleInDegrees = (i - initialWeight + weightAngle - 90)
            val angleInRadians = angleInDegrees * (Math.PI / 180f).toFloat()

            val lineType = when {
                i % 10 == 0 -> LineType.TenStep
                i % 5 == 0 -> LineType.FiveStep
                else -> LineType.Normal
            }
            val lineLength = when (lineType) {
                LineType.TenStep -> style.tenStepLineLength.toPx()
                LineType.FiveStep -> style.fiveStepLineLength.toPx()
                else -> style.normalLineLength.toPx()
            }
            val lineColor = when (lineType) {
                LineType.TenStep -> style.tenStepLineColor
                LineType.FiveStep -> style.fiveStepLineColor
                else -> style.normalLineColor
            }

            val lineStart = Offset(
                (outerScaleRadius - lineLength) * cos(angleInRadians) + scaleCircleCenter.x,
                (outerScaleRadius - lineLength) * sin(angleInRadians) + scaleCircleCenter.y,
            )
            val lineEnd = Offset(
                outerScaleRadius * cos(angleInRadians) + scaleCircleCenter.x,
                outerScaleRadius * sin(angleInRadians) + scaleCircleCenter.y,
            )

            drawLine(
                color = lineColor,
                start = lineStart,
                end = lineEnd,
                strokeWidth = 1.dp.toPx()
            )

            // Draw weight text
            if (lineType == LineType.TenStep) {
                drawContext.canvas.nativeCanvas.apply {
                    val textRadius =
                        (outerScaleRadius - lineLength - 5.dp.toPx() - style.textSize.toPx())
                    val x = textRadius * cos(angleInRadians) + scaleCircleCenter.x
                    val y = textRadius * sin(angleInRadians) + scaleCircleCenter.y

                    withRotation(
                        degrees = angleInDegrees + 90f,
                        pivotX = x,
                        pivotY = y
                    ) {
                        drawText(
                            i.toString(),
                            x,
                            y,
                            Paint().apply {
                                color = argb((lineColor.alpha * 255).toInt(),
                                    (lineColor.red * 255).toInt(),
                                    (lineColor.green * 255).toInt(),
                                    (lineColor.blue * 255).toInt())
                                textSize = style.textSize.toPx()
                                textAlign = Paint.Align.CENTER
                            }
                        )
                    }

                }
            }

            // Weight indicator
            val middleTop = Offset(
                x = scaleCircleCenter.x,
                y = scaleCircleCenter.y - innerScaleRadius - style.scaleIndicatorLength.toPx()
            )
            val bottomLeft = Offset(
                x = scaleCircleCenter.x - style.scaleIndicatorLength.toPx() / 4f,
                y = scaleCircleCenter.y - innerScaleRadius
            )
            val bottomRight = Offset(
                x = scaleCircleCenter.x + style.scaleIndicatorLength.toPx() / 4f,
                y = scaleCircleCenter.y - innerScaleRadius
            )
            val indicator = Path().apply {
                moveTo(middleTop.x, middleTop.y)
                lineTo(bottomLeft.x, bottomLeft.y)
                lineTo(bottomRight.x, bottomRight.y)
                close()
            }
            drawPath(
                path = indicator,
                color = style.scaleIndicatorColor,
            )

        }


    }
}

























