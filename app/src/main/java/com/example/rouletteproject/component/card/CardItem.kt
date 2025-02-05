package com.example.rouletteproject.component.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    text: String,
    reversed: Boolean
) {
    var selected by remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .size(width = 120.dp, height = 210.dp)
            .padding(8.dp)
            .clickable {
                selected = !selected
            }
            .background(
                color = if (selected) Color.Yellow
                else Color.LightGray
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (selected) Color.Yellow
            else Color.Black
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (reversed) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            } else {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                    imageVector = Icons.Filled.Star,
                    contentDescription = "reverse card icon"
                )
            }
        }
    }
}

@Composable
@Preview
fun CardItemPreview() {
    Column {
        CardItem(
            modifier = Modifier
                .width(120.dp)
                .height(210.dp)
                .padding(8.dp),
            text = "TEST Card",
            reversed = true
        )

        CardItem(
            modifier = Modifier
                .width(120.dp)
                .height(210.dp)
                .padding(8.dp),
            text = "TEST Card",
            reversed = false
        )
    }

}