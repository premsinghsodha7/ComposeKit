package com.premsingh.composekit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/**
 * A prominent action for a screen's primary task.
 *
 * Keep business logic in the host app and pass it through [onClick].
 */
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
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF173C49))
    ) {
        Text(
            text = if (loading) "Please wait…" else text,
            modifier = Modifier.padding(vertical = 6.dp),
            fontWeight = FontWeight.SemiBold
        )
    }
}

/** A compact summary card whose content remains fully readable by accessibility services. */
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
        colors = CardDefaults.cardColors(containerColor = Color(0xFF173C49))
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(title.uppercase(), style = MaterialTheme.typography.labelMedium, color = Color(0xFFB9C9CF))
            Text(amount, style = MaterialTheme.typography.headlineMedium, color = Color.White, fontWeight = FontWeight.Bold)
            Text(supportingText, style = MaterialTheme.typography.bodySmall, color = Color(0xFFD4E0E3))
        }
    }
}

/**
 * A decimal entry field with a visible currency code.
 *
 * Validation and currency formatting belong to the host application.
 */
@Composable
fun AmountField(
    value: String,
    onValueChange: (String) -> Unit,
    currencyCode: String,
    modifier: Modifier = Modifier,
    label: String = "Amount"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(label) },
        trailingIcon = {
            Row(modifier = Modifier.padding(end = 12.dp)) {
                Text(currencyCode, style = MaterialTheme.typography.labelLarge)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        singleLine = true
    )
}
