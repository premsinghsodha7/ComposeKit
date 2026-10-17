package com.premsingh.composekit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Brush
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

object ComposeKitColors {
    val Navy = Color(0xFF173C49)
    val Mint = Color(0xFFB9F2D5)
    val Ink = Color(0xFF17212B)
    val Muted = Color(0xFF75818B)
    val Canvas = Color(0xFFF4F7F8)
}

enum class ComposeKitPalette { Ocean, Garden, Midnight }

@Composable
fun ComposeKitTheme(palette: ComposeKitPalette = ComposeKitPalette.Ocean, content: @Composable () -> Unit) {
    val scheme = when (palette) {
        ComposeKitPalette.Ocean -> lightColorScheme(primary = ComposeKitColors.Navy, secondary = ComposeKitColors.Mint, primaryContainer = Color(0xFFDCE9EC), onPrimaryContainer = ComposeKitColors.Navy, secondaryContainer = Color(0xFFDDF3E7), onSecondaryContainer = ComposeKitColors.Navy, background = ComposeKitColors.Canvas, surface = Color.White, onBackground = ComposeKitColors.Ink, onSurface = ComposeKitColors.Ink)
        ComposeKitPalette.Garden -> lightColorScheme(primary = Color(0xFF42664B), secondary = Color(0xFFDCE8D4), primaryContainer = Color(0xFFDCE8D4), onPrimaryContainer = Color(0xFF222A23), secondaryContainer = Color(0xFFDCE8D4), onSecondaryContainer = Color(0xFF222A23), background = Color(0xFFF5F6EE), surface = Color.White, onBackground = Color(0xFF222A23), onSurface = Color(0xFF222A23))
        ComposeKitPalette.Midnight -> androidx.compose.material3.darkColorScheme(primary = Color(0xFF8EDDB6), secondary = Color(0xFF355A51), primaryContainer = Color(0xFF28453C), onPrimaryContainer = Color(0xFFE6EEF0), secondaryContainer = Color(0xFF355A51), onSecondaryContainer = Color(0xFFE6EEF0), background = Color(0xFF101B20), surface = Color(0xFF1C2A30), onBackground = Color(0xFFE6EEF0), onSurface = Color(0xFFE6EEF0))
    }
    MaterialTheme(
        colorScheme = scheme,
        content = content
    )
}

/** A prominent action. Business logic stays in the host app and is supplied through [onClick]. */
@Composable
fun PrimaryActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled && !loading,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(18.dp).semantics { contentDescription = "Loading" },
                strokeWidth = 2.dp,
                color = Color.White
            )
            Spacer(Modifier.size(10.dp))
        }
        Text(text, modifier = Modifier.padding(vertical = 6.dp), fontWeight = FontWeight.SemiBold)
    }
}

/** A summary card for a balance, budget, or other prominent amount. */
@Composable
fun BalanceSummaryCard(
    title: String,
    amount: String,
    supportingText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(title.uppercase(), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
            Text(amount, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
            Text(supportingText, style = MaterialTheme.typography.bodySmall, color = Color(0xFFD4E0E3))
        }
    }
}

/** Decimal amount input with an explicit currency label and predictable two-decimal input. */
@Composable
fun AmountField(
    value: String,
    onValueChange: (String) -> Unit,
    currencyCode: String,
    modifier: Modifier = Modifier,
    label: String = "Amount",
    isError: Boolean = false,
    supportingText: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(AmountInputPolicy.sanitize(it)) },
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        trailingIcon = { Text(currencyCode, modifier = Modifier.padding(end = 12.dp), style = MaterialTheme.typography.labelLarge) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true,
        isError = isError,
        supportingText = { supportingText?.let { Text(it) } }
    )
}

/** A transaction row, with a clear label for credit versus debit amounts. */
@Composable
fun TransactionSummaryRow(
    title: String,
    subtitle: String,
    amount: String,
    isCredit: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Surface(shape = CircleShape, color = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(42.dp)) {
            androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                Text(title.take(1).uppercase(), color = ComposeKitColors.Navy, fontWeight = FontWeight.Bold)
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(title, fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.bodyMedium)
            Text(subtitle, color = ComposeKitColors.Muted, style = MaterialTheme.typography.bodySmall)
        }
        Text(
            (if (isCredit) "+" else "−") + amount,
            color = if (isCredit) Color(0xFF246747) else ComposeKitColors.Ink,
            fontWeight = FontWeight.SemiBold
        )
    }
}

/** A reusable spend limit with a clear used and remaining summary. */
@Composable
fun BudgetProgressCard(title: String, spent: String, limit: String, progress: Float, modifier: Modifier = Modifier) {
    Card(modifier.fillMaxWidth(), shape = RoundedCornerShape(20.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(title, fontWeight = FontWeight.SemiBold)
                Text("$spent of $limit", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
            }
            androidx.compose.material3.LinearProgressIndicator({ progress.coerceIn(0f, 1f) }, Modifier.fillMaxWidth())
            Text("${((1f - progress.coerceIn(0f, 1f)) * 100).toInt()}% remaining", color = ComposeKitColors.Muted, style = MaterialTheme.typography.bodySmall)
        }
    }
}

