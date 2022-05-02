package com.example.linggan.ui.page.spectrum

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.linggan.logic.Repository
import com.example.linggan.logic.model.ColorDetailData

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/1
 */
class SpectrumPagingSource(private val theme_id: Int) : PagingSource<Int, ColorDetailData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ColorDetailData> {
        return try {
            val page = params.key ?: 0 // set page 1 as default
            Log.e("TAG", "page: $page")
            Log.e("TAG", "theme_id: $theme_id")
            val apiResponse = Repository.getSpectrumList(theme_id, page)
            val spectrumList = apiResponse.data.color_list
            val prevKey = if (page > 0) page - 1 else null
            val nextKey = if (apiResponse.data.has_more) page + 2 else null
            LoadResult.Page(spectrumList, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ColorDetailData>): Int? {
        return null
    }

}