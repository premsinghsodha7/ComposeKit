package com.premsingh.composekit.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
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
import com.premsingh.composekit.BudgetProgressCard
import com.premsingh.composekit.FeeBreakdownCard
import com.premsingh.composekit.FinancialAccountRow
import com.premsingh.composekit.PaymentCardPreview
import com.premsingh.composekit.ComposeKitPalette
import com.premsingh.composekit.ComposeKitTheme
import com.premsingh.composekit.PrimaryActionButton
import com.premsingh.composekit.TransactionCategoryChip
import com.premsingh.composekit.TransactionSummaryRow
import com.premsingh.composekit.SpendingCategoryRow
import com.premsingh.composekit.TransferStatusCard

class DemoActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ComposeKitShowcase() }
    }
}

private enum class ShowcaseTab(val label: String, val marker: String) { Overview("Home", "⌂"), Components("Kit", "◈"), Theme("Theme", "◐") }

@Composable
private fun ComposeKitShowcase() {
    var amount by rememberSaveable { mutableStateOf("") }
    var status by rememberSaveable { mutableStateOf("Components use sample-only values.") }
    var selectedTab by rememberSaveable { mutableStateOf(ShowcaseTab.Overview) }
    var paletteName by rememberSaveable { mutableStateOf(ComposeKitPalette.Ocean.name) }
    val palette = ComposeKitPalette.valueOf(paletteName)
    ComposeKitTheme(palette) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            bottomBar = {
                NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
                    ShowcaseTab.entries.forEach { tab ->
                        NavigationBarItem(selected = selectedTab == tab, onClick = { selectedTab = tab }, icon = { Text(tab.marker) }, label = { Text(tab.label) })
                    }
                }
            }
        ) { scaffoldPadding ->
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(scaffoldPadding),
                contentPadding = PaddingValues(
                    start = 20.dp,
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() + 18.dp,
                    end = 20.dp,
                    bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding() + 20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                when (selectedTab) {
                    ShowcaseTab.Overview -> {
                        item { Header("ComposeKit", "A reusable finance UI system") }
                        item { BalanceSummaryCard("Everyday account", "\$12,480.50", "••••  4829 · fictional demo") }
                        item { BudgetProgressCard("Monthly essentials", "\$840", "\$1,200", .70f) }
                        item {
                            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                Text("Try an amount", fontWeight = FontWeight.SemiBold)
                                AmountField(value = amount, onValueChange = { amount = it }, currencyCode = "USD")
                                PrimaryActionButton(text = "Continue", onClick = { status = if (AmountInputPolicy.isValid(amount)) "Amount is ready for host-app review." else "Enter an amount above zero." })
                                Text(status, color = MaterialTheme.colorScheme.onBackground.copy(alpha = .68f), style = MaterialTheme.typography.bodySmall)
                            }
                        }
                        item { Text("Recent activity", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleMedium) }
                        item { TransactionSummaryRow("Northstar Coffee", "Today · Food & drink", "\$5.80", false) }
                        item { TransactionSummaryRow("Salary deposit", "Oct 14 · Income", "\$3,200.00", true) }
                    }
                    ShowcaseTab.Components -> {
                        item { Header("Component kit", "Building blocks for everyday money") }
                        item { Text("Balance summary", fontWeight = FontWeight.SemiBold); BalanceSummaryCard("Travel fund", "\$2,640.00", "Goal · 66% funded") }
                        item { Text("Budget progress", fontWeight = FontWeight.SemiBold); BudgetProgressCard("Dining", "\$184", "\$250", .74f) }
                        item { Text("Transaction rows", fontWeight = FontWeight.SemiBold); TransactionSummaryRow("Green Market", "Yesterday · Groceries", "\$28.40", false) }
                        item { Text("Payment states", fontWeight = FontWeight.SemiBold); TransferStatusCard("Transfer complete", "Maya received \$45.00") }
                        item { TransferStatusCard("Needs attention", "Check the amount and try again.", positive = false) }
                        item { Text("Card preview", fontWeight = FontWeight.SemiBold); PaymentCardPreview("Everyday", "Alex Morgan", "4829") }
                        item { Text("Linked account", fontWeight = FontWeight.SemiBold); FinancialAccountRow("Northstar Bank", "Checking", "•••• 4829", "\$4,280") }
                        item { Text("Fee breakdown", fontWeight = FontWeight.SemiBold); FeeBreakdownCard("\$45.00", "\$0.50", "\$45.50") }
                        item { Text("Spending category", fontWeight = FontWeight.SemiBold); SpendingCategoryRow("Dining", "\$184", .74f) }
                        item { Text("Filter chips", fontWeight = FontWeight.SemiBold) }
                        item { Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) { listOf("All", "Income", "Food").forEachIndexed { index, label -> TransactionCategoryChip(label, index == 0) {} } } }
                    }
                    ShowcaseTab.Theme -> {
                        item { Header("Make it yours", "Three accessible color palettes") }
                        item { BalanceSummaryCard("Preview account", "\$4,280.00", "Choose a palette below") }
                        item { ComposeKitPalette.entries.forEach { option ->
                            androidx.compose.material3.FilterChip(selected = palette == option, onClick = { paletteName = option.name }, label = { Text(option.name) })
                        } }
                        item { TransferStatusCard("Designed for host apps", "ComposeKit keeps visual components separate from payment logic.") }
                        item { Text("All values shown are fictional. This showcase does not connect to a bank.", color = MaterialTheme.colorScheme.onBackground.copy(alpha = .68f), style = MaterialTheme.typography.bodySmall) }
                    }
                }
            }
        }
    }
}

@Composable
private fun Header(title: String, subtitle: String) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(title, color = MaterialTheme.colorScheme.primary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(subtitle, color = MaterialTheme.colorScheme.onBackground.copy(alpha = .68f))
    }
}
