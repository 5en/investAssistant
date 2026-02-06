package com.example.investassistant.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.investassistant.ui.data.InvestmentRecord
import com.example.investassistant.ui.utils.SPUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InvestmentViewModel : ViewModel() {

    // 当前选中的 Tab 索引
    private val _selectedTabIndex = MutableStateFlow(0)
    val selectedTabIndex: StateFlow<Int> = _selectedTabIndex.asStateFlow()


    // 待记录结果的投资列表
    private val _pendingRecords = mutableStateListOf<InvestmentRecord>()
    val pendingRecords: List<InvestmentRecord> = _pendingRecords

    // 已完成的历史记录列表
    private val _records = mutableStateListOf<InvestmentRecord>()
    val records: List<InvestmentRecord> = _records

    // ========== 核心修改：初始化时读取SP数据 ==========
    init {
        loadRecordsFromSP()
    }

    /**
     * 从SP加载记录，初始化待处理/已完成列表
     */
    private fun loadRecordsFromSP() {
        // 清空原有数据（防止重复加载）
        _pendingRecords.clear()
        _records.clear()
        // 读取SP中的待处理记录
        val pendingList = SPUtils.getPendingRecords()
        _pendingRecords.addAll(pendingList)
        // 读取SP中的已完成记录
        val completedList = SPUtils.getCompletedRecords()
        _records.addAll(completedList)
    }

    // 切换 Tab
    fun switchTab(index: Int) {
        _selectedTabIndex.value = index
    }

    // 保存投资记录 → 先进入待记录列表
    fun addPendingRecord(record: InvestmentRecord) {
        _pendingRecords.add(record)
        SPUtils.savePendingRecords(_pendingRecords)
    }

    // 记录投资结果 → 从待办移到历史记录
    fun recordResult(recordId: String, profit: Float, resultNote: String) {
        val pendingRecord = _pendingRecords.find { it.id == recordId }
        pendingRecord?.let {
            val completedRecord = it.copy(
                profit = profit,
                resultNote = resultNote,
                closeDate = System.currentTimeMillis(),
                isRecordResult = true
            )
            _pendingRecords.remove(it)
            _records.add(completedRecord)
            SPUtils.savePendingRecords(_pendingRecords)
            SPUtils.saveCompletedRecords(_records)
        }
    }

    fun clearRecordResult() {
        _records.clear()
        SPUtils.saveCompletedRecords(_records)
    }

    fun deleteRecord(record: InvestmentRecord){
        _records.remove(record)
        SPUtils.saveCompletedRecords(_records)
    }
    // 统计数据计算
    val totalInvestments: Int get() = records.size
    val profitableCount: Int get() = records.count { it.profit > 0 }
    val lossCount: Int get() = records.count { it.profit < 0 }
    val winRate: Float get() = if (totalInvestments == 0) 0f else (profitableCount.toFloat() / totalInvestments) * 100
    val totalProfit: Float get() = records.map { it.profit }.sum()
    val avgConfidence: Float
        get() = if (totalInvestments == 0) 0f else records.map { it.confidence }.average().toFloat()
    val avgConfidenceProfitable: Float
        get() = records.filter { it.profit > 0 }.let { list ->
            if (list.isEmpty()) 0f else list.map { it.confidence }.average().toFloat()
        }
    val avgConfidenceLoss: Float
        get() = records.filter { it.profit < 0 }.let { list ->
            if (list.isEmpty()) 0f else list.map { it.confidence }.average().toFloat()
        }
}