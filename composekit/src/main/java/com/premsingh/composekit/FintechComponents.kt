package com.premsingh.composekit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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

@Composable
fun ComposeKitTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = ComposeKitColors.Navy,
            secondary = ComposeKitColors.Mint,
            background = ComposeKitColors.Canvas,
            surface = Color.White,
            onBackground = ComposeKitColors.Ink,
            onSurface = ComposeKitColors.Ink
        ),
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
        colors = ButtonDefaults.buttonColors(containerColor = ComposeKitColors.Navy)
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
        colors = CardDefaults.cardColors(containerColor = ComposeKitColors.Navy)
    ) {
        Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(title.uppercase(), style = MaterialTheme.typography.labelMedium, color = Color(0xFFB9C9CF))
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
        Surface(shape = CircleShape, color = ComposeKitColors.Mint, modifier = Modifier.size(42.dp)) {
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
