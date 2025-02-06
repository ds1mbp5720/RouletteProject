package com.example.rouletteproject.component.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.rouletteproject.component.RouletteItem
import com.example.rouletteproject.dialog.BasicDivider

/**
 * 카드 랜덤 선택 화면
 */
@Composable
fun CardScreen(
    mainViewModel: MainViewModel
) {
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    var selectRoulette: RouletteEntity? by remember { mutableStateOf(rouletteLists?.get(0)) }
    val shuffledCardStateList = remember { mutableStateListOf<CardStateItem>() }
    var allCardReverse by remember { mutableStateOf(true) } // false: 전체 앞면, true: 전체 뒷면

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (rouletteLists?.isNotEmpty() == true) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                items(rouletteLists) { select ->
                    RouletteItem(
                        modifier = Modifier
                            .clickable {
                                selectRoulette = select
                                shuffledCardStateList.clear()
                            },
                        text = select.title,
                    ) {}
                }
            }
        } else {
            Text(
                text = stringResource(id = R.string.text_none_list)
            )
        }
        BasicDivider()
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
        ) {
            Button( //all reverse button
                modifier = Modifier,
                onClick = {
                    allCardReverse = false
                }) {
                Text(text = stringResource(id = R.string.text_reverse))
            }
            Button( // shuffle button
                modifier = Modifier,
                onClick = {
                    allCardReverse = true
                    shuffledCardStateList.shuffle().run {
                        shuffledCardStateList.forEach { item ->
                            item.isRotated = allCardReverse
                            item.isSelected = false
                        }
                    }
                }) {
                Text(text = stringResource(id = R.string.text_shuffle))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        selectRoulette?.let {
            if (shuffledCardStateList.isEmpty()) {
                it.rouletteData.shuffled().forEach { item ->
                    shuffledCardStateList.add(
                        CardStateItem( text = item, isRotated = true, isSelected = false )
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
        columns = GridCells.Fixed(3),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(horizontal = 5.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
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