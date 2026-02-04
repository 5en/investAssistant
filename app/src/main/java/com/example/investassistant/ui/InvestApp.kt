package com.example.investassistant.ui

import android.app.Application
import com.example.investassistant.ui.utils.SPUtils

/**
 * 自定义Application，初始化SP工具类
 */
class InvestApp : Application() {
    override fun onCreate() {
        super.onCreate()
        SPUtils.init(this) // 全局初始化SP
    }
}