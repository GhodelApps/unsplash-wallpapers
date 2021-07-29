package com.rafal.unsplashwallpapers.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import retrofit2.HttpException
import retrofit2.awaitResponse
import java.io.IOException

class UserPhotosPagingSource(
    private val api: UnsplashApi,
    private val username: String
) : PagingSource<Int, UnsplashSearchPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashSearchPhoto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, UnsplashSearchPhoto> {
        val position = params.key ?: 1
        return try {
            val response = api.getUserPhotos(username, position).awaitResponse()
            val results = response.body()!!
            LoadResult.Page(
                data = results,
                nextKey = if(results.isEmpty()) null else position + 1,
                prevKey = if(position == 1) null else position + 1
            )
        } catch(exception: IOException) {
            LoadResult.Error(exception)
        } catch(exception: HttpException) {
            LoadResult.Error(exception)
        } catch(exception: NullPointerException) {
            LoadResult.Error(exception)
        }
    }
}