/** A compact status banner for completed, pending, or rejected payment flows. */
@Composable
fun TransferStatusCard(title: String, detail: String, positive: Boolean = true, modifier: Modifier = Modifier) {
    val tint = if (positive) Color(0xFFE3F4EA) else Color(0xFFFFE9E5)
    Card(modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = tint)) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(if (positive) "✓" else "!", color = if (positive) Color(0xFF246747) else Color(0xFFAA4036), fontWeight = FontWeight.Bold)
            Column { Text(title, fontWeight = FontWeight.SemiBold); Text(detail, color = ComposeKitColors.Muted, style = MaterialTheme.typography.bodySmall) }
        }
    }
}

/** A reusable category selector for transaction and budget filters. */
@Composable
fun TransactionCategoryChip(label: String, selected: Boolean, onClick: () -> Unit) {
    FilterChip(selected = selected, onClick = onClick, label = { Text(label) })
}

/** A ready-to-theme virtual card face for wallet and card-management screens. */
@Composable
fun PaymentCardPreview(cardName: String, cardholder: String, lastFour: String, modifier: Modifier = Modifier) {
    Surface(modifier = modifier.fillMaxWidth().height(188.dp), shape = RoundedCornerShape(24.dp), color = MaterialTheme.colorScheme.primary) {
        Box(Modifier.background(Brush.linearGradient(listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.primary.copy(alpha = .76f)))).padding(20.dp)) {
            Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(cardName.uppercase(), color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold); Text("VISA", color = Color.White, fontWeight = FontWeight.Bold) }
                Text("••••  ••••  ••••  $lastFour", color = Color.White, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.SemiBold)
                Text(cardholder.uppercase(), color = Color.White.copy(alpha = .86f), style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

/** A compact account row that keeps the available balance easy to scan. */
@Composable
fun FinancialAccountRow(institution: String, accountType: String, maskedNumber: String, balance: String, modifier: Modifier = Modifier) {
    Card(modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Row(Modifier.padding(16.dp), horizontalArrangement = Arrangement.spacedBy(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(shape = RoundedCornerShape(14.dp), color = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(44.dp)) {
                Box(contentAlignment = Alignment.Center) { Text(institution.take(1).uppercase(), color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold) }
            }
            Column(Modifier.weight(1f)) { Text(institution, fontWeight = FontWeight.SemiBold); Text("$accountType · $maskedNumber", color = ComposeKitColors.Muted, style = MaterialTheme.typography.bodySmall) }
            Text(balance, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
    }
}

/** A transparent fee breakdown for review and confirmation screens. */
@Composable
fun FeeBreakdownCard(amount: String, fee: String, total: String, modifier: Modifier = Modifier) {
    Card(modifier.fillMaxWidth(), shape = RoundedCornerShape(18.dp), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Transfer amount"); Text(amount, fontWeight = FontWeight.SemiBold) }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Service fee", color = ComposeKitColors.Muted); Text(fee, color = ComposeKitColors.Muted) }
            androidx.compose.material3.HorizontalDivider()
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text("Total", fontWeight = FontWeight.Bold); Text(total, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary) }
        }
    }
}

/** A category label with a relative spend bar for reports and budget overviews. */
@Composable
fun SpendingCategoryRow(label: String, amount: String, progress: Float, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) { Text(label, fontWeight = FontWeight.Medium); Text(amount, color = ComposeKitColors.Muted) }
        androidx.compose.material3.LinearProgressIndicator({ progress.coerceIn(0f, 1f) }, Modifier.fillMaxWidth())
    }
}

/** Keeps decimal-entry rules independent from UI state, so host apps can validate consistently. */
object AmountInputPolicy {
    fun sanitize(raw: String): String {
        val normalized = raw.replace(',', '.')
        val result = StringBuilder()
        var hasDecimal = false
        var decimals = 0
        for (character in normalized) {
            when {
                character.isDigit() -> {
                    if (!hasDecimal || decimals < 2) {
                        result.append(character)
                        if (hasDecimal) decimals++
                    }
                }
                character == '.' && !hasDecimal -> {
                    result.append(character)
                    hasDecimal = true
                }
            }
        }
        return result.toString()
    }

    fun isValid(raw: String): Boolean = raw.matches(Regex("\\d+(?:[.,]\\d{1,2})?")) &&
        raw.toBigDecimalOrNull()?.compareTo(java.math.BigDecimal.ZERO) == 1
}

@Preview(showBackground = true)
@Composable
private fun BalanceSummaryCardPreview() {
    ComposeKitTheme {
        BalanceSummaryCard("Everyday account", "\$12,480.50", "Sample balance · fictional data", Modifier.padding(16.dp))
    }
}
