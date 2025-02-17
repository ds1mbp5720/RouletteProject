package com.example.rouletteproject.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ThemeButton(modifier: Modifier = Modifier, onClick: () -> Unit, content: @Composable RowScope.() -> Unit) {
    Button(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ),
        onClick = {
            onClick.invoke()
        }) {
        content.invoke(this)
    }
}

@Composable
fun ThemeIconButton(modifier: Modifier, onClick: () -> Unit, content: @Composable () -> Unit) {
    IconButton(
        modifier = modifier,
        colors = IconButtonDefaults.iconButtonColors(
            contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = {
            onClick.invoke()
        }) {
        content.invoke()
    }
}

@Composable
fun AddIconButton(
    modifier: Modifier,
    onclickAction: () -> Unit
) {
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

@Composable
fun ButtonText(modifier: Modifier = Modifier, text: String, style: TextStyle = MaterialTheme.typography.titleLarge) {
    Text(
        modifier = modifier,
        text = text,
        style = style,
        textAlign = TextAlign.Center,
        color = Color.White
    )
}

/**
 * 결과 표시 textView
 */
@Composable
fun ResultTextView(
    modifier: Modifier,
    result: String
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Text(
            modifier = modifier
                .padding(horizontal = 24.dp, vertical = 6.dp),
            text = result,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.surface
        )
    }
}