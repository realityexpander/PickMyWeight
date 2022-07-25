package com.realityexpander.pickmyweight

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.roundToInt

@Composable
fun ImageModes(
    modifier: Modifier = Modifier,
) {

    val jellyfish = ImageBitmap.imageResource(id = R.drawable.jellyfish)

    Canvas(modifier = Modifier.fillMaxSize()) {
//        drawCircle(
//            color = Color.Blue,
//            radius = 200f,
//            center = Offset(300f, 300f),
//        )

        drawImage(
            image = jellyfish,
            dstOffset = IntOffset(100, 100),
            dstSize = IntSize(
                (400 * (jellyfish.width.toFloat() / jellyfish.height)).toInt(),
                400
            ),
            blendMode = BlendMode.Src
        )

        drawImage(
            image = jellyfish,
            dstOffset = IntOffset(135, 135),
            dstSize = IntSize(
                (400 * (jellyfish.width.toFloat() / jellyfish.height)).toInt(),
                400
            ),
            blendMode = BlendMode.Difference
        )

        drawImage(
            image = jellyfish,
            dstOffset = IntOffset(162, 162),
            dstSize = IntSize(
                (400 * (jellyfish.width.toFloat() / jellyfish.height)).toInt(),
                400
            ),
            blendMode = BlendMode.Exclusion
        )
    }
}

@Composable
fun RevealInteractive(
    modifier: Modifier = Modifier,
) {

    var circlePos by remember {
        mutableStateOf(Offset.Zero)
    }

    var oldCirclePos by remember {
        mutableStateOf(Offset.Zero)
    }
    val imageBmp = ImageBitmap.imageResource(id = R.drawable.jellyfish)
    val radius = 200f
    Canvas(modifier = Modifier
        .fillMaxSize()
        .pointerInput(true) {
            detectDragGestures(
                onDragStart = {
                    circlePos = it
                },
                onDragEnd = {
                    //oldCirclePos = circlePos
                }
            ) { change, dragAmount ->
                circlePos = oldCirclePos + change.position
            }
        }
    ) {
        val bmpHeight = (
                (imageBmp.height.toFloat() / imageBmp.width.toFloat()) *
                    this.size.width
                ).roundToInt()
        val circlePath = Path().apply {
            addArc(
                oval = Rect(circlePos, radius),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 360f
            )
        }

        drawImage(
            image = imageBmp,
            dstSize = IntSize(
                size.width.roundToInt(),
                bmpHeight
            ),
            dstOffset = IntOffset(0, center.y.roundToInt() - bmpHeight),
            colorFilter = ColorFilter.tint(Color.White, BlendMode.Difference)
        )

        clipPath(circlePath, clipOp = ClipOp.Intersect) {
            drawImage(
                image = imageBmp,
                dstSize = IntSize(
                    size.width.roundToInt(),
                    bmpHeight
                ),
                dstOffset = IntOffset(0, center.y.roundToInt() - bmpHeight),
            )
        }
    }

}
