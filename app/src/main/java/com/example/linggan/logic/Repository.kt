package com.example.linggan.logic

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.MutableLiveData
import com.example.linggan.logic.model.SpectrumDetailData
import com.example.linggan.logic.net.LingGanApi
import com.example.linggan.logic.net.ServiceCreator
import com.example.linggan.ui.lce.MyLoading
import com.example.linggan.ui.lce.MyNoContent
import com.example.linggan.ui.lce.MyState
import com.example.linggan.ui.lce.MySuccess

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
object Repository {
    private val retrofit = ServiceCreator.create(LingGanApi::class.java)

    suspend fun getHeroList(token: String, page: Int) = retrofit.getStarList(token, page)
    suspend fun getSpectrumList(theme_id: Int, page: Int) = retrofit.getSpectrumList(theme_id, page)
    suspend fun getSpectrumDetail(state: MutableLiveData<MyState<SpectrumDetailData>>, id: Int) {
        val response = retrofit.getSpectrumDetail(id)
        state.postValue(MyLoading)

        if (response.code == 114) {
            val list = mutableListOf<MutableList<Color>>()

            val shade = response.data.shades.shade_list
            shade.forEach {
                val shadeList = mutableListOf<Color>()
                it.shade.forEach {
                    shadeList.add(Color(it.color.r, it.color.g, it.color.b))
                }
                list.add(shadeList)
            }
            response.data.shadeList = list

            state.postValue(MySuccess(response.data))
        } else {
            state.postValue(MyNoContent("没有数据"))
        }
    }
}