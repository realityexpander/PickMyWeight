package com.realityexpander.pickmyweight

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import android.graphics.Paint as AndroidPaint
import android.graphics.Path as AndroidPath
import android.graphics.Color as AndroidColor

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

    // Use the native path measure... (why isn't this in compose?)
    android.graphics.PathMeasure().apply {
        // convert compose path to android path
        setPath(path.asAndroidPath(), false)

        getSegment(0f, pathPortion.value * length, outPath, true)

        // Get position and tangent of the last point
        getPosTan(pathPortion.value * length, pos, tan)
    }

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawPath(
            path = outPath.asComposePath(),  // convert android path back to compose path
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
                    moveTo(x, y - 40f)
                    lineTo(x - 40f, y + 40f)
                    lineTo(x + 40f, y + 40f)
                    close()
                },
                color = Color.Red
            )
        }
    }
}

@Composable
fun AnimateTransform(
    modifier: Modifier = Modifier,
) {
    val animVal = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(key1 = true) {
        animVal.animateTo(
            targetValue = 3f,
            animationSpec = tween(
                durationMillis = 3500
            )
        )
    }


    // Rotate
    if (false) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            rotate(360f * animVal.value, pivot = Offset(200f, 200f)) {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(100f, 100f),
                    size = Size(300f, 200f)
                )
            }
        }
    }

    // Translate
    if (false) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 200 * animVal.value, top = animVal.value * 200f) {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(100f, 100f),
                    size = Size(300f, 200f)
                )
            }
        }
    }

    // Translate & rotate
    if (false) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            translate(left = 200 * animVal.value, top = animVal.value * 200f) {
                rotate(360f * animVal.value, pivot = Offset(200f, 200f)) {
                    scale(4f * animVal.value, pivot = Offset(200f, 200f)) {
                        drawRect(
                            color = Color.Red,
                            topLeft = Offset(100f, 100f),
                            size = Size(300f, 200f)
                        )
                    }
                }
            }
        }
    }

    // Clipping
    if (true) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val circle = Path().apply {
                addOval(Rect(center = Offset(animVal.value * 250f, animVal.value * 250f), radius = 250f))
            }

            clipPath(
                path = circle
            ) {
                drawRect(
                    color = Color.Red,
                    topLeft = Offset(400f, 400f),
                    size = Size(400f, 400f),
                    style = Stroke(width = 15.dp.toPx())
                )
            }

            drawPath(
                path = circle,
                color = Color.Black,
                style = Stroke(width = 15.dp.toPx())
            )
        }
    }
}

@Composable
fun PathEffect(
    modifier: Modifier = Modifier,
) {

    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 10000f,
        animationSpec = infiniteRepeatable(
            animation = tween(60000, easing = LinearEasing)
        )
    )
    Canvas(modifier = Modifier.fillMaxSize()) {

        val oval1 = Path().apply {
            addOval(
                Rect(
                    topLeft = Offset(
                        300f, 200f
                    ),
                    bottomRight = Offset(800f, 500f)
                )
            )
        }
        val path2 = Path().apply {
            moveTo(-3000f, -2100f)
            cubicTo(
                800f, -400f,
                -1200f, 1200f,
                size.width + 3100f, 6000f)
        }

        // Creates a bend in the last lineTo paths with the cornerPathEffect
        val path3 = Path().apply {
            moveTo(100f,100f)
            cubicTo(100f, 300f, 600f, 700f, 600f, 1100f)
            lineTo(800f, 800f)
            lineTo(1000f, 1100f)
        }
        drawPath(
            path = path3,
            color = Color.Blue,
            style = Stroke(
                width = 5.dp.toPx(),
                pathEffect = PathEffect.cornerPathEffect(
                    radius = 1000f
                )
            )
        )

        val star = PathParser()
            .parsePathString("M570.5,252.5l93.8,190c1.5,3.1 4.5,5.3 8,5.8l209.7,30.5c8.7,1.3 12.2,11.9 5.9,18.1L736.1,644.8c-2.5,2.4 -3.6,5.9 -3,9.4L768.8,863c1.5,8.7 -7.6,15.2 -15.4,11.2l-187.5,-98.6c-3.1,-1.6 -6.8,-1.6 -9.9,0l-187.5,98.6c-7.8,4.1 -16.9,-2.5 -15.4,-11.2L389,654.1c0.6,-3.4 -0.5,-6.9 -3,-9.4L234.2,496.9c-6.3,-6.1 -2.8,-16.8 5.9,-18.1l209.7,-30.5c3.4,-0.5 6.4,-2.7 8,-5.8l93.8,-190C555.4,244.7 566.6,244.7 570.5,252.5z")
            .toPath()

        // ChainPathEffect
        drawPath(
            path = oval1,
            color = Color.Green,
            style = Stroke(
                width = 5.dp.toPx(),
                pathEffect = PathEffect.chainPathEffect(
                    outer = PathEffect.stampedPathEffect(
                        shape = path3,
                        advance = 30f,
                        phase = 0f,
                        style = StampedPathEffectStyle.Rotate
                    ),
                    inner = PathEffect.dashPathEffect(
                        intervals = floatArrayOf(200f, 200f),
                        phase = -phase
                    )
                )
            )
        )

        drawPath(
            path = oval1,
            color = Color.Red,
            style = Stroke(
                width = 5.dp.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(40f, 40f),
                    phase = phase
                )
            )
        )
        scale(scale = 0.2f) {
            drawPath(
                path = path2,
                color = Color.Red,
                style = Stroke(
                    width = 5.dp.toPx(),
                    pathEffect = PathEffect.stampedPathEffect(
                        shape = star,
                        advance = 1000f,
                        phase = -phase * 10f,
                        style = StampedPathEffectStyle.Rotate
                    )
                )
            )
        }
    }
}

@Composable
fun PathText(
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition()
    val phase by infiniteTransition.animateFloat(
        initialValue = -1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = LinearEasing)
        )
    )


    Canvas(modifier = Modifier.fillMaxSize()) {
        val path = AndroidPath().apply {
            moveTo(0f, 800f)
            quadTo(
                size.width / 2f, phase * 300f,
                size.width, 800f
            )
        }

        drawContext.canvas.nativeCanvas.apply {
            drawTextOnPath(
                "Hello World 2!",
                path,
                0f,
                phase * -60f,
                AndroidPaint().apply {
                    color = AndroidColor.RED
                    textSize = 130f
                    textAlign = AndroidPaint.Align.CENTER
                }
            )
        }

        drawPath(
            path = path.asComposePath(),
            color = androidx.compose.ui.graphics.Color.Black,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round,
                pathEffect = PathEffect.dashPathEffect(
                    intervals = floatArrayOf(50f, 30f)
                )
            )
        )
    }

}


































