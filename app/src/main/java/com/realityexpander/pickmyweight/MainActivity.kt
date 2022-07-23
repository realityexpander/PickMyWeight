package com.realityexpander.pickmyweight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.realityexpander.pickmyweight.ui.theme.PickMyWeightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var weight by remember { mutableStateOf(80) }

            PickMyWeightTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    ClockWidget(
                        hour = 6,
                        minute = 34,
                        second = 22,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .align(Alignment.TopCenter)
                    )

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

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PickMyWeightTheme {
        Greeting("Android")
    }
}