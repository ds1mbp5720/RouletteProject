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
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    val reverseState = remember { mutableStateOf(true) } // true: 보이기, false: 뒤집기
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    var selectRoulette: RouletteEntity? by remember { mutableStateOf(rouletteLists?.get(0)) }
    val shuffledList = remember { mutableStateListOf<String>() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
    ) {
        if (rouletteLists?.isNotEmpty() == true) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                items(rouletteLists) { roulette ->
                    RouletteItem(
                        modifier = Modifier
                            .clickable {
                                selectRoulette = roulette
                                shuffledList.clear()
                            },
                        text = roulette.title,
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
            horizontalArrangement = Arrangement.Center
        ) {
            Button( // reverse button
                modifier = Modifier,
                onClick = {
                    reverseState.value = !reverseState.value
                }) {
                Text(text = stringResource(id = R.string.text_reverse))
            }
            Button( // shuffle button
                modifier = Modifier,
                onClick = {
                    shuffledList.shuffle()
                }) {
                Text(text = stringResource(id = R.string.text_shuffle))
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
        selectRoulette?.let {
            if (shuffledList.isEmpty()) {
                shuffledList.clear()
                shuffledList.addAll(it.rouletteData.shuffled())
            }
            CardListScreen(
                cardItems = shuffledList,
                reverseState = reverseState.value
            )
        }

    }
}

@Composable
fun CardListScreen(
    modifier: Modifier = Modifier,
    cardItems: List<String>,
    reverseState: Boolean = false
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.Center,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(cardItems) { cardItem ->
                CardItem(
                    text = cardItem,
                    modifier = Modifier,
                    reversed = reverseState
                )

            }
        }
    }
}