package com.example.investassistant.ui.compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 适配整体界面色调的 Card 组件
 * 配色选用柔和的浅灰蓝，与安卓原生界面风格协调，无压抑感也不夸张
 */
@Composable
fun CoordinatedColorCard(
    // 卡片内容文本（可根据你的需求替换为自定义内容）
    cardContent: String,
    // 内边距（可自定义）
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    // 核心 Card 组件配置
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp), // 卡片外间距
        // 关键：适配整体色调的配色配置
        colors = CardDefaults.cardColors(
            // 卡片背景色：柔和浅灰蓝（安卓界面通用协调色）
            containerColor = Color(0xFFF5F7FA),
            // 点击态颜色（可选，保持协调）
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        // 卡片阴影：适度阴影提升层次感，不突兀
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 4.dp
        ),
        // 圆角：符合安卓设计规范的圆角大小
        shape = MaterialTheme.shapes.medium
    ) {
        // 卡片内部内容区域（可替换为你的实际界面内容）
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .fillMaxWidth()
        ) {
            Column {
                Text(
                    text = cardContent,
                    // 文字颜色与卡片背景协调
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                // 可添加其他组件（如按钮、图片、列表等）
            }
        }
    }
}

// 调用示例（在你的 Composable 界面中使用）
@Composable
fun DemoScreen() {
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        CoordinatedColorCard(cardContent = "你的界面内容1")
        CoordinatedColorCard(cardContent = "你的界面内容2")
        CoordinatedColorCard(cardContent = "你的界面内容3")
    }
}