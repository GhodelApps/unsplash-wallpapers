package com.rafal.unsplashwallpapers.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto

@Dao
interface UnsplashSearchPhotoDao {
    @Insert
    fun insertAll(vararg users: UnsplashSearchPhoto)

    @Delete
    fun delete(user: UnsplashSearchPhoto)

    @Query("SELECT * FROM search_photo")
    fun getAll(): List<UnsplashSearchPhoto>
}