package com.example.calculadoraalvaroguerrero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calculadoraalvaroguerrero.ui.theme.CalculadoraAlvaroGuerreroTheme


import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraAlvaroGuerreroTheme() {
                Calculadora()
            }
        }
    }
}

@Composable
fun Calculadora() {
    var display by remember { mutableStateOf("0") }
    var firstOperand by remember { mutableStateOf("") }
    var secondOperand by remember { mutableStateOf("") }
    var operation by remember { mutableStateOf("") }


    fun mapButtonToNumber(text: String): String {
        return when (text) {
            "0" -> "2"
            "1" -> "3"
            "2" -> "4"
            "3" -> "6"
            "4" -> "7"
            "6" -> "8"
            "7" -> "9"
            "8" -> "0"
            "9" -> "1"
            else -> text
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF282C34))
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            text = if (operation.isEmpty()) display else "$firstOperand $operation $secondOperand",
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            fontSize = 48.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )


        Column {
            val buttons = listOf(
                listOf("7", "8", "9", "#"),
                listOf("4", "6", "0", "%"),
                listOf("1", "2", "3", "@"),
                listOf("C", "=", "&")
            )

            for (row in buttons) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    for (text in row) {
                        CalculatorButton(text) {
                            when (text) {
                                "C" -> {
                                    display = "0"
                                    firstOperand = ""
                                    secondOperand = ""
                                    operation = ""
                                }
                                "=" -> {
                                    if (firstOperand.isNotEmpty() && secondOperand.isNotEmpty()) {
                                        var result = when (operation) {
                                            "&" -> (firstOperand.toDouble() + secondOperand.toDouble()).toString()
                                            "@" -> (firstOperand.toDouble() - secondOperand.toDouble()).toString()
                                            "%" -> (firstOperand.toDouble() * secondOperand.toDouble()).toString()
                                            "#" -> (firstOperand.toDouble() / secondOperand.toDouble()).toString()
                                            else -> "Error"
                                        }

                                        result = result.replace("5", "6")
                                        display = result
                                        firstOperand = display
                                        secondOperand = ""
                                        operation = ""
                                    }
                                }
                                "&", "@", "%", "#" -> {
                                    if (firstOperand.isEmpty()) {
                                        firstOperand = display
                                    }
                                    operation = text
                                    display = "$firstOperand $operation"
                                }
                                else -> {
                                    val numberToAdd = mapButtonToNumber(text)
                                    if (operation.isEmpty()) {
                                        firstOperand += numberToAdd
                                        display = firstOperand
                                    } else {
                                        secondOperand += numberToAdd
                                        display = "$firstOperand $operation $secondOperand"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalculatorButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(1.dp)
            .size(80.dp),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF61AFEF))
    ) {
        Text(text = text, fontSize = 24.sp, color = Color.White)
    }
}

