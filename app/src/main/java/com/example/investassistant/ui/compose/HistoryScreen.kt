package com.example.investassistant.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investassistant.ui.data.InvestmentRecord
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun HistoryScreen(
    records: List<InvestmentRecord>,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "投资历史记录",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp)
        )

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            items(records) { record ->
                InvestmentRecordItem(record = record)
            }
        }
    }
}

@Composable
fun InvestmentRecordItem(record: InvestmentRecord) {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
    val investDate = dateFormat.format(record.investDate)
    val closeDate = dateFormat.format(record.closeDate)
    val isProfit = record.profit > 0

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 金额和收益
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = androidx.compose.ui.Alignment.CenterEnd
            ) {
                Text(
                    text = "¥${record.amount.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isProfit) Color(0xFF2E7D32) else Color.Red
                )
                Text(
                    text = "${if (isProfit) "+" else ""}${record.profit.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isProfit) Color(0xFF2E7D32) else Color.Red,
                    modifier = Modifier.align(androidx.compose.ui.Alignment.CenterEnd)
                )
            }

            // 信心度和盈亏
            Text(
                text = "信心度: ${record.confidence.toInt()}% | ${if (isProfit) "盈利" else "亏损"}: ¥${record.profit.toInt()}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // 投资备注
            Text(
                text = "投资备注: ${record.note}",
                fontSize = 14.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // 经验总结（示例，可扩展）
            Text(
                text = "经验总结: ${record.note}", // 可单独加一个 summary 字段
                fontSize = 14.sp,
                color = Color.Blue,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // 时间
            Text(
                text = "投资: $investDate | 结果: $closeDate",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}