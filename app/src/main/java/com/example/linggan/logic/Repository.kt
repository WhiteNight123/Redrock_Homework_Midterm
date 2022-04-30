package com.example.linggan.logic

import com.example.linggan.logic.net.LingGanApi
import com.example.linggan.logic.net.ServiceCreator

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
object Repository {
    val retrofit = ServiceCreator.create(LingGanApi::class.java)

    suspend fun getIdeaPage() = retrofit.getIdeaPage()
}