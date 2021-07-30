package com.rafal.unsplashwallpapers.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashCollection
import retrofit2.HttpException
import java.io.IOException

class CollectionsPagingSource(
    private val api: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashCollection>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashCollection>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashCollection> {
        val position = params.key ?: 1

        return try {
            val response = api.searchCollections(query, position)
            val results = response.body()!!.results
            LoadResult.Page(
                data = results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )

        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: NullPointerException) {
            LoadResult.Error(exception)
        }
    }
}