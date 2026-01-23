package com.example.investassistant.ui.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.investassistant.ui.activity.InvestmentTopBar
import com.example.investassistant.ui.viewmodel.InvestmentViewModel

@Preview
@Composable
fun InvestmentMainScreen() {
    val viewModel: InvestmentViewModel = viewModel()
    val selectedTabIndex = viewModel.selectedTabIndex.collectAsState().value

    Scaffold(
        topBar = {
            InvestmentTopBar() // 顶部导航栏
        }
    ) { innerPadding ->
        // 核心修改：用Column垂直布局包裹Tab栏和页面内容
        // Column占满全屏，先显示Tab栏，再显示页面内容
        Column(
            modifier = Modifier
                .fillMaxSize() // 占满整个屏幕
                .padding(innerPadding) // 应用Scaffold的内边距（避免被TopBar遮挡）
        ) {
            val tabs = listOf("记录投资", "历史记录", "统计分析")

            // Tab栏：只占自身宽度和高度，固定在Column顶部
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = Color(0xFF2E7D32),
                // 关键修改1：移除fillMaxSize()，改为fillMaxWidth()（只占满宽度，高度自适应）
                modifier = Modifier.fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { viewModel.switchTab(index) },
                        selectedContentColor = Color(0xFF2E7D32),
                        unselectedContentColor = Color.Gray
                    )
                }
            }

            // 页面内容：占Column剩余的所有空间（关键：weight(1f)）
            // 关键修改2：用weight(1f)让内容区占满Tab栏下方的剩余空间
            Column(
                modifier = Modifier
                    .weight(1f) // 占剩余空间
                    .fillMaxWidth() // 占满宽度
            ) {
                // 根据选中的Tab显示对应页面
                when (selectedTabIndex) {
                    0 -> RecordInvestmentScreen(
                        onSave = { record -> viewModel.addRecord(record) },
                        // 移除重复的innerPadding（已在最外层Column应用）
                        modifier = Modifier.fillMaxSize()
                    )
                    1 -> HistoryScreen(
                        records = viewModel.records,
                        modifier = Modifier.fillMaxSize()
                    )
                    2 -> StatisticsScreen(
                        viewModel = viewModel,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}