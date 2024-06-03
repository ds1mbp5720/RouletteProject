package com.example.rouletteproject.component.roulette

import android.util.Log
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.R
import com.example.rouletteproject.component.RouletteCard
import kotlin.math.cos
import kotlin.math.sin

private val topPadding = 20.dp
private val iconSize = 80.dp
private val rouletteSize = 400.dp

/**
 * 룰렛 화면
 */
@Composable
fun RouletteScreen(
    mainViewModel: MainViewModel,
    rouletteList: List<String> // todo viewModel로 받아서 flow 감지하기
) {
    val rouletteLists = mainViewModel.rouletteList.observeAsState().value
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var resultPosition by remember { mutableIntStateOf(0) }
        var selectRoulette: RouletteEntity? by remember { mutableStateOf(rouletteLists?.get(0)) }  // todo 0 대신 마지막 선택한 index 활용
        // todo add googleAdMob
        Spacer(modifier = Modifier.height(25.dp))
        ResultTextView(
            modifier = Modifier,
            result = stringResource(
                id = R.string.text_result,
                if (selectRoulette != null) {
                    selectRoulette!!.rouletteData[resultPosition]
                } else {
                    "선택한 리스트가 없습니다."
                }
            )
        )
        Spacer(modifier = Modifier.height(35.dp))
        BasicRoulette(
            modifier = Modifier,
            rouletteList = rouletteList,
            selectDegree = 270,
        ) {
            resultPosition = it
        }
        Spacer(modifier = Modifier.height(30.dp))
        if (rouletteLists?.isNotEmpty() == true) {
            rouletteLists.forEach { rouletteData ->
                RouletteCard(
                    modifier = Modifier
                        .clickable {
                            selectRoulette = rouletteData
                        },
                    text = rouletteData.title,
                ) { //todo 해당 부분은 수정 다이얼로그 방식으로 변경하기

                }
            }
        } else {
            Text(
                text = "저장된 리스트가 없습니다."
            )
        }

    }
}

@Composable
fun BasicRoulette(
    modifier: Modifier = Modifier,
    rouletteList: List<String>,
    selectDegree: Int,
    centerPosition: (Int) -> Unit, // 270 도 (상단 기준)
) {
    var rotationValue by remember { mutableFloatStateOf(0f) }// 회전 각도
    var rotateIng by remember { mutableStateOf(false) } // 회전 중일때만 결과 값 반환을 위한 회전 동작 구분 boolean 변수
    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 2000, //todo 시간 설정 별도로 받기 고려
            easing = LinearOutSlowInEasing
        ),
        finishedListener = {
            //todo 룰렛 완료 후 동작
            rotateIng = false
        },
        label = ""
    )
    Box(
        modifier = modifier
    ) {
        RouletteView(
            modifier = Modifier
                .align(Alignment.Center)
                .width(rouletteSize)
                .height(rouletteSize + topPadding)
                .padding(top = topPadding)
                .rotate(angle),
            rouletteList = rouletteList,
            rouletteSize = rouletteList.size,
            angle = angle,
            rotateIng = rotateIng,
            selectDegree = selectDegree,
            centerPosition = centerPosition
        )
        // 상단 위치 표시 Icon
        Icon(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopCenter),
            imageVector = Icons.Filled.LocationOn,
            tint = Color.Red,
            contentDescription = "simple_icon"
        )
        // 가운데 룰렛 시작 IconButton
        IconButton(
            modifier = Modifier
                .width(iconSize)
                .height(iconSize + topPadding)
                .padding(top = topPadding)
                .background(
                    color = Color.White,
                    shape = CircleShape
                )
                .align(Alignment.Center),
            onClick = {
                rotateIng = true
                val check: Boolean = rotationValue in Float.MAX_VALUE - 5000f..Float.MAX_VALUE
                rotationValue = if (check) {
                    0f
                } else {
                    (720..1080).random().toFloat() + angle
                }
            }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_play_circle_24),
                modifier = Modifier.size(80.dp),
                contentDescription = "play_roulette"
            )
        }
    }
}

/**
 * 원판 view
 */
@Composable
fun RouletteView(
    modifier: Modifier,
    rouletteList: List<String>,
    rouletteSize: Int,
    angle: Float,
    rotateIng: Boolean,
    selectDegree: Int,
    centerPosition: (Int) -> Unit, // 270 도 (상단 기준)
) {
    val textMeasurer = rememberTextMeasurer()
    Canvas(
        modifier = modifier
    ) {
        val sweepAngle = 360f / rouletteSize
        val radius = size.width / 2 * 0.5
        for (i in 0 until rouletteSize) {
            // draw roulette arc
            val startAngle = (sweepAngle * i) // 각도 시작 위치
            drawArc(
                color = Color.Black,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                style = Stroke(width = 2f)
            )
            drawArc(
                color = when (i % 3) {
                    0 -> Color.White
                    1 -> Color.LightGray
                    else -> Color.Gray
                },
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
            )
            val changeRotate = if (startAngle + (angle % 360) > 360) {
                startAngle + (angle % 360) - 360
            } else {
                startAngle + (angle % 360)
            }
            // 회전 각도(selectDegree)가 부채꼴 시작과 끝 사이에 있을때 해당 i 값 반환
            if (changeRotate <= selectDegree && changeRotate + sweepAngle >= selectDegree) {
                if (rotateIng)
                    centerPosition.invoke(i)
            }
            // draw roulette text
            val rotateAngle = startAngle + (sweepAngle / 2)
            val medianAngle = ((rotateAngle - (rouletteSize / 2)) * Math.PI / 180f)
            // text view 그리는 중앙점 위치
            val x = (center.x + (radius * cos(medianAngle))).toFloat()
            val y = (center.y + (radius * sin(medianAngle))).toFloat()
            rotate(
                degrees = rotateAngle,
                pivot = Offset(
                    x = x,
                    y = y
                )
            ) {
                drawText(
                    textMeasurer = textMeasurer,
                    text = rouletteList[i],
                    maxLines = 1,
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = Color.Black,
                    ),
                    topLeft = Offset(
                        x = x,
                        y = y
                    ),
                    size = Size(
                        width = 70.dp.toPx(),
                        height = 20.dp.toPx()
                    )
                )
            }
        }
    }
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
        elevation = CardDefaults.cardElevation(
            defaultElevation = 3.dp
        )
    ) {
        Text(
            modifier = modifier
                .padding(horizontal = 8.dp, vertical = 4.dp),
            text = result,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

/**
 * 저장된 룰렛 리스트 선택 flow list view
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RouletteListFlow(
    modifier: Modifier = Modifier,
    rouletteList: List<String>,
    onDeleteClick: (String) -> Unit
) {
    FlowRow(
        modifier = modifier
            .padding(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        repeat(rouletteList.size) { index ->
            RouletteCard(
                text = rouletteList[index],
                onDeleteClick = onDeleteClick
            )
        }
    }
}