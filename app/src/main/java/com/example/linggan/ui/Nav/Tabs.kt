package com.example.linggan.ui.Nav

import androidx.annotation.DrawableRes
import com.example.linggan.R

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
enum class Tabs(
    val title: String,
    @DrawableRes val icon: Int
) {
    SPECTRUM_PAGE("色谱", R.drawable.icon_colorpalette),
    IDEA_PAGE("灵感", R.drawable.icon_lamp),
    STAR_PAGE("收藏", R.drawable.icon_heart_96)
}