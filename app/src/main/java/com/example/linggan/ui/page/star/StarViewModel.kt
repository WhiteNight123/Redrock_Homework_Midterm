package com.example.linggan.ui.page.star

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.linggan.logic.Repository
import com.example.linggan.logic.model.StarListData
import com.example.linggan.logic.model.UnStarBody
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/4/30
 */
class StarViewModel : ViewModel() {


//    val starResult1 = Pager(PagingConfig(pageSize = 10)) {
//        StarPagingSource()
//    }.flow.cachedIn(viewModelScope)


    private val starResult =
        MutableStateFlow("")

    private val deleteResult = MutableStateFlow(UnStarBody(0))

    @OptIn(ExperimentalCoroutinesApi::class)
    val starListResult: Flow<PagingData<StarListData>> =
        starResult.flatMapLatest {
            Pager(PagingConfig(pageSize = 10)) {
                StarPagingSource(it)
            }.flow
        }.cachedIn(viewModelScope)

    fun getStarList(token: String) {

        viewModelScope.launch {
            starResult.emit(token)
        }
    }

    fun deleteStar(id: Int, token: String) {
        viewModelScope.launch {
            Repository.deleteStar(
                token, id
            )
        }
    }
}