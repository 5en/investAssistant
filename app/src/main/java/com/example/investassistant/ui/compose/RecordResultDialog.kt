package com.example.investassistant.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.investassistant.ui.data.InvestmentRecord

// RecordInvestmentScreen.kt 中新增弹窗逻辑
@Composable
fun RecordResultDialog(
    record: InvestmentRecord,
    onDismiss: () -> Unit,
    onConfirm: (Float, String) -> Unit
) {
    val profitInput = remember { mutableStateOf("") }
    val resultNoteInput = remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "记录投资结果") },
        text = {
            Column {
                Text(text = "投资金额: ¥${record.amount.toInt()}")
                Text(text = "信心度: ${record.confidence}%")

                Spacer(modifier = Modifier.height(16.dp))

                // 收益金额输入
                OutlinedTextField(
                    value = profitInput.value,
                    onValueChange = { profitInput.value = it },
                    label = { Text("收益金额") },
                    placeholder = { Text("正数为盈利，负数为亏损") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 结果备注输入
                OutlinedTextField(
                    value = resultNoteInput.value,
                    onValueChange = { resultNoteInput.value = it },
                    label = { Text("结果备注") },
                    placeholder = { Text("记录关键经验...") },
                    maxLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val profit = profitInput.value.toFloatOrNull() ?: 0.0f
                    onConfirm(profit, resultNoteInput.value)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text("确认")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("取消")
            }
        }
    )
}
