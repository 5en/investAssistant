package com.example.investassistant.ui.data

import java.util.UUID

data class InvestmentRecord(
    val id: String = UUID.randomUUID().toString(),
    val amount: Float,          // 投资金额
    val confidence: Float,      // 决策信心度 (0-100)
    val note: String,           // 投资备注
    val profit: Float,          // 收益/亏损（正数盈利，负数亏损）
    val investDate: Long = System.currentTimeMillis(), // 投资时间
    val resultNote: String? = null, // 结果备注
    val closeDate: Long = System.currentTimeMillis(),  // 平仓时间
    val isRecordResult: Boolean = false // 是否已经记录投资结果
)