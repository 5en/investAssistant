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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.investassistant.ui.data.InvestmentRecord
import com.example.investassistant.ui.viewmodel.InvestmentViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.UUID

/**
 * 投资记录页面 - 优化后（解决滚动嵌套无限高度异常）
 * 核心修改：根布局改为LazyColumn，所有内容拆分为独立item，避免滚动嵌套
 */
@Composable
fun RecordInvestmentScreen(
    viewModel: InvestmentViewModel = viewModel(),
    onSavePendingRecord: (InvestmentRecord) -> Unit,
    // 新增：列表项结果记录回调，供外部处理业务（如更新ViewModel）
    onRecordResult: (InvestmentRecord, Float, String) -> Unit = { _, _, _ -> },
    modifier: Modifier = Modifier
) {
    // 弹窗状态：选中的待记录结果的投资项（修复原代码使用错误）
    val (selectedRecord, setSelectedRecord) = remember { mutableStateOf<InvestmentRecord?>(null) }
    // 表单状态
    val (amount, setAmount) = remember { mutableStateOf(10000f) }
    val (confidence, setConfidence) = remember { mutableStateOf(50f) }
    val (note, setNote) = remember { mutableStateOf("") }
    val (profit, setProfit) = remember { mutableStateOf(0f) }
    val (resultNote,setResultNote) = remember { mutableStateOf("") }

    // 根布局改为LazyColumn，统一管理所有内容（表单+列表），彻底解决滚动嵌套
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp), // 统一设置item间距，替代原Column的spacedBy
        contentPadding = PaddingValues(bottom = 24.dp) // 底部内边距，避免列表被底部遮挡
    ) {
        // ========== 独立item：页面标题 ==========
        item {
            Text(
                text = "新建投资记录",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }

        // ========== 独立item：投资金额滑块 ==========
        item {
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
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // ========== 独立item：决策信心度滑块 ==========
        item {
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
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // ========== 独立item：投资备注输入框 ==========
        item {
            OutlinedTextField(
                value = note,
                onValueChange = { setNote(it) },
                label = { Text("投资备注") },
                placeholder = { Text("记录投资理由或关键信息...") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )
        }

        // ========== 独立item：收益输入框 ==========
//        item {
//            OutlinedTextField(
//                value = profit.toString(),
//                onValueChange = { setProfit(it.toFloatOrNull() ?: 0f) },
//                label = { Text("收益/亏损（元）") },
//                placeholder = { Text("正数盈利，负数亏损") },
//                modifier = Modifier.fillMaxWidth(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
//            )
//        }

        // ========== 独立item：保存按钮 ==========
        item {
            Button(
                onClick = {
                    val record = InvestmentRecord(
                        id = UUID.randomUUID().toString(),
                        amount = amount,
                        confidence = confidence,
                        note = note,
                        profit = profit,
                        investDate = System.currentTimeMillis() // 补充时间戳，原代码可能缺失
                    )
                    onSavePendingRecord(record)
                    // 保存后清空表单（可选，提升体验）
                    setAmount(10000f)
                    setConfidence(50f)
                    setNote("")
                    setProfit(0f)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2E7D32)
                )
            ) {
                Text(text = "保存投资记录", color = Color.White)
            }
        }

        // ========== 独立item：待记录结果列表标题 ==========
        item {
            Text(
                text = "待记录结果的投资",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

        // ========== 列表items：待记录投资项 ==========
        items(
            items = viewModel.pendingRecords,
            key = { it.id } // 设置唯一key，提升重组性能
        ) { record ->
            PendingRecordItem(
                record = record,
                onRecordResult = {
                    // 点击"记录结果"，设置选中项，触发弹窗显示
                    setSelectedRecord(record)
                }
            )
        }
    }

    // ========== 弹窗：记录投资结果（独立Composable，移出Item内部，修复嵌套问题） ==========
    selectedRecord?.let { record ->
        RecordResultDialog(
            record = record,
            onDismiss = { setSelectedRecord(null) }, // 取消/关闭，清空选中项
            onConfirm = { profit, resultNote ->
                // 确认记录，回调外部处理业务（如更新ViewModel、修改记录状态）
                onRecordResult(record, profit, resultNote)
                // 清空选中项，关闭弹窗
                setSelectedRecord(null)
            }
        )
    }
}

/**
 * 待记录结果的投资列表项（独立Composable，无嵌套弹窗）
 */
@Composable
fun PendingRecordItem(
    record: InvestmentRecord,
    onRecordResult: () -> Unit // 点击"记录结果"的回调
) {
    val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.CHINA)
    val investDate = dateFormat.format(record.investDate)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = false) { }, // 保留你原有防止点击穿透的逻辑
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // 保留原有阴影配置
        // ========== 新增核心配置：设置协调的背景色 ==========
        colors = CardDefaults.cardColors(
            // 选项1：浅灰蓝（最通用，适配绝大多数安卓浅色界面）
            containerColor = Color(0xFFF5F7FA),
            // 选项2：浅米白（适配暖色调界面，如浅黄/米色底）
//             containerColor = Color(0xFFFCFBF7),
            // 选项3：极浅青（适配冷色调/偏蓝的界面）
//             containerColor = Color(0xFFF0F8F9)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "¥${record.amount.toInt()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "信心度: ${record.confidence.toInt()}% | $investDate",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Text(
                    text = "投资备注: ${record.note}",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Button(
                onClick = onRecordResult,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2E7D32))
            ) {
                Text(text = "记录结果", color = Color.White)
            }
        }
    }
}


// 预览函数（可选，方便开发时查看UI）
@Preview(showBackground = true, widthDp = 360)
@Composable
fun RecordInvestmentScreenPreview() {
    RecordInvestmentScreen(
        onSavePendingRecord = {},
        onRecordResult = { _, _, _ -> }
    )
}