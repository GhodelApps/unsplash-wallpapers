package com.rafal.unsplashwallpapers.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.model.pagingsource.AllPhotosPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllPhotosRepository @Inject constructor(
    private val api: UnsplashApi
) {
    fun getAllPhotos(orderBy: String): Flow<PagingData<UnsplashSearchPhoto>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                maxSize = 100
            )
        ) {
            AllPhotosPagingSource(api, orderBy)
        }.flow
    }
}