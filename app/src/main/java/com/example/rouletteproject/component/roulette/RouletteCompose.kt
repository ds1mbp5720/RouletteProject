package com.example.rouletteproject.component.roulette

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 룰렛 화면
 */
@Composable
fun RouletteScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val rouletteSize = 9 //todo 임시 test size
        var rotationValue by remember { mutableFloatStateOf(0f) }// 회전 각도
        var resultPosition by remember { mutableIntStateOf(0) }
        val angle: Float by animateFloatAsState(
            targetValue = rotationValue,
            animationSpec = tween(
                durationMillis = 2000,
                easing = LinearOutSlowInEasing
            ),
            finishedListener = {

            },
            label = ""
        )
        Spacer(modifier = Modifier.height(25.dp))
        Icon(
            imageVector = Icons.Filled.KeyboardArrowDown,
            tint = Color.Red,
            contentDescription = "simple_icon"
        )
        BasicRoulette(
            modifier = Modifier,
            rouletteSize = rouletteSize,
            angle = angle,
            selectDegree = 270,
        ) {
            resultPosition = it
        }
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "결과값 $resultPosition",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(40.dp))
        RouletteButton() {
            val check: Boolean = rotationValue in Float.MAX_VALUE - 5000f..Float.MAX_VALUE
            rotationValue = if (check) {
                0f
            } else {
                (720..1080).random().toFloat() + angle
            }
        }
    }
}

@Composable
fun RouletteButton(
    changeRotationValue: () -> Unit
) {
    Button(
        onClick = changeRotationValue,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            text = "Start",
            color = Color.White
        )
    }

}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BasicRoulette(
    modifier: Modifier = Modifier,
    rouletteSize: Int,
    angle: Float,
    selectDegree: Int,
    centerPosition: (Int) -> Unit // 270 도 (상단 기준)
) {
    Box(
        modifier = modifier
    ) {
        val textMeasurer = rememberTextMeasurer()
        Canvas(
            modifier = Modifier
                .align(Alignment.Center)
                .size(400.dp)
                .rotate(angle)
        ) {
            val sweepAngle = 360f / rouletteSize
            val radius = size.width / 2 * 0.5
            var resultIndex = 0
            for (i in 0 until rouletteSize) {
                // draw roulette arc
                val startAngle = (sweepAngle * i)
                drawArc(
                    color = Color.Black,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                    style = Stroke(width = 1f)
                )
                drawArc(
                    color = Color.LightGray,
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = true,
                )
                val changeRotate = if (startAngle + (angle % 360) > 360) {
                    startAngle + (angle % 360) - 360
                } else {
                    startAngle + (angle % 360)
                }
                if( changeRotate <= selectDegree && changeRotate + sweepAngle >= selectDegree ) {
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
                        text = "Test $i",
                        maxLines = 1,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.Black,
                            background = Color.Red.copy(alpha = 0.2f)
                        ),
                        topLeft = Offset(
                            x = x,
                            y = y
                        )
                    )
                }
            }
        }
    }
}