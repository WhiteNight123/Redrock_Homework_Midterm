package com.example.linggan.ui.page.idea

import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Idea(modifier: Modifier) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden
    )
    val modalBottomSheetScope = rememberCoroutineScope()
    ModalBottomSheetLayout(
        modifier = modifier,
        sheetState = modalBottomSheetState,
        sheetContent = {
            Column() {
                ListItem() {
                    Text(
                        text = "按照光谱的颜色顺序，青(Cyan)应该是介于绿色和蓝色之间的一种颜色，即发蓝的绿色或发绿的蓝色。青色是在可见光谱中介于绿色和蓝色之间的颜色，属于电磁波里可见光的高频段，频率600~620THz(对应空气中波长500-485nm)，有点类似于湖水的颜色。 [1] \n" +
                                "青色，古代本义是蓝色，但在绘画颜色中，如果是蓝色中混有少量的绿色，则称为青色（是减法三原色之一），它有多个级别。类似于翡翠玉石的颜色。\n" +
                                "在老一辈中，青色是黑色，例如“青青子衿”指的是黑色的古代学士衣服。青介于绿色蓝色之间，但很多词容易让人误认为绿色，比如青草就是绿色。按照光谱的颜色顺序，青(Cyan)应该是介于绿色和蓝色之间的一种颜色，即发蓝的绿色或发绿的蓝色。青色是在可见光谱中介于绿色和蓝色之间的颜色，属于电磁波里可见光的高频段，频率600~620THz(对应空气中波长500-485nm)，有点类似于湖水的颜色。 [1] \\n\" +\n" +
                                "                                \"青色，古代本义是蓝色，但在绘画颜色中，如"
                    )
                }
            }
        },
    ) {
    }
    LaunchedEffect(modalBottomSheetState) {
        modalBottomSheetScope.launch {
            modalBottomSheetState.show()
        }
    }
}
