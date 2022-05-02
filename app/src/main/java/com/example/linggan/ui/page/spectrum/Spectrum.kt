package com.example.linggan.ui.page.spectrum

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.linggan.R
import com.example.linggan.logic.model.ColorDetailData
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Spectrum(
    modifier: Modifier,
) {
    val viewModel: SpectrumViewModel = hiltViewModel()
    val spectrumList = viewModel.spectrumListResult.collectAsLazyPagingItems()

    Column() {
        val pagerState = rememberPagerState(0)

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
        )

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                Log.e("TESE", "Spectrum: $page+1")
                viewModel.getSpectrumList(page + 1)
            }
        }

        HorizontalPager(
            count = 7,
            state = pagerState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            LazyColumn(modifier = modifier) {
                itemsIndexed(spectrumList) { index, data ->
                    Log.e("TAG", "Spectrum??: $index")
                    SpectrumListDetail(data!!)
                }
            }

        }
    }
}


@Composable
fun SpectrumListDetail(colorDetailData: ColorDetailData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.background(
                Color(
                    red = colorDetailData.r,
                    green = colorDetailData.g,
                    blue = colorDetailData.b
                )
            )
        ) {
            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth(1f)) {
                Text(
                    text = colorDetailData.name,
                    fontSize = 32.sp,
                    modifier = Modifier.padding(end = 24.dp)
                )
            }

            Text(
                text = "Hex #${colorDetailData.hex}",
                color = Color(255, 255, 255, 85),
                modifier = Modifier.padding(start = 16.dp, top = 32.dp)
            )
            Text(
                color = Color(255, 255, 255, 80),
                text = "RGB ${colorDetailData.r},${colorDetailData.g},${colorDetailData.b}",
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                color = Color(255, 255, 255, 80),
                text = "CMYK ${colorDetailData.c},${colorDetailData.m},${colorDetailData.y},${colorDetailData.k}",
                modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun SpectrumDetail() {
    Column(modifier = Modifier.padding(8.dp)) {
//        Text(text = spectrumDetailData.colors.color_1.name)
//        SpectrumDetail(spectrumDetailData = spectrumDetailData)
        Text(text = "渐变示例")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Canvas(modifier = Modifier.size(100.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0, 247, 153),
                            Color(255, 238, 0),
                            Color(255, 0, 0)
                        )
                    ),
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = size.minDimension / 3
                )
            }
            Canvas(modifier = Modifier.size(100.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0, 247, 153),
                            Color(255, 238, 0),
                            Color(255, 0, 0)
                        )
                    ),
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = size.minDimension / 3
                )
            }
            Canvas(modifier = Modifier.size(100.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0, 247, 153),
                            Color(255, 238, 0),
                            Color(255, 0, 0)
                        )
                    ),
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = size.minDimension / 3
                )
            }

        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Canvas(modifier = Modifier.size(100.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0, 247, 153),
                            Color(255, 238, 0),
                            Color(255, 0, 0)
                        )
                    ),
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = size.minDimension / 3
                )
            }
            Canvas(modifier = Modifier.size(100.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0, 247, 153),
                            Color(255, 238, 0),
                            Color(255, 0, 0)
                        )
                    ),
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = size.minDimension / 3
                )
            }
            Canvas(modifier = Modifier.size(100.dp)) {
                val canvasWidth = size.width
                val canvasHeight = size.height
                drawCircle(
                    brush = Brush.verticalGradient(
                        listOf(
                            Color(0, 247, 153),
                            Color(255, 238, 0),
                            Color(255, 0, 0)
                        )
                    ),
                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                    radius = size.minDimension / 3
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "配色示例")
            Icon(
                painter = painterResource(id = R.drawable.icon_2arrows),
                contentDescription = null,
                Modifier
                    .size(24.dp)
                    .padding(end = 8.dp)
            )
        }

        val nativePaint = android.graphics.Paint().let {
            it.apply {
                textSize = 72f
                color = android.graphics.Color.GRAY
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {

            Column() {

                Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                    drawRect(
                        color = Color(255, 238, 0),
                        size = Size(175f, 90f),
                        topLeft = Offset(57f, 35f),
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        "乙",
                        165f,
                        115f,
                        nativePaint
                    )


                }
                Text(text = "#225,238,0", modifier = Modifier.padding(start = 19.dp))
            }
            Column() {
                Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                    drawRect(
                        color = Color(255, 238, 0),
                        size = Size(175f, 90f),
                        topLeft = Offset(57f, 35f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "丙",
                        165f,
                        115f,
                        nativePaint
                    )

                }
                Text(text = "#225,238,0", modifier = Modifier.padding(start = 19.dp))
            }
            Column() {
                Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                    drawRect(
                        color = Color(255, 238, 0),
                        size = Size(175f, 90f),
                        topLeft = Offset(57f, 35f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "丁",
                        165f,
                        115f,
                        nativePaint
                    )

                }
                Text(text = "#225,238,0", modifier = Modifier.padding(start = 19.dp))
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            Column() {
                Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                    drawRect(
                        color = Color(255, 238, 0),
                        size = Size(175f, 90f),
                        topLeft = Offset(57f, 35f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "戊",
                        165f,
                        115f,
                        nativePaint
                    )

                }
                Text(text = "#225,238,0", modifier = Modifier.padding(start = 19.dp))
            }
            Column() {
                Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                    drawRect(
                        color = Color(255, 238, 0),
                        size = Size(175f, 90f),
                        topLeft = Offset(57f, 35f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "己",
                        165f,
                        115f,
                        nativePaint
                    )

                }
                Text(text = "#225,238,0", modifier = Modifier.padding(start = 19.dp))
            }
            Column() {
                Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                    drawRect(
                        color = Color(255, 238, 0),
                        size = Size(175f, 90f),
                        topLeft = Offset(57f, 35f),
                    )
                    drawContext.canvas.nativeCanvas.drawText(
                        "庚",
                        165f,
                        115f,
                        nativePaint
                    )

                }
                Text(text = "#225,238,0", modifier = Modifier.padding(start = 19.dp))
            }
        }
    }

}

@Preview
@Composable
fun DrawColorCircle() {
//    Box() {
//
//        Canvas(modifier = Modifier.size(32.dp)) {
//            val canvasWidth = size.width
//            val canvasHeight = size.height
//            drawCircle(
//                brush = Brush.verticalGradient(listOf(Color(0, 247, 153), Color(255, 238, 0),Color(255, 0, 0))),
//                center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
//                radius = size.minDimension / 4
//            )
//        }
//        Canvas(modifier = Modifier.fillMaxSize()) {
//            drawRect(
//                color=Color(255, 238, 0),
//                size = Size(64f,32f),
//            )
//
//        }
//    }
    SpectrumDetail()
}

