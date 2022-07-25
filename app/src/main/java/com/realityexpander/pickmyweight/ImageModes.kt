package com.realityexpander.pickmyweight

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

@Composable
fun ImageModes(
    modifier: Modifier = Modifier,
) {

    val kermit = ImageBitmap.imageResource(id = R.drawable.jellyfish)
    Canvas(modifier = Modifier.fillMaxSize()) {
//        drawCircle(
//            color = Color.Blue,
//            radius = 200f,
//            center = Offset(300f, 300f),
//        )

        drawImage(
            image = kermit,
            dstOffset = IntOffset(100, 100),
            dstSize = IntSize(
                (400 * (kermit.width.toFloat() / kermit.height)).toInt(),
                400
            ),
            blendMode = BlendMode.Src
        )

        drawImage(
            image = kermit,
            dstOffset = IntOffset(135, 135),
            dstSize = IntSize(
                (400 * (kermit.width.toFloat() / kermit.height)).toInt(),
                400
            ),
            blendMode = BlendMode.Difference
        )

        drawImage(
            image = kermit,
            dstOffset = IntOffset(162, 162),
            dstSize = IntSize(
                (400 * (kermit.width.toFloat() / kermit.height)).toInt(),
                400
            ),
            blendMode = BlendMode.Exclusion
        )
    }
}