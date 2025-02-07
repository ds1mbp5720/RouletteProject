package com.example.rouletteproject.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

/**
 * top bar 중심 메인 선택 리스트 표시
 * 클릭시 전체 리스트 선택 dialog 형식으로 노출
 */
@Composable
fun ChoiceView(
    title: String,
    modifier: Modifier = Modifier,
    clickAction: () -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable { clickAction.invoke() },
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(6.dp),
                text = title,
                maxLines = 1,
                textDecoration = TextDecoration.Underline,
                style = MaterialTheme.typography.titleLarge
            )
            Icon(
                modifier = Modifier.padding(end = 3.dp),
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "show_all_list")
        }
    }
}

@Preview
@Composable
fun PreViewChoiceView() {
    ChoiceView("테스트 리스트"){}
}