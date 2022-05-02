package com.example.linggan.ui.lce

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/2
 */
@Composable
fun LoadingContent(
    modifier: Modifier = Modifier
) {
    var progress = remember {
        mutableStateOf(0.1f)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        CircularProgressIndicator()
        Spacer(modifier = Modifier.requiredHeight(10.dp))
    }
}