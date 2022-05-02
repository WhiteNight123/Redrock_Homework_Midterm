package com.example.linggan.ui.page.star

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.linggan.logic.Repository

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
class StarViewModel : ViewModel() {
    var heroListResult
    fun getStarList(token:String){
        heroListResult = Pager(PagingConfig(pageSize = 10)) {
            StarPagingSource(token)
        }.flow.cachedIn(viewModelScope)
    }

}