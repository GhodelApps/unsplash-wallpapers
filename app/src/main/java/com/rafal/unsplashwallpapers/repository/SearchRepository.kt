package com.rafal.unsplashwallpapers.repository

import com.rafal.unsplashwallpapers.model.UnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashCollectionsResults
import com.rafal.unsplashwallpapers.model.UnsplashPhotoResults
import com.rafal.unsplashwallpapers.model.UnsplashUserResults
import com.rafal.unsplashwallpapers.util.Resource
import retrofit2.awaitResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: UnsplashApi
){
    suspend fun searchPhotos(query: String): Resource<UnsplashPhotoResults> {
        return try {
            val apiResponse = api.searchPhotos(query).awaitResponse()
            val apiResponseBody = apiResponse.body()

            if(apiResponse.isSuccessful && apiResponseBody != null) {
                Resource.Success(apiResponseBody)
            } else {
                Resource.Fail(apiResponse.message())
            }
        } catch(e: Exception) {
            Resource.Fail(e.message ?: "Error")
        }
    }

    suspend fun searchUsers(query: String): Resource<UnsplashUserResults> {
        return try {
            val apiResponse = api.searchUsers(query).awaitResponse()
            val apiResponseBody = apiResponse.body()

            if(apiResponse.isSuccessful && apiResponseBody != null) {
                Resource.Success(apiResponseBody)
            } else {
                Resource.Fail(apiResponse.message())
            }
        } catch(e: Exception) {
            Resource.Fail(e.message ?: "Error")
        }
    }

    suspend fun searchCollections(query: String): Resource<UnsplashCollectionsResults> {
        return try {
            val apiResponse = api.searchCollections(query).awaitResponse()
            val apiResponseBody = apiResponse.body()

            if(apiResponse.isSuccessful && apiResponseBody != null) {
                Resource.Success(apiResponseBody)
            } else {
                Resource.Fail(apiResponse.message())
            }
        } catch (e: Exception) {
            Resource.Fail(e.message ?: "Error")
        }
    }
}