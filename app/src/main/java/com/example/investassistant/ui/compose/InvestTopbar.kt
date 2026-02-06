package com.example.investassistant.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


// 顶部导航栏
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvestmentTopBar(navigationCallback:()->Unit) {
    TopAppBar(
        title = {
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = "投资决策助手",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "记录投资决策，分析决策质量",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { navigationCallback() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
            }
        },
//        actions = {
//            Text(
//                text = "总收益率",
//                fontSize = 12.sp,
//                color = Color.Gray,
//                modifier = Modifier.padding(end = 8.dp)
//            )
//            Text(
//                text = "-2.2%",
//                fontSize = 14.sp,
//                color = Color.Red,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.padding(end = 16.dp)
//            )
//        }
    )
}

// 选项卡栏
@Composable
fun InvestmentTabBar() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("记录投资", "历史记录", "统计分析")

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color.White,
                shape = RoundedCornerShape(8.dp)
            )
            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        tabs.forEachIndexed { index, title ->
            val isSelected = index == selectedTab
            Button(
                onClick = { selectedTab = index },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isSelected) Color(0xFF2E7D32) else Color.Transparent,
                    contentColor = if (isSelected) Color.White else Color.Gray
                ),
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.weight(1f)
            ) {
                Text(text = title, fontSize = 14.sp)
            }
        }
    }
}

// 投资记录表单
@Composable
fun InvestmentForm() {
    // 状态管理
    var investmentAmount by remember { mutableStateOf(10000f) }
    var confidenceLevel by remember { mutableStateOf(50f) }
    var note by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = "新建投资记录",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        // 投资金额滑块
        Column {
            Text(text = "投资金额", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "¥1,000", fontSize = 12.sp, color = Color.Gray)
                Slider(
                    value = investmentAmount,
                    onValueChange = { investmentAmount = it },
                    valueRange = 1000f..100000f,
                    steps = 30, // 100000-1000=99000，每10000一步
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color(0xFF2E7D32),
                        thumbColor = Color(0xFF2E7D32)
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(text = "¥100,000", fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = "¥${investmentAmount.toInt()}",
                fontSize = 16.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // 决策信心度滑块
        Column {
            Text(text = "决策信心度", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "0%", fontSize = 12.sp, color = Color.Gray)
                Slider(
                    value = confidenceLevel,
                    onValueChange = { confidenceLevel = it },
                    valueRange = 0f..100f,
                    colors = SliderDefaults.colors(
                        activeTrackColor = Color(0xFF2E7D32),
                        thumbColor = Color(0xFF2E7D32)
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(text = "100%", fontSize = 12.sp, color = Color.Gray)
            }
            Text(
                text = "${confidenceLevel.toInt()}%",
                fontSize = 16.sp,
                color = Color(0xFF2E7D32),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        // 投资备注输入框
        Column {
            Text(text = "投资备注", fontSize = 14.sp, color = Color.Gray)
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = note,
                onValueChange = { note = it },
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White, RoundedCornerShape(8.dp))
                            .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        if (note.isEmpty()) {
                            Text(
                                text = "记录投资理由或关键信息...",
                                color = Color.LightGray,
                                fontSize = 14.sp
                            )
                        }
                        innerTextField()
                    }
                },
                modifier = Modifier.height(120.dp)
            )
        }

        // 保存按钮
        Button(
            onClick = {
                // 这里可以添加保存逻辑，比如提交到服务器或本地数据库
                // 示例：打印日志或弹出Toast
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF2E7D32),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
//            Icon(Icons.AutoMirrored.Filled.List, contentDescription = "保存")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "保存投资记录", fontSize = 16.sp)
        }
    }
}