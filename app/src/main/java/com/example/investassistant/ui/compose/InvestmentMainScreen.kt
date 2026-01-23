package com.example.investassistant.ui.compose

import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.investassistant.ui.activity.InvestmentTopBar
import com.example.investassistant.ui.viewmodel.InvestmentViewModel

@Composable
fun InvestmentMainScreen() {
    val viewModel: InvestmentViewModel = viewModel()
    val selectedTabIndex = viewModel.selectedTabIndex.collectAsState().value

    Scaffold(
        topBar = {
            InvestmentTopBar() // 你之前实现的 TopBar
        }
    ) { innerPadding ->
        val tabs = listOf("记录投资", "历史记录", "统计分析")
        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = Color(0xFF2E7D32),
            modifier = Modifier.fillMaxSize()
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

        // 根据选中的 Tab 显示对应页面
        when (selectedTabIndex) {
            0 -> RecordInvestmentScreen(
                onSave = { record -> viewModel.addRecord(record) },
                modifier = Modifier.padding(innerPadding)
            )
            1 -> HistoryScreen(
                records = viewModel.records,
                modifier = Modifier.padding(innerPadding)
            )
            2 -> StatisticsScreen(
                viewModel = viewModel,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}