package com.example.rouletteproject.managelist

import android.util.Log
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel

/**
 * 룰렛 내부 리스트 관리 화면
 * 내부 저장된 룰렛 내용 리스트 관리
 */
@Composable
fun ManageListScreen(
    mainViewModel: MainViewModel,
    rouletteList: List<String> // todo viewModel로 받아서 flow 감지하기
) {
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    Button(
        onClick = {
            mainViewModel.insert(
                RouletteEntity(
                    id = (720..1080).random(),
                    title = "test",
                    rouletteData = listOf("a","b","c","d","f","g","h","i")
                )
            )
            Log.e("","mainViewModel 길이 체크 ${rouletteLists?.size}")
        }) {
        Text(text = "test roomDB Add")
    }
}