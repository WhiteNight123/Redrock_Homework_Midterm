package com.example.linggan.ui.page.spectrum

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.linggan.LingGanApp
import com.example.linggan.LingGanApp.Companion.context
import com.example.linggan.R
import com.example.linggan.logic.model.ColorDetailData
import com.example.linggan.logic.model.SpectrumDetailData
import com.example.linggan.ui.Nav.LingGanDestination
import com.example.linggan.ui.Nav.LingGanDestination.SPECTRUM_ROUTE_ID
import com.example.linggan.ui.lce.LcePage
import com.example.linggan.ui.lce.MyState
import com.example.linggan.utils.showToast
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.lang.reflect.Type

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SpectrumNav(modifier: Modifier, startDestination: String = LingGanDestination.SPECTRUM_ROUTE) {
    val navController2 = rememberNavController()
    //val actions2 = remember(navController2) { LingGanActions(navController2) }
    NavHost(navController2, startDestination = LingGanDestination.SPECTRUM_ROUTE) {
        composable(LingGanDestination.SPECTRUM_ROUTE) {
            Spectrum(modifier, navController2)
        }
        composable(
            "${LingGanDestination.SPECTRUM_DETAIL_ROUTE}/{$SPECTRUM_ROUTE_ID}",
            arguments = listOf(navArgument(SPECTRUM_ROUTE_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            val argument = requireNotNull(backStackEntry.arguments)
            val id = argument.getInt(SPECTRUM_ROUTE_ID)
            val viewModel: SpectrumViewModel = hiltViewModel()
            viewModel.getSpectrumDetail(id)

            val state by viewModel.spectrumDetailState.observeAsState()
            state?.let { SpectrumDetail(it, navController2, modifier) }
        }
        composable(
            "${LingGanDestination.SPECTRUM_SHADE_ROUTE}/{color}",
            arguments = listOf(
                navArgument("color") { type = NavType.StringType }),
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            val color = arguments.getString("color")
            val gson = Gson()

            val type: Type = object : TypeToken<ArrayList<Color>>() {}.type
            val list1: List<Color> = gson.fromJson(color, type)
            Log.e("TAG", "SpectrumNav: string=${list1.toString()}")
            SpectrumShade(list1, navController = navController2, modifier)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Spectrum(
    modifier: Modifier,
    //actions: LingGanActions
    navController: NavController
) {
    val viewModel: SpectrumViewModel = hiltViewModel()
    val spectrumList = viewModel.spectrumListResult.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "色谱") },
                backgroundColor = Color(200, 219, 185),
                actions = {
                    IconButton(onClick = { showToast(LingGanApp.context, "搜索") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_search_48),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                })
        },
    ) { innerPadding ->
        val modifier1 = Modifier.padding(innerPadding)
        Column(Modifier.background(Color(200, 219, 185))) {
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

                        SpectrumListDetail(
                            data!!,
                            toDetail = { navController.navigate("${LingGanDestination.SPECTRUM_DETAIL_ROUTE}/$it") })
                    }
                }
            }
        }
    }

}


