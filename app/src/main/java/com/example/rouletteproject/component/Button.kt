package com.example.rouletteproject.component

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddIconButton(
    modifier: Modifier,
    onclickAction: () -> Unit
){
    IconButton(
        modifier = modifier,
        onClick = {
            onclickAction.invoke()
        }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "add_list",
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = CircleShape
                )
        )
    }
}