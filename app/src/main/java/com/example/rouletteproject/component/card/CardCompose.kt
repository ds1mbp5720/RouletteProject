package com.example.rouletteproject.component.card

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.R
import com.example.rouletteproject.component.ButtonText
import com.example.rouletteproject.component.ThemeButton
import kotlinx.coroutines.delay

/**
 * 카드 랜덤 선택 화면
 */
@Composable
fun CardScreen(
    mainViewModel: MainViewModel,
    selectedList: RouletteEntity?
) {
    val shuffledCardStateList = remember { mutableStateListOf<CardStateItem>() }
    var allCardReverse by remember { mutableStateOf(true) } // false: 전체 앞면, true: 전체 뒷면
    LaunchedEffect(key1 = selectedList) {
        shuffledCardStateList.clear()
    }
    LaunchedEffect(key1 = allCardReverse) {
        // 전체 뒷면에 따른 값 변경
        // 섞을 경우 변경된 리스트가 잠깐 노출되어 뒤집히고 리스트 변경되도록 delay 추가
        delay(500)
        if (allCardReverse) {
            shuffledCardStateList.shuffle().run {
                shuffledCardStateList.forEach { item ->
                    item.isRotated = allCardReverse
                    item.isSelected = false
                }
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
        ) {
            ThemeButton( //all reverse button
                modifier = Modifier,
                onClick = {
                    allCardReverse = false
                }
            ) {
                ButtonText(text = stringResource(id = R.string.text_reverse))
            }
            ThemeButton( // shuffle button
                modifier = Modifier,
                onClick = {
                    allCardReverse = true
                }
            ) {
                ButtonText(text = stringResource(id = R.string.text_shuffle))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        selectedList?.let {
            if (shuffledCardStateList.isEmpty()) {
                it.rouletteData.shuffled().forEach { item ->
                    shuffledCardStateList.add(
                        CardStateItem(text = item, isRotated = true, isSelected = false)
                    )
                }
            } else {
                shuffledCardStateList.forEach { item ->
                    item.isRotated = allCardReverse
                }
            }
            CardList(
                modifier = Modifier,
                cardItems = shuffledCardStateList,
            )
        }
    }
}

@Composable
fun CardList(
    modifier: Modifier = Modifier,
    cardItems: List<CardStateItem>
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(
            if (cardItems.size % 2 == 0 && cardItems.size < 5) 2
            else 3
        ),
        modifier = modifier
            .width(
                if (cardItems.size % 2 == 0 && cardItems.size < 5) 245.dp
                else 370.dp
            )
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.Center, verticalArrangement = Arrangement.Center
    ) {
        items(cardItems) { cardItem ->
            CardItem(
                item = cardItem,
                modifier = Modifier
            )

        }
    }
}

@Preview
@Composable
fun CardListScreenPreview() {
    CardList(
        cardItems = listOf(CardStateItem("1"), CardStateItem("2"))
    )
}