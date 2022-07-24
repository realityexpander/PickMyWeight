package com.realityexpander.pickmyweight

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2

@Composable
fun PathCompose(
    modifier: Modifier = Modifier,
) {
    Canvas(
        modifier = Modifier.fillMaxSize()
    ) {
        val path = Path().apply {
            moveTo(2000f, 100f)
            lineTo(100f, 500f)
            lineTo(500f, 500f)
//                    quadraticBezierTo(800f, 300f, 500f, 100f)
            cubicTo(800f, 500f, 800f, 100f, 500f, 100f)
        }
        drawPath(
            path = path,
            color = Color.Red,
            style = Stroke(
                width = 10.dp.toPx(),
                cap = StrokeCap.Round,
                join = StrokeJoin.Miter,
                miter = 0f
            )

        )
    }
}

@Composable
fun PathOps(
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val squareWithoutOp = Path().apply {
            addRect(Rect(Offset(200f, 200f), Size(200f, 200f)))
        }
        val circle = Path().apply {
            addOval(Rect(Offset(200f, 200f), 100f))
        }
        val pathWithOp = Path().apply {
            op(squareWithoutOp, circle, PathOperation.Xor)
        }
        drawPath(
            path = squareWithoutOp,
            color = Color.Red,
            style = Stroke(width = 2.dp.toPx())
        )
        drawPath(
            path = circle,
            color = Color.Blue,
            style = Stroke(width = 2.dp.toPx())
        )
        drawPath(
            path = pathWithOp,
            color = Color.Green,
        )
    }
}

@Composable
fun PathAnimate(
    modifier: Modifier = Modifier,
) {

    val pathPortion = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = true) {
        pathPortion.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2500
            )
        )
    }


    val path = Path().apply {
        moveTo(100f, 100f)
        quadraticBezierTo(400f, 400f, 100f, 400f)
    }
    val outPath = Path()
    PathMeasure().apply {
        setPath(path, false)
        getSegment(0f, pathPortion.value * length, outPath, true)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPath(
            path = outPath,
            color = Color.Red,
            style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
        )
    }

}


@Composable
fun PathAnimateArrowhead(
    modifier: Modifier = Modifier,
) {

    val pathPortion = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = true) {
        pathPortion.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 2500
            )
        )
    }

    val path = Path().apply {
        moveTo(100f, 100f)
        quadraticBezierTo(400f, 400f, 100f, 400f)
    }
    val outPath = android.graphics.Path()
    val pos = FloatArray(2)
    val tan = FloatArray(2)

    android.graphics.PathMeasure().apply {
        setPath(path.asAndroidPath(), false)
        getSegment(0f, pathPortion.value * length, outPath, true)
        getPosTan(pathPortion.value * length, pos, tan)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPath(
            path = outPath.asComposePath(),
            color = Color.Blue,
            style = Stroke(width = 5.dp.toPx(), cap = StrokeCap.Round)
        )

        val x = pos[0]
        val y = pos[1]
        val degrees = atan2(tan[1], tan[0]) * (180f / PI.toFloat()) + 90f

        rotate(degrees = degrees, pivot = Offset(x, y)) {

            // Draw arrowhead
            drawPath(
                path = Path().apply {
                    moveTo(x, y - 50f)
                    lineTo(x - 40f, y + 60f)
                    lineTo(x + 40f, y + 60f)
                    close()
                },
                color = Color.Red
            )
        }
    }
}