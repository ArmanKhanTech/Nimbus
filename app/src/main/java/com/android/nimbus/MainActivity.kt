package com.android.nimbus

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.android.nimbus.ui.theme.NimbusTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            NimbusTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center),
                ) {
                    Greetings()
                }
            }
        }
    }
}

@Composable
fun Greetings(modifier: Modifier = Modifier) {
    Text(
        text = "Hello World!",
        modifier = modifier,
        fontSize = 24.sp,
        color = Color.Black
    )
}

@Preview(
    showBackground = true
)
@Composable
fun GreetingsPreview() {
    NimbusTheme {
        Greetings()
    }
}