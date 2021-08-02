package com.rafal.unsplashwallpapers.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.rafal.unsplashwallpapers.model.UnsplashUser

@Dao
interface UnsplashUserDao {
    @Insert
    fun insertAll(vararg users: UnsplashUser)

    @Delete
    fun delete(user: UnsplashUser)

    @Query("SELECT * FROM user")
    fun getAll(): List<UnsplashUser>
}