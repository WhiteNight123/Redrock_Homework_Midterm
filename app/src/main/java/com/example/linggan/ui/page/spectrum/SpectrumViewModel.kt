package com.example.linggan.ui.page.spectrum

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.linggan.logic.Repository
import com.example.linggan.logic.model.ColorDetailData
import com.example.linggan.logic.model.SpectrumDetailData
import com.example.linggan.ui.lce.MyState
import kotlinx.coroutines.Dispatchers
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
class SpectrumViewModel : ViewModel() {

    private val spectrumResult = MutableStateFlow(1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val spectrumListResult: Flow<PagingData<ColorDetailData>> =
        spectrumResult.flatMapLatest {
            getPagingData(it)
        }.cachedIn(viewModelScope)

    fun getSpectrumList(id: Int) {

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

    private val _spectrumDetailState = MutableLiveData<MyState<SpectrumDetailData>>()
    val spectrumDetailState: LiveData<MyState<SpectrumDetailData>>
        get() = _spectrumDetailState

    fun getSpectrumDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            Repository.getSpectrumDetail(_spectrumDetailState, id)
        }
    }

}