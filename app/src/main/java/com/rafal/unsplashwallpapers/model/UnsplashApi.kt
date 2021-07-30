package com.rafal.unsplashwallpapers.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    suspend fun getAllPhotos(
        @Query("page") page: Int,
        @Query("order_by") orderBy: String
    ): Response<List<UnsplashSearchPhoto>>

    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("order_by") orderBy: String
    ): Response<UnsplashSearchPhotoResults>

    @GET("search/users")
    suspend fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<UnsplashUserResults>

    @GET("search/collections")
    suspend fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Response<UnsplashCollectionsResults>

    @GET("photos/{id}")
    suspend fun getPhoto(
        @Path("id") id: String
    ): Response<UnsplashPhoto>

    @GET("users/{username}")
    suspend fun getUser(
        @Path("username") username: String
    ): Response<UnsplashUser>

    @GET("users/{username}/photos")
    suspend fun getUserPhotos(
        @Path("username") username: String,
        @Query("page") page: Int
    ): Response<List<UnsplashSearchPhoto>>

    @GET("collections/{id}/photos")
    suspend fun getCollectionPhotos(
        @Path("id") id: String,
        @Query("page") page: Int
    ): Response<List<UnsplashSearchPhoto>>
}