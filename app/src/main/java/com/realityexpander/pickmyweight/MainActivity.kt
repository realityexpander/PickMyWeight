package com.realityexpander.pickmyweight

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.realityexpander.pickmyweight.ClockWidget.ClockStyle
import com.realityexpander.pickmyweight.ClockWidget.ClockWidget
import com.realityexpander.pickmyweight.ScaleWidget.ScaleStyle
import com.realityexpander.pickmyweight.ScaleWidget.ScaleWidget
import com.realityexpander.pickmyweight.ui.theme.PickMyWeightTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var weight by remember { mutableStateOf(80) }


            PickMyWeightTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    if(false) {
                        PathCompose()
                    }

                    if(false) {
                        PathOps()
                    }

                    if(false) {
                        PathAnimate()
                    }

                    if(true) {
                        PathAnimateArrowhead()
                    }

                    if(false) {
                        ClockWidget(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .align(Alignment.TopCenter)
                        )
                    }


                    if(true) {
                        ScaleWidget(
                            style = ScaleStyle(
                                scaleWidth = 150.dp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(300.dp)
                                .align(Alignment.BottomCenter)
                        ) { weightResult ->
                            weight = weightResult
                        }

                        Text(
                            text = "Weight: $weight",
                            style = TextStyle(
                                textAlign = TextAlign.Center,
                                fontSize = 40.sp
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PickMyWeightTheme {
        ClockWidget(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .width(100.dp),
            style = ClockStyle(
                radius = 50.dp,
                hourHandLength = 25.dp,
                minuteHandLength = 25.dp,
                secondHandLength = 30.dp,
            )
        )
    }
}