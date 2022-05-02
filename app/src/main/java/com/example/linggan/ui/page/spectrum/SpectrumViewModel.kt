package com.example.linggan.ui.page.spectrum

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.linggan.logic.model.ColorDetailData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
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
class SpectrumViewModel : ViewModel() {

    private val spectrumResult = MutableStateFlow(1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val spectrumListResult: Flow<PagingData<ColorDetailData>> =
        spectrumResult.flatMapLatest {
            getPagingData(it)
        }.cachedIn(viewModelScope)
    private var searchJob: Job? = null

    fun getSpectrumList(id: Int) {
        Log.e("TAG", "getSpectrumListffff: $id")
        viewModelScope.launch {
            spectrumResult.emit(id)
        }
    }


    private fun getPagingData(id: Int) = Pager(
        PagingConfig(
            pageSize = 10,
            enablePlaceholders = false
        )
    ) {
        SpectrumPagingSource(id)
    }.flow


}