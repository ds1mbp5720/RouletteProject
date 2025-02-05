package com.example.rouletteproject.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * 리스트 index 별 표시 CardView
 */
@Composable
fun RouletteCard(
    modifier: Modifier = Modifier,
    updateEnable: Boolean = false,
    text: String,
    onUpdateList: (String) -> Unit = {},
    onDeleteClick: (String) -> Unit
) {
    val textState = remember { mutableStateOf(text) }
    textState.value = text // 해당 카드뷰 제거 동작시 리스트값은 갱신되나 state 부분의 remember로 인해 값 다시 초기화 동작
    Card(
        modifier = modifier
            .widthIn(min = 50.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp, top = 6.dp, bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            if(updateEnable) {
                BasicTextField(
                    value = textState.value,
                    onValueChange = { textValue ->
                        onUpdateList(textValue)
                        textState.value = textValue
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.titleMedium
                ) {innerTextField ->
                    innerTextField()
                }
            } else {
                Text(
                    text = textState.value,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier
                    .clickable {
                        onDeleteClick.invoke(textState.value)
                    },
                imageVector = Icons.Filled.Clear,
                contentDescription = "search_history_delete"
            )
        }
    }
}