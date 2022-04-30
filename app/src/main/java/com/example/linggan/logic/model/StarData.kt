package com.example.linggan.logic.model

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */

/*
{
    "has_more": false,
    "star_list": [ ]
}
 */
data class StarData(val has_more: Boolean, val star_list: List<StarListData>)

/*
{
    "id": 2,
    "name": "颜色",
    "colorShade": [ ]
}
 */
data class StarListData(val id: Int, val name: String, val colorShade: List<StarTempData>)

/*
{
    "color": { }
}
 */
data class StarTempData(val color: ColorDetailData)

data class StarBody(val shade_id: Int, val name: String)

data class UnStarBody(val star_id: Int)