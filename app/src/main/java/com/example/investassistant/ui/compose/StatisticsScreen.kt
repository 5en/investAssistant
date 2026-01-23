package com.example.investassistant.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investassistant.ui.viewmodel.InvestmentViewModel

@Composable
fun StatisticsScreen(
    viewModel: InvestmentViewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "统计分析", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        // 收益统计卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "收益统计", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                StatItem(label = "总投资次数", value = viewModel.totalInvestments.toString())
                StatItem(label = "盈利次数", value = viewModel.profitableCount.toString(), color = Color(0xFF2E7D32))
                StatItem(label = "亏损次数", value = viewModel.lossCount.toString(), color = Color.Red)
                StatItem(label = "胜率", value = String.format("%.1f%%", viewModel.winRate))
                StatItem(label = "总收益", value = "¥${viewModel.totalProfit.toInt()}", color = if (viewModel.totalProfit > 0) Color(0xFF2E7D32) else Color.Red)
            }
        }

        // 信心度分析卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "信心度分析", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                StatItem(label = "平均信心度", value = String.format("%.1f%%", viewModel.avgConfidence))
                StatItem(label = "盈利时平均信心度", value = String.format("%.1f%%", viewModel.avgConfidenceProfitable), color = Color(0xFF2E7D32))
                StatItem(label = "亏损时平均信心度", value = String.format("%.1f%%", viewModel.avgConfidenceLoss), color = Color.Red)
                StatItem(label = "信心度准确率", value = "0%", color = Color.Gray)
            }
        }

        // 投资建议卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "投资建议", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(text = "• 您的投资胜率中等，可以考虑优化投资决策流程。")
                Text(text = "• 您的信心度与实际结果偏差较大，建议降低主观预期，多做市场研究。")
            }
        }
    }
}

@Composable
fun StatItem(label: String, value: String, color: Color = Color.Black) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text = label, fontSize = 14.sp, color = Color.Gray)
        Text(text = value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = color)
    }
}