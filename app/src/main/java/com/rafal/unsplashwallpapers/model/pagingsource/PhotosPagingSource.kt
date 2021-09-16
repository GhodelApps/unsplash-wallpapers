package com.rafal.unsplashwallpapers.model.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import retrofit2.HttpException
import java.io.IOException

class PhotosPagingSource(
    private val api: UnsplashApi,
    private val query: String,
    private val sortBy: String
) : PagingSource<Int, UnsplashSearchPhoto>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashSearchPhoto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashSearchPhoto> {
        val position = params.key ?: 1

        return try {
            val response = api.searchPhotos(query, position, sortBy)

            val results = response.body()!!.results
            LoadResult.Page(
                data = results,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (results.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: NullPointerException) {
            return LoadResult.Error(exception)
        }

    }
}