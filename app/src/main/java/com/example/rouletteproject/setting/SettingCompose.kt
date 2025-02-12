package com.example.rouletteproject.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.dialog.BasicDivider

@Composable
fun SettingScreen(
    mainViewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        SettingCategory(title = "앱 설정") {
            RadioGroupButton(
                title = "",
                radioList = listOf("룰렛","카드","사다리"),
                selected = "룰렛",
            ) {

            }
        }

        SettingCategory(title = "룰렛") {
            SwitchButton(
                title = "드래그 회전 기능",
                unCheckedText = "비활성화",
                checkedText = "활성화",
                checked = true
            ) {

            }
        }


        SettingCategory(title = "카드") {
            SwitchButton(
                title = "선택 카드 뒤집기",
                unCheckedText = "비활성화",
                checkedText = "활성화",
                checked = true
            ) {

            }
        }


        SettingCategory(title = "사다리") {
            InputIntTextField(
                title = "이동시간",
                default = "0",
                maxInt = 10
            ) {

            }
            InputIntTextField(
                title = "가로선 갯수",
                default = "0",
                maxInt = 10
            ) {

            }
        }
    }
}

@Composable
fun SettingCategory(title: String, content: @Composable () -> Unit
) {
    Text(
        modifier = Modifier,
        text = title,
        textAlign = TextAlign.Center,
        style = TextStyle(
          fontStyle = FontStyle.Italic
        )
    )
    Column {
        content.invoke()
    }
    BasicDivider(paddingVertical = 2)
}

@Composable
fun SwitchButton(title: String, unCheckedText: String = "", checkedText: String = "", checked: Boolean, onCheckAction: (Boolean) -> Unit) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontStyle = FontStyle.Italic
        )
        
        Spacer(modifier = Modifier.width(6.dp))
        Text(text = unCheckedText)
        Switch(
            checked = checked,
            onCheckedChange = { checked ->
                onCheckAction.invoke(checked)
            }
        )
        Text(text = checkedText)
    }
}

@Composable
fun RadioGroupButton(title: String, radioList: List<String>, selected: String, onClick: ()-> Unit) {
    Column {
        Text(text = title)
        radioList.forEach {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = it)
                RadioButton(
                    selected = it == selected ,
                    onClick = { onClick.invoke() }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputIntTextField(title: String, default: String, maxInt: Int, inputAction: (String) -> Unit) {
    val inputText = remember { mutableStateOf(default) }
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title)
        TextField(
            value = inputText.value,
            onValueChange = { result ->
                inputAction.invoke(result)
                inputText.value = result
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
        )
    }
}