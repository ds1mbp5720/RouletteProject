package com.example.rouletteproject.managelist

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.component.RouletteCard

/**
 * 룰렛 내부 리스트 관리 화면
 * 내부 저장된 룰렛 내용 리스트 관리
 */
@Composable
fun ManageListScreen(
    mainViewModel: MainViewModel
) {
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    Column(
        modifier = Modifier
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = {
                mainViewModel.insert(
                    RouletteEntity(
                        id = System.currentTimeMillis(),
                        title = "test",
                        rouletteData = listOf("a","b","c","d","f","g","h","i")
                    )
                )
            }) {
            Text(text = "test roomDB Add")
        }
        Spacer(modifier = Modifier.height(20.dp))
        rouletteLists?.forEach {rouletteData ->
            RouletteItemBox(
                title = rouletteData.title,
                rouletteList = rouletteData.rouletteData,
                itemDeleteClick = { removeString ->
                    // 룰렛 구성 string 중 선택한 it 지우는 동작
                    val updateList = mutableListOf<String>()
                    updateList.addAll(rouletteData.rouletteData)
                    updateList.remove(removeString)
                    mainViewModel.update(
                        RouletteEntity(
                            id = rouletteData.id,
                            title = rouletteData.title,
                            rouletteData = updateList
                        )
                    )
                }
            ) {
                mainViewModel.delete(rouletteData.id)
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RouletteItemBox(
    modifier: Modifier = Modifier,
    title: String,
    rouletteList: List<String>,
    itemDeleteClick: (String) -> Unit,
    rouletteDeleteClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 15.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.TopCenter),
                text = title
            )
            Icon(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .clickable {
                        rouletteDeleteClick.invoke()
                    },
                imageVector = Icons.Filled.Clear,
                contentDescription = "search_history_delete"
            )
        }
        Spacer(modifier = Modifier.height(15.dp))
        FlowRow(
            modifier = modifier
                .padding(horizontal = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            repeat(rouletteList.size) { index ->
                RouletteCard(
                    updateEnable = true,
                    text = rouletteList[index],
                    onDeleteClick = {
                        itemDeleteClick.invoke(it)
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreViewItemBox() {
    RouletteItemBox(
        modifier = Modifier,
        title = "Test Title",
        rouletteList = listOf("a","b","c","d","f","g","h","i"),
        itemDeleteClick = { }
    ) { }
}