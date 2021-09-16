package com.rafal.unsplashwallpapers.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.model.UnsplashUser
import com.rafal.unsplashwallpapers.model.pagingsource.UserPhotosPagingSource
import com.rafal.unsplashwallpapers.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDetailsRepository @Inject constructor(
    private val api: UnsplashApi
) {
    suspend fun getUser(username: String): Resource<UnsplashUser> {
        return try {
            val response = api.getUser(username)
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Fail(response.message())
            }
        } catch (e: Exception) {
            Resource.Fail(e.message ?: "Error")
        }
    }

    fun getUserPhotos(username: String): Flow<PagingData<UnsplashSearchPhoto>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                maxSize = 100
            )
        ) {
            UserPhotosPagingSource(api, username)
        }.flow
    }
}