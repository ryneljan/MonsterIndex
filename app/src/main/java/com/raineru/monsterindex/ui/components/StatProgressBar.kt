package com.raineru.monsterindex.ui.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BaseStatProgressBar(
    @FloatRange(0.0, 1.0) progress: Float,
    color: Color,
    label: String,
    modifier: Modifier = Modifier
) {
    var progressWidth by remember {
        mutableFloatStateOf(
            0f
        )
    }

    var textWidth by remember { mutableIntStateOf(0) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(24.dp)
            .onSizeChanged {
                progressWidth = if (it.width * progress > textWidth) {
                    it.width * progress
                } else {
                    textWidth.toFloat()
                }
            }
            .background(
                color = Color.White,
                shape = RoundedCornerShape(64.dp),
            )
            .clip(RoundedCornerShape(64.dp)),
    ) {
        val animation: Float by animateFloatAsState(
            targetValue = if (progressWidth == 0f) 0f else 1f,
            animationSpec = tween(
                durationMillis = 950,
                easing = LinearOutSlowInEasing,
                delayMillis = 500
            ),
            label = "",
        )

        Box(
            modifier = Modifier
                .background(
                    color = color,
                    shape = RoundedCornerShape(64.dp),
                )
                .width(
                    progressWidth
                        .toInt()
                        .pxToDp() * animation,
                )
                .fillMaxHeight()
        )

        Text(
            text = label,
            modifier = Modifier
                .onSizeChanged {
                    textWidth = it.width
                }
                .align(Alignment.CenterStart)
                .padding(horizontal = 8.dp),
            color = Color.White,
        )
    }
}

@Preview
@Composable
fun BaseStatProgressBarPreview() {
    Column {
        BaseStatProgressBar(
            progress = 1 / 255f,
            color = Color.Green,
            label = "1/255"
        )
        BaseStatProgressBar(
            progress = 50 / 255f,
            color = Color(0xFFD53A47),
            label = "50/255"
        )
        BaseStatProgressBar(
            progress = 200 / 255f,
            color = Color.Gray,
            label = "200/255"
        )
        BaseStatProgressBar(
            progress = 255 / 255f,
            color = Color.Blue,
            label = "255/255"
        )
    }
}

@Composable
fun Int.pxToDp(): Dp = with(LocalDensity.current) { this@pxToDp.toDp() }