package com.rafal.unsplashwallpapers.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("search/photos")
    fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("order_by") orderBy: String
    ): Call<UnsplashSearchPhotoResults>

    @GET("search/users")
    fun searchUsers(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<UnsplashUserResults>

    @GET("search/collections")
    fun searchCollections(
        @Query("query") query: String,
        @Query("page") page: Int
    ): Call<UnsplashCollectionsResults>

    @GET("photos/{id}")
    fun getPhoto(
        @Path("id") id: String
    ) : Call<UnsplashPhoto>

    @GET("users/{username}")
    fun getUser(
        @Path("username") username: String
    ) : Call<UnsplashUser>

    @GET("/users/{username}/photos")
    fun getUserPhotos(
        @Path("username") username: String,
        @Query("page") page: Int
    ) : Call<List<UnsplashSearchPhoto>>
}