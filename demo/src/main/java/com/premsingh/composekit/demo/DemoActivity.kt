package com.premsingh.composekit.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.premsingh.composekit.AmountField
import com.premsingh.composekit.AmountInputPolicy
import com.premsingh.composekit.BalanceSummaryCard
import com.premsingh.composekit.ComposeKitColors
import com.premsingh.composekit.ComposeKitTheme
import com.premsingh.composekit.PrimaryActionButton
import com.premsingh.composekit.TransactionSummaryRow

class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ComposeKitShowcase() }
    }
}

@Composable
private fun ComposeKitShowcase() {
    var amount by rememberSaveable { mutableStateOf("") }
    var status by rememberSaveable { mutableStateOf("Components use sample-only values.") }
    ComposeKitTheme {
        Surface(color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()) {
            LazyColumn(contentPadding = PaddingValues(20.dp), verticalArrangement = Arrangement.spacedBy(18.dp)) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text("ComposeKit", color = ComposeKitColors.Navy, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                        Text("Reusable finance UI · sample showcase", color = ComposeKitColors.Muted)
                    }
                }
                item { BalanceSummaryCard("Everyday account", "\$12,480.50", "••••  4829 · fictional demo") }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("Amount entry", fontWeight = FontWeight.SemiBold)
                        AmountField(value = amount, onValueChange = { amount = it }, currencyCode = "USD")
                        PrimaryActionButton(text = "Continue", onClick = { status = if (AmountInputPolicy.isValid(amount)) "Amount is ready for host-app review." else "Enter a valid amount first." })
                        Text(status, color = ComposeKitColors.Muted, style = MaterialTheme.typography.bodySmall)
                    }
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text("Recent activity", fontWeight = FontWeight.SemiBold)
                        TransactionSummaryRow("Northstar Coffee", "Today · Food & drink", "\$5.80", isCredit = false)
                        TransactionSummaryRow("Salary deposit", "Oct 14 · Income", "\$3,200.00", isCredit = true)
                    }
                }
                item { Text("No real accounts, payments, or customer data are connected.", color = ComposeKitColors.Muted, style = MaterialTheme.typography.bodySmall) }
            }
        }
    }
}
