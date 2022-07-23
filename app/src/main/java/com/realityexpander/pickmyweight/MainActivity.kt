package com.realityexpander.pickmyweight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.realityexpander.pickmyweight.ui.theme.PickMyWeightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PickMyWeightTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    ScaleWidget(
                        style = ScaleStyle(
                            scaleWidth = 150.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center)
                    ) {

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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PickMyWeightTheme {
        Greeting("Android")
    }
}