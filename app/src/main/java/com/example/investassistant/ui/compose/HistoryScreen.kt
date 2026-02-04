package com.example.investassistant.ui.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investassistant.ui.data.InvestmentRecord
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 投资历史记录页面
 * @param records 投资记录列表
 * @param onClearAllClick 清空所有记录的点击事件
 */
@Composable
fun HistoryScreen(
    records: List<InvestmentRecord>,
    onClearAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp), // Item之间的间距
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp)
    ) {
        // 标题栏（含清空按钮）
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "投资历史记录",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                // 清空记录按钮（可点击样式）
                Text(
                    text = "清空记录",
                    fontSize = 14.sp,
                    color = Color(0xFF2196F3), // 蓝色提示色
                    modifier = Modifier
                        .clickable { onClearAllClick() }
                        .padding(4.dp)
                )
            }
        }

        // 投资记录列表
        items(records, key = { it.id }) { record ->
            InvestmentRecordItem(record = record)
        }
    }
}

/**
 * 投资记录Item（贴合截图UI结构）
 */
@Composable
private fun InvestmentRecordItem(record: InvestmentRecord) {
    // 日期格式化
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
    val investDate = dateFormat.format(record.investDate)
    val closeDate = dateFormat.format(record.closeDate)

    // 状态判断（盈利/亏损）
    val isProfit = record.profit > 0
    val profitColor = if (isProfit) Color(0xFF2E7D32) else Color.Red // 盈利绿/亏损红

    // 计算收益率（保留1位小数，避免金额为0崩溃）
    val profitRate = if (record.amount == 0f) 0f else (record.profit / record.amount) * 100
    val profitRateText = String.format("%.1f%%", profitRate)

    // 卡片容器（带阴影，贴合截图视觉）
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // 1. 金额 + 收益 + 收益率（第一行，左右对齐）
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 投资金额
                Text(
                    text = "¥${record.amount.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                // 收益 + 收益率（右侧组合）
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${if (isProfit) "+" else ""}${record.profit.toInt()}",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = profitColor
                    )
                    Text(
                        text = profitRateText,
                        fontSize = 14.sp,
                        color = profitColor
                    )
                }
            }

            // 2. 信心度 + 盈亏类型（第二行）
            Text(
                text = "信心度: ${record.confidence.toInt()}% | ${if (isProfit) "盈利" else "亏损"}: ¥${record.profit.toInt()}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 6.dp)
            )

            // 3. 投资备注（第三行，支持2行省略）
            Text(
                text = "投资备注: ${record.note}",
                fontSize = 14.sp,
                color = Color.Black,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = 4.dp)
            )

            // 4. 经验总结（第四行，仅当resultNote不为空时显示）
            record.resultNote?.takeIf { it.isNotBlank() }?.let { summary ->
                Text(
                    text = "经验总结: $summary",
                    fontSize = 14.sp,
                    color = Color(0xFF2196F3), // 蓝色区分经验总结
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            // 5. 时间信息（第五行，灰色小字）
            Text(
                text = "投资: $investDate | 结果: $closeDate",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}