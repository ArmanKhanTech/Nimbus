package com.android.nimbus

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                ) {
                    Greetings()
                }
            }
        }
    }
}

@SuppressLint("ResourceType")
@Composable
fun Greetings(modifier: Modifier = Modifier) {
    var dinnerAmount by remember { mutableStateOf("0") }
    var tipAmount by remember { mutableStateOf("0") }
    var totalAmount by remember { mutableStateOf("0") }

    Column(
        modifier = modifier
            .background(color = Color.White)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.dice_1),
            contentDescription = "Nimbus Logo"
        )
        TextField(
            value = dinnerAmount,
            onValueChange = {
                dinnerAmount = it
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        TextField(
            value = tipAmount,
            onValueChange = {
                tipAmount = it
            }
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                totalAmount = calculateTotal(dinnerAmount, tipAmount)
            }
        ) {
            Text(text = "Calculate Total")
        }
        Spacer(modifier = Modifier.height(50.dp))
        Text(
            text = "Total Amount Is: $totalAmount",
            fontSize = 30.sp,
            lineHeight = 50.sp
        )
    }
}

fun calculateTotal(dinnerAmount: String, tipAmount: String): String {
    var sum = 0
    sum += dinnerAmount.toInt()
    sum += tipAmount.toInt()
    return sum.toString()
}

@Preview
@Composable
fun GreetingsPreview() {
    NimbusTheme {
        Greetings()
    }
}