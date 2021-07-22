package com.rafal.unsplashwallpapers.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("search/photos")
    fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Call<UnsplashPhotoResults>

    @GET("search/users")
    fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Call<UnsplashUserResults>

    @GET("search/collections")
    fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int
    ) : Call<UnsplashCollectionsResults>
}