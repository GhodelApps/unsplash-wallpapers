package com.rafal.unsplashwallpapers.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("search/photos")
    fun searchPhotos(@Query("query") query: String) : Call<UnsplashResults>
}