package com.example.investassistant.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investassistant.ui.data.InvestmentRecord
import com.example.investassistant.ui.theme.GREEN
import com.example.investassistant.ui.theme.RED
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * 投资历史记录页面
 * @param records 投资记录列表
 * @param onClearAllClick 清空所有记录的点击事件
 * @param onDeleteRecord 单个记录删除的回调（新增）
 */
@Composable
fun HistoryScreen(
    records: List<InvestmentRecord>,
    onClearAllClick: () -> Unit,
    onDeleteRecord: (InvestmentRecord) -> Unit, // 新增：单个记录删除回调
    modifier: Modifier = Modifier
) {
    // 核心：按时间字段倒序排序（替换成你实际的时间字段名，比如record.time）
    val sortedRecords = records.sortedByDescending { it.closeDate }

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
        items(sortedRecords, key = { it.id }) { record ->
            // 传入删除回调（新增）
            InvestmentRecordItem(
                record = record,
                onDeleteRecord = onDeleteRecord
            )
        }
    }
}

/**
 * 投资记录Item（贴合截图UI结构 + 左滑删除）
 * @param onDeleteRecord 单个记录删除回调（新增）
 */
@Composable
private fun InvestmentRecordItem(
    record: InvestmentRecord,
    onDeleteRecord: (InvestmentRecord) -> Unit // 新增：删除回调
) {
    // 1. 初始化滑动删除状态（指定滑动方向：从右向左滑）
    val swipeState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            // 当滑动到删除状态时，触发删除逻辑
            if (value == SwipeToDismissBoxValue.EndToStart) {
                onDeleteRecord(record)
                true // 确认状态变更
            } else {
                false // 其他状态不确认
            }
        }
    )

    // 2. 滑动后重置状态（可选：删除后恢复初始状态，避免UI残留）
    LaunchedEffect(swipeState.currentValue) {
        if (swipeState.currentValue != SwipeToDismissBoxValue.Settled) {
            swipeState.reset()
        }
    }

    // 3. 包裹滑动删除组件（核心）
    SwipeToDismissBox(
        state = swipeState,
        // 滑动方向：从右向左（EndToStart）= 左滑删除
        enableDismissFromEndToStart = true,
        // 左滑时显示的背景（红色+删除图标/文字）
        backgroundContent = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent) // 红色背景
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "删除",
                    tint = Color.White
                )
                Text(
                    text = "删除",
                    color = RED,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        },
        // 前景内容：原有的记录Item布局
        content = {
            // 日期格式化
            val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
            val investDate = dateFormat.format(record.investDate)
            val closeDate = dateFormat.format(record.closeDate)
            val isProfit = record.profit > 0
            // 状态判断（盈利/亏损）
            val profitColor = when {
                record.profit > 0 -> RED    // 盈利-红
                record.profit < 0 -> GREEN  // 亏损-绿
                else -> Color.Gray          // 持平-灰
            }

            // 计算收益率（保留1位小数，避免金额为0崩溃）
            val profitRate = if (record.amount == 0f) 0f else (record.profit / record.amount) * 100
            val profitRateText = String.format("%.1f%%", profitRate)

            // 卡片容器（带阴影，贴合截图视觉）
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.cardColors(
                    // 选项1：浅灰蓝（最通用，适配绝大多数安卓浅色界面）
                    containerColor = Color(0xFFF5F7FA),
                    // 选项2：浅米白（适配暖色调界面，如浅黄/米色底）
//                     containerColor = Color(0xFFFCFBF7),
                    // 选项3：极浅青（适配冷色调/偏蓝的界面）
//                     containerColor = Color(0xFFF0F8F9)
                )
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
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    // 4. 经验总结（第四行，仅当resultNote不为空时显示）
                    record.resultNote?.takeIf { it.isNotBlank() }?.let { summary ->
                        Text(
                            text = "经验总结: $summary",
                            fontSize = 14.sp,
                            color = Color(0xFF2196F3), // 蓝色区分经验总结
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
    )
}