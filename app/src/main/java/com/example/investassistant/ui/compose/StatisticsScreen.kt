package com.example.investassistant.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.investassistant.ui.theme.GREEN
import com.example.investassistant.ui.theme.RED
import com.example.investassistant.ui.viewmodel.InvestmentViewModel

@Composable
fun StatisticsScreen(
    viewModel: InvestmentViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(text = "统计分析", fontSize = 18.sp, fontWeight = FontWeight.Bold)

        // 收益统计卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                // 选项1：浅灰蓝（最通用，适配绝大多数安卓浅色界面）
                containerColor = Color(0xFFF5F7FA),
                // 选项2：浅米白（适配暖色调界面，如浅黄/米色底）
//             containerColor = Color(0xFFFCFBF7),
                // 选项3：极浅青（适配冷色调/偏蓝的界面）
//                containerColor = Color(0xFFF0F8F9)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "收益统计", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                StatItem(label = "总投资次数", value = viewModel.totalInvestments.toString())
                StatItem(
                    label = "盈利次数",
                    value = viewModel.profitableCount.toString(),
                    color = RED
                )
                StatItem(
                    label = "亏损次数",
                    value = viewModel.lossCount.toString(),
                    color = GREEN
                )
                StatItem(label = "胜率", value = String.format("%.1f%%", viewModel.winRate))
                StatItem(
                    label = "总收益",
                    value = "¥${viewModel.totalProfit.toInt()}",
                    color = if (viewModel.totalProfit > 0) RED else GREEN
                )
            }
        }

        // 信心度分析卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                // 选项1：浅灰蓝（最通用，适配绝大多数安卓浅色界面）
                containerColor = Color(0xFFF5F7FA),
                // 选项2：浅米白（适配暖色调界面，如浅黄/米色底）
//             containerColor = Color(0xFFFCFBF7),
                // 选项3：极浅青（适配冷色调/偏蓝的界面）
//                containerColor = Color(0xFFF0F8F9)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "信心度分析", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                StatItem(
                    label = "平均信心度",
                    value = String.format("%.1f%%", viewModel.avgConfidence)
                )
                StatItem(
                    label = "盈利时平均信心度",
                    value = String.format("%.1f%%", viewModel.avgConfidenceProfitable),
                    color = RED
                )
                StatItem(
                    label = "亏损时平均信心度",
                    value = String.format("%.1f%%", viewModel.avgConfidenceLoss),
                    color = GREEN
                )
                StatItem(label = "信心度准确率", value = "0%", color = Color.Gray)
            }
        }

        // 投资建议卡片
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            colors = CardDefaults.cardColors(
                // 选项1：浅灰蓝（最通用，适配绝大多数安卓浅色界面）
                containerColor = Color(0xFFF5F7FA),
                // 选项2：浅米白（适配暖色调界面，如浅黄/米色底）
//             containerColor = Color(0xFFFCFBF7),
                // 选项3：极浅青（适配冷色调/偏蓝的界面）
//                containerColor = Color(0xFFF0F8F9)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "必背投资误区:", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                Text(
                    text = " 1. 贪婪心理：止盈后看到行情延续，会产生“利润没吃满”的遗憾感，进而渴望抓住剩余趋势实现利润最大化，忽视了期货和股票市场趋势随时可能反转的风险，陷入“赚了还想赚”的贪婪误区。\n" +
                            "\n" +
                            "2. 锚定效应影响：将此前的盈利点位和趋势走势作为“锚点”，主观认定行情会按照原有轨迹继续，忽略了市场供需、资金面等因素的变化，对趋势拐点缺乏警惕。\n" +
                            "\n" +
                            "3. 侥幸与认知偏差：把趋势延续的偶然性当成必然性，误以为“追进去还能赚”，同时高估自己对趋势的判断能力，低估追高/追涨的风险，陷入“这次不一样”的侥幸心理。\n" +
                            "\n" +
                            "4. 交易规则缺失：没有明确的二次入场标准，止盈后仅凭情绪和视觉上的趋势延续就决策，导致交易行为脱离理性框架。\n" +
                            "\n" +
                            "防治方法\n" +
                            "\n" +
                            "1. 建立刚性的二次入场规则\n" +
                            "\n" +
                            "◦ 止盈后若想再次入场，必须重新满足预设的交易信号（如期货需突破关键阻力位且成交量放大，股票需站稳均线并出现量价齐升形态），无信号坚决不追单。\n" +
                            "\n" +
                            "◦ 设定追单仓位限制，追单仓位不得超过首次入场仓位的50%，且总仓位不超过账户资金的30%，降低风险敞口。\n" +
                            "\n" +
                            "2. 接受“盈利不完美”的现实\n" +
                            "\n" +
                            "◦ 明确期货和股票市场中“吃满所有行情”是不可能的，止盈本身就是对已有利润的锁定，将“部分盈利”视为成功交易，而非“错过的利润”。\n" +
                            "\n" +
                            "◦ 可以在交易笔记中记录“止盈后未追单的盈利案例”，强化“见好就收”的正向记忆。\n" +
                            "\n" +
                            "3. 设置冷静期与复盘机制\n" +
                            "\n" +
                            "◦ 止盈后强制设置30分钟-1小时的冷静期，期间不做任何交易决策，待情绪平复后再客观分析行情是否具备二次入场条件。\n" +
                            "\n" +
                            "◦ 每次追单亏损后，详细记录行情走势、入场原因、亏损点，总结共性问题（如追在趋势末端、无确认信号入场等），形成避坑清单。\n" +
                            "\n" +
                            "4. 转移注意力与心理暗示\n" +
                            "\n" +
                            "◦ 止盈后可暂时离开交易软件，参与其他非交易活动（如运动、阅读），避免持续盯盘放大“错过行情”的焦虑感。\n" +
                            "\n" +
                            "◦ 反复给自己心理暗示：“落袋为安的利润才是真实的，追单的风险远大于潜在收益”，强化理性交易的思维。\n"
                )
                Text(
                    text = "普通投资者害怕空仓、不愿拿着钱等待，核心是心理认知偏差+市场环境诱导的双重作用，具体可以归结为这几点：\n" +
                            "\n" +
                            "1. 错失恐惧症（FOMO）主导决策：看到市场偶尔的反弹、板块轮动或者别人赚钱的消息，会下意识觉得“空仓就是错过盈利机会”，反而忽略了“持仓可能亏损”的风险，把“不赚”等同于“亏损”。\n" +
                            "\n" +
                            "2. 机会成本的错觉放大：容易过度关注“钱没投出去的潜在收益损失”，却低估了持仓时的波动风险和实际亏损概率，比如拿着现金的“无风险收益”（如货币基金）被忽略，只盯着股票/期货的潜在涨幅。\n" +
                            "\n" +
                            "3. 市场噪音的持续干扰：财经媒体、社交平台天天推送“热点标的”“抄底机会”，营造出“市场永远有机会”的氛围，让空仓的等待变得焦虑，总担心“踏空一波大行情”。\n" +
                            "\n" +
                            "4. 对“确定性”的心理依赖：持仓时哪怕标的下跌，也会觉得“有资产在手”，心理上有个“抓手”；而空仓要面对“什么时候入场”的未知，这种不确定性比暂时的亏损更让人不安。\n" +
                            "\n" +
                            "5. 过往踏空/亏损的补偿心理：如果之前有过踏空大涨行情，或者持仓亏损后急于回本，会形成“不能再空仓”的惯性，怕再次错过翻身机会，反而陷入“越怕空仓越乱操作”的循环。"
                )
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