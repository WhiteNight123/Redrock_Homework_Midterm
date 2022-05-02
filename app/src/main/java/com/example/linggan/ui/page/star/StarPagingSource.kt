package com.example.linggan.ui.page.star

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.linggan.logic.Repository
import com.example.linggan.logic.model.StarListData

/**
 * ...
 * @author WhiteNight123(Guo XiaoQiang)
 * @email 1448375249@qq.com
 * @date 2022/5/3
 */
class StarPagingSource(private val token: String) :
    PagingSource<Int, StarListData>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StarListData> {
        return try {
            val nextPage = params.key ?: 1
            val response = Repository.getHeroList(token, nextPage)

            LoadResult.Page(
                data = response.data.star_list,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (response.data.has_more) nextPage + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, StarListData>): Int? {
        return null
    }
}