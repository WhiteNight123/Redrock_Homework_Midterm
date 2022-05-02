package com.example.linggan.ui.lce

import androidx.compose.runtime.Composable

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/2
 */
@Composable
fun <T> LcePage(myState: MyState<T>, onErrorClick: () -> Unit, content: @Composable (T) -> Unit) =
    when (myState) {
        MyLoading -> {
            LoadingContent()
        }
        is MyNoContent -> {
            NoContent(tip = myState.reason)
        }
        is MyError -> {
            ErrorContent(onErrorClick = onErrorClick)
        }
        is MySuccess<T> -> {
            content(myState.data)
        }
    }