@Composable
fun SpectrumListDetail(colorDetailData: ColorDetailData, toDetail: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                toDetail(colorDetailData.id)
                Log.e("TAG", "SpectrumListDetail: id=${colorDetailData.id}")
            }
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
                    modifier = Modifier.padding(top = 4.dp, end = 24.dp)
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

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpectrumDetail(
    state: MyState<SpectrumDetailData>,
    navController: NavController,
    modifier: Modifier
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val modalBottomSheetScope = rememberCoroutineScope()


    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "标题") },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_arrowleft),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    navController.navigateUp()
                                }
                        )

                    }
                },
                actions = {
                    IconButton(onClick = { showToast(LingGanApp.context, "科普") }) {
                        Icon(
                            painter = painterResource(id = R.drawable.icon_book),
                            contentDescription = null,
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    modalBottomSheetScope.launch {
                                        modalBottomSheetState.show()
                                        showToast(context, "科普")
                                    }
                                }
                        )
                    }
                })
        }, bottomBar = {
            ModalBottomSheetLayout(
                sheetShape = RoundedCornerShape(10),
                sheetState = modalBottomSheetState,
                sheetContent = {
                    Column(modifier = modifier) {
                        Card() {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = "黄色\n" +
                                        "温柔和娇美的颜色，有很强的温暖感，使人感到愉悦和纯洁。枯萎的植物往往呈淡黄色，又有衰老、老年、黄昏的联想，还可以让人想起极富营养的蛋黄、奶油及其他食品。" +
                                        "但是，黄色又与病弱有关，植物的衰败、枯萎也与黄色相关联。因此，黄色又使人感到空虚、贫乏和不健康。"
                            )
                        }
                    }
                },
            ) {
            }
        }
    ) { innerPadding ->
        val modifier1 = Modifier.padding(innerPadding)
        LcePage(myState = state, onErrorClick = { /*TODO*/ }) { data ->
            Column(modifier = Modifier.padding(8.dp)) {

                Text(text = data.colors.color_1.name, fontSize = 20.sp)
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)

                ) {

                    Column(
                        modifier = Modifier.background(
                            Color(
                                red = data.colors.color_1.r,
                                green = data.colors.color_1.g,
                                blue = data.colors.color_1.b
                            )
                        )
                    ) {
                        Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                            val nativePaint = android.graphics.Paint().let {
                                it.apply {
                                    textSize = 250f
                                    color = android.graphics.Color.GRAY
                                }
                            }
                            drawContext.canvas.nativeCanvas.drawText(
                                "甲",
                                700f,
                                320f,
                                nativePaint
                            )
                        }

                        Text(
                            text = "Hex #${data.colors.color_1.hex}",
                            color = Color(255, 255, 255, 85),
                            modifier = Modifier.padding(start = 16.dp, top = 32.dp)
                        )
                        Text(
                            color = Color(255, 255, 255, 80),
                            text = "RGB ${data.colors.color_1.r},${data.colors.color_1.g},${data.colors.color_1.b}",
                            modifier = Modifier.padding(start = 16.dp)
                        )
                        Text(
                            color = Color(255, 255, 255, 80),
                            text = "CMYK ${data.colors.color_1.c},${data.colors.color_1.m},${data.colors.color_1.y},${data.colors.color_1.k}",
                            modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                        )
                    }
                }

                Text(text = "渐变示例", fontSize = 20.sp)
                val gson = Gson()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {


                    Log.e("TAG", "SpectrumDetail: ${data.shadeList[0].toString()}")
                    Canvas(modifier = Modifier
                        .size(100.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                navController.navigate(
                                    "${LingGanDestination.SPECTRUM_SHADE_ROUTE}/${
                                        gson.toJson(data.shadeList[0])
                                    }"
                                )
                            })
                        }) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            brush = Brush.verticalGradient(
                                data.shadeList[0]
                            ),
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                            radius = size.minDimension / 3
                        )
                    }
                    Canvas(modifier = Modifier
                        .size(100.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                navController.navigate(
                                    "${LingGanDestination.SPECTRUM_SHADE_ROUTE}/${
                                        gson.toJson(data.shadeList[1])
                                    }"
                                )
                            })
                        }) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            brush = Brush.verticalGradient(
                                data.shadeList[1]
                            ),
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                            radius = size.minDimension / 3
                        )
                    }
                    Canvas(modifier = Modifier
                        .size(100.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                navController.navigate(
                                    "${LingGanDestination.SPECTRUM_SHADE_ROUTE}/${
                                        gson.toJson(data.shadeList[2])
                                    }"
                                )
                            })
                        }) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            brush = Brush.verticalGradient(
                                data.shadeList[2]
                            ),
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                            radius = size.minDimension / 3
                        )
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Canvas(modifier = Modifier
                        .size(100.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                navController.navigate(
                                    "${LingGanDestination.SPECTRUM_SHADE_ROUTE}/${
                                        gson.toJson(data.shadeList[3])
                                    }"
                                )
                            })
                        }) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            brush = Brush.verticalGradient(
                                data.shadeList[3]
                            ),
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                            radius = size.minDimension / 3
                        )
                    }
                    Canvas(modifier = Modifier
                        .size(100.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                navController.navigate(
                                    "${LingGanDestination.SPECTRUM_SHADE_ROUTE}/${
                                        gson.toJson(data.shadeList[4])
                                    }"
                                )
                            })
                        }) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            brush = Brush.verticalGradient(
                                data.shadeList[4]
                            ),
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                            radius = size.minDimension / 3
                        )
                    }
                    Canvas(modifier = Modifier
                        .size(100.dp)
                        .pointerInput(Unit) {
                            detectTapGestures(onLongPress = {
                                navController.navigate(
                                    "${LingGanDestination.SPECTRUM_SHADE_ROUTE}/${
                                        gson.toJson(data.shadeList[5])
                                    }"
                                )
                            })
                        }) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            brush = Brush.verticalGradient(
                                data.shadeList[5]
                            ),
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                            radius = size.minDimension / 3
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "配色示例", fontSize = 20.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.icon_2arrows),
                        contentDescription = null,
                        Modifier
                            .size(32.dp)
                            .padding(end = 8.dp)
                    )
                }

                val nativePaint = android.graphics.Paint().let {
                    it.apply {
                        textSize = 72f
                        color = android.graphics.Color.GRAY
                    }
                }
                val colors = data.colors
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column() {
                        val color2 = colors.color_2
                        Canvas(modifier = Modifier.size(100.dp, 50.dp)) {

                            drawRect(
                                color = Color(color2.r, color2.g, color2.b),
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
                        Text(text = "#${color2.hex}", modifier = Modifier.padding(start = 19.dp))
                    }
                    Column() {
                        val color3 = colors.color_3

                        Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                            drawRect(
                                color = Color(color3.r, color3.g, color3.b),
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
                        Text(text = "#${color3.hex}", modifier = Modifier.padding(start = 19.dp))
                    }
                    Column() {
                        val color4 = colors.color_4

                        Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                            drawRect(
                                color = Color(color4.r, color4.g, color4.b),
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
                        Text(text = "#${color4.hex}", modifier = Modifier.padding(start = 19.dp))
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column() {
                        val color5 = colors.color_5

                        Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                            drawRect(
                                color = Color(color5.r, color5.g, color5.b),
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
                        Text(text = "#${color5.hex}", modifier = Modifier.padding(start = 19.dp))
                    }
                    Column() {
                        val color6 = colors.color_6

                        Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                            drawRect(
                                color = Color(color6.r, color6.g, color6.b),
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
                        Text(text = "#${color6.hex}", modifier = Modifier.padding(start = 19.dp))
                    }
                    Column() {
                        val color7 = colors.color_7

                        Canvas(modifier = Modifier.size(100.dp, 50.dp)) {
                            drawRect(
                                color = Color(color7.r, color7.g, color7.b),
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
                        Text(text = "#${color7.hex}", modifier = Modifier.padding(start = 19.dp))
                    }
                }
            }
        }

    }


}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SpectrumShade(color: List<Color>, navController: NavController, modifier: Modifier) {

    var isLike by remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val modalBottomSheetScope = rememberCoroutineScope()
    Scaffold(bottomBar = {
        ModalBottomSheetLayout(
            sheetShape = RoundedCornerShape(10),
            sheetState = modalBottomSheetState,
            sheetContent = {
                Row(modifier.padding(8.dp)) {
                    Image(
                        painter = painterResource(R.drawable.icon_qq),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clickable { })
                    Image(
                        painter = painterResource(R.drawable.icon_wechat),
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp).offset(8.dp)
                            .clickable { showToast(context,"暂不支持微信分享")})
                }
            },
        ) {
        }
    }) { innerPadding ->
        val modifier1 = Modifier.padding(innerPadding)
        Box(
            modifier
                .background(brush = Brush.verticalGradient(color))
                .fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_arrowleft),
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .offset(24.dp, 16.dp)
                    .clickable { navController.navigateUp() }
            )
            Icon(
                painter = if (isLike) painterResource(R.drawable.icon_heart_48_red) else painterResource(
                    R.drawable.icon_heart_48
                ),
                modifier = Modifier
                    .size(32.dp)
                    .offset(32.dp, (-128).dp)
                    .align(Alignment.BottomStart)
                    .clickable { isLike = !isLike },
                contentDescription = null
            )
            Icon(
                painter = painterResource(id = R.drawable.icon_share_48),
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd)
                    .offset((-32).dp, (-128).dp)
                    .clickable {
                        modalBottomSheetScope.launch {
                            modalBottomSheetState.show()
                            showToast(context, "分享")
                        }
                    },
                contentDescription = null
            )
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

}

