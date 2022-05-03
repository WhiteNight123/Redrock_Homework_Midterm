package com.example.linggan.ui.page.star

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.linggan.LingGanApp
import com.example.linggan.LingGanApp.Companion.context
import com.example.linggan.R
import com.example.linggan.logic.model.StarListData
import com.example.linggan.ui.Nav.LingGanDestination
import com.example.linggan.utils.showToast
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun Star(modifier: Modifier) {
    val starViewModel: StarViewModel = hiltViewModel()
    starViewModel.getStarList(LingGanDestination.TOKEN)
    val starList = starViewModel.starListResult.collectAsLazyPagingItems()
    var isRefreshing by remember {
        mutableStateOf(false)
    }

    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val modalBottomSheetScope = rememberCoroutineScope()
    if ("" == LingGanDestination.TOKEN) {
        showToast(context, "请先登录")
    } else {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "收藏") },
                    backgroundColor = Color.White,
                )
            }, bottomBar = {
                ModalBottomSheetLayout(
                    sheetShape = RoundedCornerShape(10),
                    sheetState = modalBottomSheetState,
                    sheetContent = {
                        Row(
                            modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Column(Modifier.size(72.dp)) {
                                Image(
                                    painter = painterResource(R.drawable.icon_qq),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .clickable { })
                                Text(text = "qq好友")
                            }
                            Column(Modifier.size(72.dp)) {
                                Image(
                                    painter = painterResource(R.drawable.icon_wechat),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .offset(8.dp)
                                        .clickable { showToast(LingGanApp.context, "暂不支持微信分享") })
                                Text("微信好友")
                            }
                            Column(Modifier.size(72.dp)) {
                                Image(
                                    painter = painterResource(R.drawable.icon_download),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .offset(8.dp)
                                        .clickable { showToast(LingGanApp.context, "暂不支持保存图片") })
                                Text("保存图片")
                            }
                            Column(Modifier.size(72.dp)) {
                                Image(
                                    painter = painterResource(R.drawable.icon_link),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(48.dp)
                                        .offset(8.dp)
                                        .clickable { showToast(LingGanApp.context, "暂不支持复制链接") })
                                Text("复制链接")
                            }

                        }
                    },
                ) {
                }
            }
        ) { innerPadding ->
            val modifier1 = Modifier.padding(innerPadding)
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = {
                    isRefreshing = true
                    starList.refresh()
                    isRefreshing = false
                },
            ) {
                LazyColumn(modifier) {
                    items(starList, key = { item: StarListData -> item.id }) { item ->
                        val dismissState = rememberDismissState()
                        if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                            if (item != null) {
                                starViewModel.deleteStar(item.id, LingGanDestination.TOKEN)
                                starList.refresh()
                            }
                        }
                        SwipeToDismiss(
                            state = dismissState,
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateItemPlacement(),
                            dismissThresholds = { direction ->
                                FractionalThreshold(if (direction == DismissDirection.EndToStart) 0.25f else 0.5f)
                            },
                            directions = setOf(DismissDirection.EndToStart),
                            background = { }) {
                            val starListData = item!!
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .background(
                                        Brush.linearGradient(
                                            listOf(
                                                Color(
                                                    starListData.colorShade[0].color.r,
                                                    starListData.colorShade[0].color.g,
                                                    starListData.colorShade[0].color.b
                                                ),
                                                Color(
                                                    starListData.colorShade[1].color.r,
                                                    starListData.colorShade[1].color.g,
                                                    starListData.colorShade[1].color.b
                                                )
                                            )
                                        )
                                    ),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Bottom
                                ) {
                                    Text(
                                        text = starListData.colorShade[0].color.name,
                                        fontSize = 20.sp,
                                        modifier = Modifier.offset(8.dp, (-4).dp)
                                    )
                                    Text(text = " ", fontSize = 64.sp)
                                    Image(
                                        painter = painterResource(R.drawable.icon_share_48),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(24.dp)
                                            .offset(
                                                (-8).dp, (-4).dp
                                            )
                                            .clickable {
                                                modalBottomSheetScope.launch {
                                                    modalBottomSheetState.show()

                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
