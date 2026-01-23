package com.example.investassistant.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investassistant.ui.data.InvestmentRecord
import java.util.UUID

@Composable
fun RecordInvestmentScreen(
    onSave: (InvestmentRecord) -> Unit,
    modifier: Modifier = Modifier
) {
    val (amount, setAmount) = remember { mutableStateOf(10000f) }
    val (confidence, setConfidence) = remember { mutableStateOf(50f) }
    val (note, setNote) = remember { mutableStateOf("") }
    val (profit, setProfit) = remember { mutableStateOf(0f) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "新建投资记录", fontSize = 18.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold)

        // 投资金额滑块
        Column {
            Text(text = "投资金额", fontSize = 14.sp, color = Color.Gray)
            Slider(
                value = amount,
                onValueChange = { setAmount(it) },
                valueRange = 1000f..100000f,
                colors = SliderDefaults.colors(
                    activeTrackColor = Color(0xFF2E7D32),
                    thumbColor = Color(0xFF2E7D32)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "¥${amount.toInt()}",
                fontSize = 16.sp,
                color = Color(0xFF2E7D32),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // 决策信心度滑块
        Column {
            Text(text = "决策信心度", fontSize = 14.sp, color = Color.Gray)
            Slider(
                value = confidence,
                onValueChange = { setConfidence(it) },
                valueRange = 0f..100f,
                colors = SliderDefaults.colors(
                    activeTrackColor = Color(0xFF2E7D32),
                    thumbColor = Color(0xFF2E7D32)
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "${confidence.toInt()}%",
                fontSize = 16.sp,
                color = Color(0xFF2E7D32),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // 投资备注
        OutlinedTextField(
            value = note,
            onValueChange = { setNote(it) },
            label = { Text("投资备注") },
            placeholder = { Text("记录投资理由或关键信息...") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )

        // 收益输入（可选，平仓后填写）
        OutlinedTextField(
            value = profit.toString(),
            onValueChange = { setProfit(it.toFloatOrNull() ?: 0f) },
            label = { Text("收益/亏损（元）") },
            placeholder = { Text("正数盈利，负数亏损") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        // 保存按钮
        Button(
            onClick = {
                val record = InvestmentRecord(
                    id = UUID.randomUUID().toString(),
                    amount = amount,
                    confidence = confidence,
                    note = note,
                    profit = profit
                )
                onSave(record)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
        ) {
            Text(text = "保存投资记录", color = Color.White)
        }
    }
}