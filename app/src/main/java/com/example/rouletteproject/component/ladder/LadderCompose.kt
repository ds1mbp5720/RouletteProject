package com.example.rouletteproject.component.ladder

import android.widget.Toast
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.R
import com.example.rouletteproject.component.ButtonText
import com.example.rouletteproject.component.ResultTextView
import com.example.rouletteproject.component.ThemeButton
import com.example.rouletteproject.setting.SettingDataStore
import kotlin.random.Random

@Composable
fun LadderScreen(
    mainViewModel: MainViewModel,
    selectedList: RouletteEntity?
) {
    val context = LocalContext.current
    val numVerticalLines = selectedList?.rouletteData?.size ?: 0 // 세로선 개수
    val numHorizontalLines = SettingDataStore.getLadderRowCount()
    var selectedStartPoint by remember { mutableStateOf<Int?>(null) } // 선택된 시작점
    var currentPoint by remember { mutableStateOf<Int?>(null) } // 현재 위치
    var isGameStarted by remember { mutableStateOf(false) } // 게임 시작 여부
    var isGameFinished by remember { mutableStateOf(false) } // 게임 종료 여부
    var result by remember { mutableStateOf<Int?>(null) } // 결과
    var isGameReset by remember { mutableStateOf(false) } // 게임 리셋 여부
    val delayTime = SettingDataStore.getLadderMoveTime() * 1000
    fun resetSetting() {
        isGameStarted = false
        isGameFinished = false
        selectedStartPoint = null
        currentPoint = null
        result = null
        isGameReset = !isGameReset
    }
    // 사다리 생성
    val ladder = remember { mutableStateListOf<Pair<Int, Int>>() }
    LaunchedEffect(key1 = selectedList) {
        resetSetting()
    }
    LaunchedEffect(isGameReset) {
        ladder.clear()
        for (i in 0 until numHorizontalLines) { //6 줄의 가로줄 -> 시작~끝 ex) 1-3까지, 2-3까지 등
            val start = Random.nextInt(numVerticalLines) // 0 ~ numVerticalLines - 1
            val end = Random.nextInt(2)
            ladder.add(
                Pair(
                    first = start,
                    second = when (end) {
                        0 -> { // +1
                            if (start == numVerticalLines - 1) start - 1
                            else start + 1
                        }

                        else -> { // -1
                            if (start == 0) 1
                            else start - 1
                        }
                    }
                )
            )
        }
    }
    fun RowScope.borderItemAsCondition(condition: Boolean): Modifier {
        return Modifier
            .weight(1f)
            .size(40.dp)
            .padding(horizontal = 4.dp)
            .border(
                width = 2.dp,
                color = if (condition) Color.Green
                else Color.Black,
                shape = RoundedCornerShape(16.dp)
            )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally)
        ) {
            // 게임 시작 버튼
            ThemeButton( //all reverse button
                modifier = Modifier,
                onClick = {
                    if (selectedStartPoint != null) {
                        isGameStarted = true
                        currentPoint = selectedStartPoint
                        isGameFinished = false
                        result = null
                        // 게임 로직 실행
                        var current = selectedStartPoint!!
                        for (i in 0 until numHorizontalLines) { //가로선 갯수 만큼 진행
                            var horizontalLine: Pair<Int, Int>? = null
                            if (ladder[i].first == current || ladder[i].second == current) { // 가로선 이동 있는지 체크
                                horizontalLine = ladder[i]
                            }
                            if (horizontalLine != null) { // 가로선 로직
                                current = if (horizontalLine.first == current) {
                                    horizontalLine.second
                                } else {
                                    horizontalLine.first
                                }
                            }
                        }
                        result = current
                    } else {
                        Toast.makeText(context, context.getString(R.string.text_none_select_item), Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                ButtonText(text = stringResource(id = R.string.text_start))
            }
            // 게임 재시작 버튼
            ThemeButton(
                modifier = Modifier,
                onClick = {
                    resetSetting()
                }
            ) {
                ButtonText(text = stringResource(id = R.string.text_reset))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // 시작점 선택
        Row(modifier = Modifier.fillMaxWidth()) {
            selectedList?.rouletteData?.forEachIndexed { index, _ ->
                Box(
                    modifier = this
                        .borderItemAsCondition(selectedStartPoint == index)
                        .clickable {
                            if (!isGameStarted) {
                                selectedStartPoint = index
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "${index + 1}")
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            LadderCanvas(
                numVerticalLines = numVerticalLines,
                numHorizontalLines = numHorizontalLines,
                ladder = ladder,
                isGameStarted = isGameStarted,
                currentPoint = currentPoint,
                selectedStartPoint = selectedStartPoint,
                delayTime = delayTime
            ) {
                isGameFinished = true
            }
            CardFlip(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp),
                isCardFlipped = isGameStarted
            )
        }

        // 도착지 표시
        Row(modifier = Modifier.fillMaxWidth()) {
            selectedList?.rouletteData?.forEachIndexed { index, _ ->
                Box(
                    modifier = this.borderItemAsCondition(result == index && isGameFinished),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = selectedList.rouletteData[index])
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // 게임 결과
        if (isGameFinished && result != null) {
            ResultTextView(
                modifier = Modifier,
                result = stringResource(
                    id = R.string.text_result,
                    selectedList?.rouletteData?.get(result!!) ?: ""
                )
            )
        }
    }
}

@Composable
fun LadderCanvas(
    numVerticalLines: Int,
    numHorizontalLines: Int,
    ladder: List<Pair<Int, Int>>,
    isGameStarted: Boolean,
    currentPoint: Int?,
    selectedStartPoint: Int?,
    delayTime: Int,
    finishEvent: () -> Unit
) {
    var lineWidth by remember { mutableFloatStateOf(0f) }
    var lineHeight by remember { mutableFloatStateOf(0f) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .align(Alignment.TopCenter)
        ) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val canvasWidthPadding = canvasWidth / (numVerticalLines * 2)
            lineWidth = canvasWidthPadding * 2 // 세로선 간격
            lineHeight = canvasHeight / (numHorizontalLines + 1) // 가로선 간격
            // 세로선 그리기
            for (i in 0 until numVerticalLines) {
                val x = (i * 2 + 1) * (lineWidth / 2)
                drawLine(
                    color = Color.Black,
                    start = Offset(x, 0f),
                    end = Offset(x, canvasHeight),
                    strokeWidth = 5f,
                    cap = StrokeCap.Round
                )
            }
            // 가로선 그리기
            var ladderNum = 1
            for (line in ladder) {
                val startX = line.first * lineWidth + canvasWidthPadding
                val endX = line.second * lineWidth + canvasWidthPadding
                val y = ladderNum * lineHeight
                ladderNum += 1
                drawLine(
                    color = Color.Black,
                    start = Offset(startX, y),
                    end = Offset(endX, y),
                    strokeWidth = 5f,
                    cap = StrokeCap.Round
                )
            }
        }
        if (isGameStarted && selectedStartPoint != null && currentPoint != null) {
            AnimationPath(
                path = Path().apply {
                    val startX = selectedStartPoint * lineWidth + (lineWidth / 2) //선택한 좌표 시작점 (ex: 3번 선택시 3*간격)
                    val startY = 0f //0f
                    this.moveTo(startX, startY) // 선택 위치 에서 시작점 세팅
                    this.lineTo(startX, lineHeight) //최초 다음줄 까지 이동 선 생성
                    //이동시 마다 그릴 선 좌표
                    var currentX = startX
                    var currentY = lineHeight
                    var current = selectedStartPoint

                    for (i in 0 until numHorizontalLines) { //가로선 갯수 만큼 진행
                        var horizontalLine: Pair<Int, Int>? = null
                        if (ladder[i].first == current || ladder[i].second == current) {// 가로선 이동 할거 있는지 체크
                            horizontalLine = ladder[i]
                        }
                        if (horizontalLine != null) { // 가로선 로직
                            val nextX = if (horizontalLine.first == current) {
                                horizontalLine.second * lineWidth + (lineWidth / 2)
                            } else {
                                horizontalLine.first * lineWidth + (lineWidth / 2)
                            }  // nextX : current 부터 이동할 x 좌표
                            val nextY = currentY + lineHeight // 가로선 이후 다음 세로 좌표
                            this.lineTo(nextX, currentY)
                            this.lineTo(nextX, nextY)
                            currentX = nextX
                            currentY = nextY
                            current = if (horizontalLine.first == current) {
                                horizontalLine.second
                            } else {
                                horizontalLine.first
                            }
                        } else { // 가로선 없을 경우 세로선 로직
                            val nextY = currentY + lineHeight
                            this.lineTo(currentX, nextY)
                            currentY = nextY
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .align(Alignment.TopCenter),
                delayTime = delayTime
            ) {
                finishEvent.invoke()
            }
        }
    }
}

/**
 * 경로에 맞춰 그려줄 animation composable function
 * path: apply 통한 선들 정보가 담긴 Path
 */
@Composable
fun AnimationPath(
    path: Path,
    modifier: Modifier,
    delayTime: Int = 5000,
    finishEvent: () -> Unit
) {
    val animatedProgress = remember { Animatable(0f) }
    val pathMeasure = remember { PathMeasure() }
    val partialPath = Path() // 그리는 중의 경로 path
    var onceFinishEvent = true
    pathMeasure.setPath(path, false)
    LaunchedEffect(key1 = path) {
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = delayTime) //시간 지정
        ) {
            if (animatedProgress.value == 1.0f && onceFinishEvent) {
                onceFinishEvent = false
                finishEvent.invoke()
            }
        }
    }
    Canvas(modifier = modifier) {
        val pathLength = pathMeasure.length
        val currentLength = pathLength * animatedProgress.value
        pathMeasure.getSegment(0f, currentLength, partialPath, true)
        drawPath(
            path = partialPath,
            color = Color.Red,
            style = Stroke(
                width = 7f,
                cap = StrokeCap.Round
            )
        )
    }
}

@Composable
fun CardFlip(modifier: Modifier, isCardFlipped: Boolean) {
    val animationDuration = 700
    val flipAnimation by animateFloatAsState(
        targetValue = if (isCardFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = animationDuration),
        label = "cardFlip"
    )

    val alpha by animateFloatAsState(
        targetValue = if (isCardFlipped) 0f else 1f,
        animationSpec = tween(durationMillis = animationDuration),
        label = "cardAlpha"
    )
    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = flipAnimation
                this.alpha = alpha
            }
            .clickable {

            },
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFFFCFC95)
            )
        ) {
        }
    }
}