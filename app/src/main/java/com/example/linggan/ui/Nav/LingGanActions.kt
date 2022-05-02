package com.example.linggan.ui.Nav

import androidx.navigation.NavController

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/2
 */
class LingGanActions(navController: NavController) {
    val toSpectrumDetail: (Int) -> Unit = { id ->
        navController.navigate("${LingGanDestination.SPECTRUM_DETAIL_ROUTE}/$id")
    }
    val toLogin: () -> Unit = {
        navController.navigate(LingGanDestination.LOGIN_ROUTE)
    }
    val toLingGanDetail: (Int) -> Unit = { id ->
        navController.navigate(LingGanDestination.LING_GAN_DETAIL_ROUTE)
    }
    val upPress: () -> Unit = {
        navController.navigateUp()
    }
}