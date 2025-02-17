package com.example.rouletteproject.component.roulette

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.entity.RouletteEntity
import com.example.rouletteproject.MainViewModel
import com.example.rouletteproject.R
import com.example.rouletteproject.component.ResultTextView
import com.example.rouletteproject.randomColor
import com.example.rouletteproject.setting.SettingDataStore
import kotlin.math.cos
import kotlin.math.sin

private val topPadding = 20.dp
private val iconSize = 80.dp

/**
 * 룰렛 화면
 */
@Composable
fun RouletteScreen(
    mainViewModel: MainViewModel,
    selectedList: RouletteEntity?
) {
    val colorList = remember { mutableStateListOf<Color>() }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var resultPosition: Int? by remember { mutableStateOf(null) }
        // todo add googleAdMob
        LaunchedEffect(key1 = selectedList) {
            colorList.clear()
            resultPosition = null
            selectedList?.rouletteData?.forEach { _ ->
                colorList.add(randomColor())
            }
        }
        Spacer(modifier = Modifier.height(25.dp))
        ResultTextView(
            modifier = Modifier,
            result = stringResource(
                id = R.string.text_result,
                if (selectedList != null) {
                    if (resultPosition != null) {
                        selectedList.rouletteData[resultPosition!!]
                    } else ""
                } else {
                    stringResource(id = R.string.text_none_select_list)
                }
            )
        )
        Spacer(modifier = Modifier.height(35.dp))
        selectedList?.let { selected ->
            if (selected.rouletteData.size == colorList.size) {
                BasicRoulette(
                    modifier = Modifier,
                    rouletteList = selected.rouletteData,
                    colorList = colorList,
                    selectDegree = 270,
                ) { position ->
                    resultPosition = position
                }
            }
        }
    }
}

@Composable
fun BasicRoulette(
    modifier: Modifier = Modifier,
    rouletteList: List<String>,
    colorList: List<Color>,
    selectDegree: Int,
    centerPosition: (Int) -> Unit, // 270 도 (상단 기준)
) {
    var rotationValue by remember { mutableFloatStateOf(0f) }// 회전 각도
    var rotating by remember { mutableStateOf(false) } // 회전 중일때만 결과 값 반환을 위한 회전 동작 구분 boolean 변수
    val angle: Float by animateFloatAsState(
        targetValue = rotationValue,
        animationSpec = tween(
            durationMillis = 2000, //todo 드래그 거리 구해서 해당 거리만큼 시간값 변경해보기
            easing = LinearOutSlowInEasing
        ),
        finishedListener = {
            rotating = false
        },
        label = ""
    )
    Box(
        modifier = modifier
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(topPadding),
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            shape = CircleShape,
            colors = CardDefaults.cardColors(
                containerColor = Color.White,
                contentColor = Color.White
            )
        ) {}
        RouletteView(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(topPadding + 5.dp)
                .rotate(angle)
                .pointerInput(Unit) {
                    if (SettingDataStore.getDragRotate()) {
                        detectDragGestures( //todo 해당 부분 로직 생각
                            onDragStart = { offset ->
                                rotating = true
                            },
                            onDrag = { change, dragAmount ->
                                if (rotating) {
                                    rotationValue = (720..1080) //todo rotationValue 애니메이션 트리거
                                        .random()
                                        .toFloat() + angle
                                }
                            },
                            onDragEnd = {}
                        )
                    }
                },
            rouletteList = rouletteList,
            colorList = colorList,
            rouletteSize = rouletteList.size,
            angle = angle,
            rotateIng = rotating,
            selectDegree = selectDegree,
            centerPosition = centerPosition
        )
        // 상단 위치 표시 Icon
        Icon(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.TopCenter),
            painter = painterResource(id = R.drawable.baseline_south_24),
            tint = Color.Black,
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
                rotating = true
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
    colorList: List<Color>,
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
                color = Color.White,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                style = Stroke(width = 20f)
            )
            drawArc(
                color = colorList[i],
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
                        fontSize = 30.sp,
                        fontStyle = FontStyle.Italic,
                        color = Color.Black,
                    ),
                    topLeft = Offset(
                        x = x,
                        y = y
                    ),
                    size = Size(
                        width = 90.dp.toPx(),
                        height = 30.dp.toPx()
                    )
                )
            }
        }
    }
}

