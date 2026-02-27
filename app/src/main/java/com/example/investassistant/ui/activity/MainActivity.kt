package com.example.investassistant.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.investassistant.ui.theme.InvestAssistantTheme

import androidx.compose.material3.Surface
import com.example.investassistant.ui.compose.InvestmentMainScreen

// MainActivity 是应用的主界面活动。当 MainActivity 被创建时，onCreate 方法会被调用。
// 在 onCreate 中，通过 setContent 方法设置了界面内容，采用 Jetpack Compose 实现 UI。
// MaterialTheme 用于应用主题色和样式，Surface 提供了填满整个屏幕的背景。
// 在 Surface 里面调用了 InvestmentMainScreen 组件，并传入 { finish() } 作为退出回调。
// 新增一个紫红色主题，用于替换 MaterialTheme。可以用于全局 Compose 主题。
// 示例
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color

private val PurpleRedLightColorScheme = lightColorScheme(
    primary = Color(0xFFD50060),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFC1E3),
    onPrimaryContainer = Color(0xFF3F001C),
    secondary = Color(0xFF8E24AA),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE1BEE7),
    onSecondaryContainer = Color(0xFF32004B),
    background = Color(0xFFFFF0F6),
    onBackground = Color(0xFF210011),
    surface = Color(0xFFFFF0F6),
    onSurface = Color(0xFF210011)
)

@Composable
fun PurpleRedTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = PurpleRedLightColorScheme,
        typography = MaterialTheme.typography,
        shapes = MaterialTheme.shapes,
        content = content
    )
}
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InvestmentMainScreen({ finish() })
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    InvestAssistantTheme {
        Greeting("Android")
    }
}