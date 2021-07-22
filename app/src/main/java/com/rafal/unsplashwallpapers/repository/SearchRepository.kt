package com.rafal.unsplashwallpapers.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.rafal.unsplashwallpapers.model.*
import com.rafal.unsplashwallpapers.util.Resource
import retrofit2.awaitResponse
import javax.inject.Inject

class SearchRepository @Inject constructor(
    private val api: UnsplashApi
){
    suspend fun searchPhotos(query: String): Resource<UnsplashPhotoResults> {
        return try {
            val apiResponse = api.searchPhotos(query, 1).awaitResponse()
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

    fun searchPhotosPaging(query: String): LiveData<PagingData<UnsplashPhoto>> {
        return Pager(
            PagingConfig(
                enablePlaceholders = false,
                pageSize = 10,
                maxSize = 100
            )
        ) {
            UnsplashPhotoPagingSource(api, query)
        }.liveData
    }

    suspend fun searchUsers(query: String): Resource<UnsplashUserResults> {
        return try {
            val apiResponse = api.searchUsers(query, 1).awaitResponse()
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
            val apiResponse = api.searchCollections(query, 1).awaitResponse()
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