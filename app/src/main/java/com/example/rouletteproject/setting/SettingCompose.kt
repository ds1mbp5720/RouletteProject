package com.example.rouletteproject.setting

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rouletteproject.R
import com.example.rouletteproject.dialog.BasicDivider
import com.example.rouletteproject.navigation.MainDestination

@Composable
fun SettingScreen(
    // mainViewModel: MainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        SettingCategory(title = stringResource(id = R.string.text_setting_title)) {
            RadioGroupButton(
                title = stringResource(id = R.string.text_setting_first_screen),
                radioList = listOf("룰렛", "카드", "사다리"),
                selected = "룰렛",
            ) {

            }
        }
        SettingCategory(title = stringResource(id = R.string.text_setting_roulette)) {
            SwitchButton(
                title = stringResource(id = R.string.text_setting_roulette_drag),
                unCheckedText = stringResource(id = R.string.text_deactivate),
                checkedText = stringResource(id = R.string.text_activate),
                checked = true
            ) {

            }
        }


        SettingCategory(title = stringResource(id = R.string.text_setting_card)) {
            SwitchButton(
                title = stringResource(id = R.string.text_setting_card_reverse),
                unCheckedText = stringResource(id = R.string.text_deactivate),
                checkedText = stringResource(id = R.string.text_activate),
                checked = true
            ) {

            }
        }


        SettingCategory(title = stringResource(id = R.string.text_setting_ladder)) {
            InputIntTextField(
                title = stringResource(id = R.string.text_setting_ladder_move_time),
                default = 0,
                maxInt = 10,
            ) {

            }
            InputIntTextField(
                title = stringResource(id = R.string.text_setting_ladder_row_count),
                default = 1,
                maxInt = 10,
                minimumInt = 1
            ) {

            }
        }
    }
}

// 항목별 묶음단위
@Composable
fun SettingCategory(
    title: String, content: @Composable () -> Unit
) {
    BasicDivider(paddingVertical = 0, color = Color.Gray)
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 1.dp, horizontal = 6.dp),
            text = title,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp)
    ) {
        content.invoke()
    }
    Spacer(modifier = Modifier.height(30.dp))
}

@Composable
fun RadioGroupButton(title: String, radioList: List<String>, selected: String, onClick: () -> Unit) {
    Column {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            modifier = Modifier
                .padding(0.dp),
        ) {
            radioList.forEach {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = it == selected,
                        onClick = { onClick.invoke() },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = Color.Cyan,
                            unselectedColor = Color.Gray
                        )
                    )
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun SwitchButton(title: String, unCheckedText: String = "", checkedText: String = "", checked: Boolean, onCheckAction: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 50.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = unCheckedText,
                style = MaterialTheme.typography.bodyMedium
            )
            Switch(
                checked = checked,
                onCheckedChange = { checked ->
                    onCheckAction.invoke(checked)
                },
                colors = SwitchDefaults.colors(
                    checkedTrackColor = Color.Cyan
                )
            )
            Text(
                text = checkedText,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun InputIntTextField(title: String, default: Int, maxInt: Int, minimumInt: Int = 0, inputAction: (Int) -> Unit) {
    val count = remember { mutableIntStateOf(default) }
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, end = 100.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium
        )
        Card(
            modifier = Modifier,
            shape = RectangleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = BorderStroke(
                width = 1.dp,
                color = Color.Black
            )
        ) {
            Row {
                BasicTextField(
                    modifier = Modifier
                        .width(40.dp)
                        .align(Alignment.CenterVertically)
                        .padding(horizontal = 6.dp, vertical = 4.dp),
                    readOnly = true,
                    value = count.intValue.toString(),
                    onValueChange = { result ->
                        inputAction.invoke(result.toInt())
                    },
                    singleLine = true
                )
                Column {
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = {
                            if (count.intValue + 1 <= maxInt) {
                                count.intValue += 1
                            } else {
                                Toast.makeText(context, context.getString(R.string.text_warning_up), Toast.LENGTH_SHORT).show()
                            }
                        }) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.KeyboardArrowUp,
                            contentDescription = "up"
                        )
                    }
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = {
                            if (count.intValue - 1 >= minimumInt) {
                                count.intValue -= 1
                            }
                        }) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            imageVector = Icons.Filled.KeyboardArrowDown,
                            contentDescription = "down"
                        )
                    }
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun SettingPreview() {
    SettingScreen()
}