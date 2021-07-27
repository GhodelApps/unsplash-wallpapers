package com.rafal.unsplashwallpapers.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashPhoto
import com.rafal.unsplashwallpapers.model.UnsplashUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: UnsplashApi
) {

    fun searchPhotos(query: String, orderBy: String): Flow<PagingData<UnsplashPhoto>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                maxSize = 100
            )
        ) {
            PhotosPagingSource(api, query, orderBy)
        }.flow
    }

    fun searchUsers(query: String): Flow<PagingData<UnsplashUser>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                maxSize = 100
            )
        ) {
            UsersPagingSource(api, query)
        }.flow
    }

}