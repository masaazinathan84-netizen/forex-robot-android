package com.forexrobot.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

data class Trade(
    val pair: String,
    val side: String,
    val price: String,
    val pnl: String
)

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ForexRobotApp()
        }
    }
}

@Composable
fun ForexRobotApp() {

    var running by remember {
        mutableStateOf(false)
    }

    var risk by remember {
        mutableFloatStateOf(1f)
    }

    var balance by remember {
        mutableStateOf(10000.0)
    }

    val trades = remember {

        mutableStateListOf(

            Trade(
                "EUR/USD",
                "BUY",
                "1.0872",
                "+12.40"
            ),

            Trade(
                "GBP/USD",
                "SELL",
                "1.2741",
                "-4.80"
            )
        )
    }

    MaterialTheme {

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {

            LazyColumn(

                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)

            ) {

                item {

                    Text(
                        "Forex Robot",
                        style =
                            MaterialTheme.typography.headlineMedium
                    )

                    Text(
                        "Automated forex trading • Demo mode"
                    )
                }

                item {

                    Card(
                        Modifier.fillMaxWidth()
                    ) {

                        Column(
                            Modifier.padding(16.dp)
                        ) {

                            Text(
                                "Account",
                                style =
                                    MaterialTheme.typography.titleLarge
                            )

                            Spacer(
                                Modifier.height(8.dp)
                            )

                            Text(
                                "Balance: " +
                                    "%.2f".format(balance)
                            )

                            Text(
                                "Equity: " +
                                    "%.2f".format(
                                        balance + 7.60
                                    )
                            )

                            Text(
                                "Mode: PAPER / DEMO"
                            )
                        }
                    }
                }

                item {

                    Card(
                        Modifier.fillMaxWidth()
                    ) {

                        Column(
                            Modifier.padding(16.dp)
                        ) {

                            Row(

                                Modifier.fillMaxWidth(),

                                horizontalArrangement =
                                    Arrangement.SpaceBetween

                            ) {

                                Column {

                                    Text(
                                        "Robot",
                                        style =
                                            MaterialTheme
                                                .typography
                                                .titleLarge
                                    )

                                    Text(
                                        if (running)
                                            "Running"
                                        else
                                            "Stopped"
                                    )
                                }

                                Switch(

                                    checked = running,

                                    onCheckedChange = {
                                        running = it
                                    }
                                )
                            }

                            Spacer(
                                Modifier.height(8.dp)
                            )

                            Text(
                                "Strategy: EMA crossover"
                            )

                            Text(
                                "Risk per trade: " +
                                    "%.1f".format(risk) +
                                    "%"
                            )

                            Spacer(
                                Modifier.height(8.dp)
                            )

                            Button(

                                onClick = {

                                    risk =
                                        if (risk >= 3f)
                                            0.5f
                                        else
                                            risk + 0.5f
                                },

                                modifier =
                                    Modifier.fillMaxWidth()

                            ) {

                                Text(
                                    "Adjust risk"
                                )
                            }

                            Spacer(
                                Modifier.height(4.dp)
                            )

                            OutlinedButton(

                                onClick = {
                                    running = false
                                },

                                modifier =
                                    Modifier.fillMaxWidth()

                            ) {

                                Text(
                                    "EMERGENCY STOP"
                                )
                            }
                        }
                    }
                }

                item {

                    Text(
                        "Recent trades",
                        style =
                            MaterialTheme
                                .typography
                                .titleLarge
                    )
                }

                items(trades) { trade ->

                    Card(
                        Modifier.fillMaxWidth()
                    ) {

                        Row(

                            Modifier
                                .fillMaxWidth()
                                .padding(14.dp),

                            horizontalArrangement =
                                Arrangement.SpaceBetween

                        ) {

                            Column {

                                Text(
                                    trade.pair +
                                        " • " +
                                        trade.side
                                )

                                Text(
                                    trade.price
                                )
                            }

                            Text(
                                trade.pnl
                            )
                        }
                    }
                }

                item {

                    OutlinedButton(

                        onClick = {

                            trades.add(
                                0,
                                Trade(
                                    "USD/JPY",
                                    "BUY",
                                    "149.20",
                                    "+3.10"
                                )
                            )

                            balance += 3.10
                        },

                        modifier =
                            Modifier.fillMaxWidth()

                    ) {

                        Text(
                            "Simulate demo trade"
                        )
                    }
                }
            }
        }
    }
}
