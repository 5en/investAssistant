package com.example.investassistant.ui.viewmodel
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investassistant.ui.data.InvestmentRecord
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class InvestmentViewModel : ViewModel() {
    // 投资记录列表
    private val _records = mutableStateListOf<InvestmentRecord>()
    val records = _records

    // 当前选中的 Tab 索引
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()

    // 切换 Tab
    fun switchTab(index: Int) {
        _selectedTabIndex.value = index
    }

    // 添加投资记录
    fun addRecord(record: InvestmentRecord) {
        viewModelScope.launch {
            _records.add(0, record) // 最新记录放最前面
        }
    }

    // 统计数据计算
    val totalInvestments: Int get() = records.size
    val profitableCount: Int get() = records.count { it.profit > 0 }
    val lossCount: Int get() = records.count { it.profit < 0 }
    val winRate: Float get() = if (totalInvestments == 0) 0f else (profitableCount.toFloat() / totalInvestments) * 100
    val totalProfit: Float get() = records.map { it.profit }.sum()
    val avgConfidence: Float get() = if (totalInvestments == 0) 0f else records.map { it.confidence }.average().toFloat()
    val avgConfidenceProfitable: Float get() = records.filter { it.profit > 0 }.let { list ->
        if (list.isEmpty()) 0f else list.map { it.confidence }.average().toFloat()
    }
    val avgConfidenceLoss: Float get() = records.filter { it.profit < 0 }.let { list ->
        if (list.isEmpty()) 0f else list.map { it.confidence }.average().toFloat()
    }
}