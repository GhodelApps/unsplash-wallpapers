package com.rafal.unsplashwallpapers.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import retrofit2.HttpException
import java.io.IOException

class AllPhotosPagingSource(
    private val api: UnsplashApi,
    private val orderBy: String
) : PagingSource<Int, UnsplashSearchPhoto>() {
    override fun getRefreshKey(state: PagingState<Int, UnsplashSearchPhoto>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashSearchPhoto> {
        val position = params.key ?: 1

        return try {
            val response = api.getAllPhotos(position, orderBy)
            val body = response.body()!!
            LoadResult.Page(
                data = body,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (body.isEmpty()) null else position + 1
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