package com.example.rouletteproject.component.card

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rouletteproject.R

@Composable
fun CardItem(
    modifier: Modifier = Modifier,
    item: CardStateItem
) {
    var cardReverse by remember { mutableStateOf(true) }
    val rotation by animateFloatAsState(
        targetValue = if (cardReverse) 180f else 0f,
        animationSpec = tween(500),
        label = "rotation"
    )
    cardReverse = item.isRotated
    Card(
        modifier = modifier
            .size(width = 120.dp, height = 210.dp)
            .padding(8.dp)
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 8f
            }
            .clickable {
                cardReverse = !cardReverse
                item.isSelected = !item.isSelected
                item.isRotated = !item.isRotated
            },
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (item.isSelected) Color.Yellow
            else Color.Black
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (rotation <= 90f) {
                Text(
                    text = item.text,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
            } else {
                Icon(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center),
                    painter = painterResource(id = R.drawable.baseline_question_mark_24),
                    tint = Color.Black,
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
            item = CardStateItem(text = "TEST Card")
        )

        CardItem(
            modifier = Modifier
                .width(120.dp)
                .height(210.dp)
                .padding(8.dp),
            item = CardStateItem(text = "TEST Card", isRotated = true)
        )
    }

}