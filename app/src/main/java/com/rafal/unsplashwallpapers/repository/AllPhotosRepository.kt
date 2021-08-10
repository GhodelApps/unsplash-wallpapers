package com.rafal.unsplashwallpapers.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.model.db.AppDatabase
import com.rafal.unsplashwallpapers.model.remotemediators.AllPhotosRemoteMediator
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AllPhotosRepository @Inject constructor(
    private val api: UnsplashApi,
    private val db: AppDatabase
) {
    @ExperimentalPagingApi
    fun getAllPhotos(orderBy: String): Flow<PagingData<UnsplashSearchPhoto>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false
            ),
            remoteMediator = AllPhotosRemoteMediator(orderBy, db, api)
        ) {
            db.photoDao().pagingSource()
        }.flow
    }
}