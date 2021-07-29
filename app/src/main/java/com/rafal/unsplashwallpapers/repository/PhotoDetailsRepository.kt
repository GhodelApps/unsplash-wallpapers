package com.rafal.unsplashwallpapers.repository

import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashPhoto
import com.rafal.unsplashwallpapers.util.Resource
import retrofit2.awaitResponse
import javax.inject.Inject

class PhotoDetailsRepository @Inject constructor(
    private val api: UnsplashApi
) {
    suspend fun getPhoto(id: String): Resource<UnsplashPhoto> {
        return try {
            val response = api.getPhoto(id).awaitResponse()
            val body = response.body()
            if(response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Fail(response.message())
            }
        } catch(e: Exception) {
            Resource.Fail(e.message ?: "Error")
        }
    }
}