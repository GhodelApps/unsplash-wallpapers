package com.rafal.unsplashwallpapers.model.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashUser
import retrofit2.HttpException
import java.io.IOException

class UsersPagingSource(
    private val api: UnsplashApi,
    private val query: String
) : PagingSource<Int, UnsplashUser>() {

    override fun getRefreshKey(state: PagingState<Int, UnsplashUser>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UnsplashUser> {
        val position = params.key ?: 1

        return try {
            val response = api.searchUsers(query, position)
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