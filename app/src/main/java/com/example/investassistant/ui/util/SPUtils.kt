package com.example.investassistant.ui.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.investassistant.ui.data.InvestmentRecord
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * SP工具类：处理投资记录的持久化
 */
class SPUtils {
    companion object {
        // SP文件名
        private const val SP_NAME = "InvestmentRecordsSP"
        // 待处理记录的SP Key
        private const val KEY_PENDING = "pending_invest_records"
        // 已完成记录的SP Key
        private const val KEY_COMPLETED = "completed_invest_records"

        private lateinit var sp: SharedPreferences
        private val gson = Gson() // Gson实例，用于序列化/反序列化

        /**
         * 初始化SP（建议在Application中调用）
         */
        fun init(context: Context) {
            sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        }

        // ========== 待处理记录 ==========
        /**
         * 保存待处理记录到SP
         */
        fun savePendingRecords(records: List<InvestmentRecord>) {
            val json = gson.toJson(records)
            sp.edit().putString(KEY_PENDING, json).apply() // apply异步保存，不阻塞主线程
        }

        /**
         * 从SP读取待处理记录
         */
        fun getPendingRecords(): List<InvestmentRecord> {
            val json = sp.getString(KEY_PENDING, "")
            val type = object : TypeToken<List<InvestmentRecord>>() {}.type
            return if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, type)
        }

        // ========== 已完成记录 ==========
        /**
         * 保存已完成记录到SP
         */
        fun saveCompletedRecords(records: List<InvestmentRecord>) {
            val json = gson.toJson(records)
            sp.edit().putString(KEY_COMPLETED, json).apply()
        }

        /**
         * 从SP读取已完成记录
         */
        fun getCompletedRecords(): List<InvestmentRecord> {
            val json = sp.getString(KEY_COMPLETED, "")
            val type = object : TypeToken<List<InvestmentRecord>>() {}.type
            return if (json.isNullOrEmpty()) emptyList() else gson.fromJson(json, type)
        }
    }
}