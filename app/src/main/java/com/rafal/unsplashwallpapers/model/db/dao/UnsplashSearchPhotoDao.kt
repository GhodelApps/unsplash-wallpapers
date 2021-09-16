package com.rafal.unsplashwallpapers.model.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto

@Dao
interface UnsplashSearchPhotoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(photos: List<UnsplashSearchPhoto>)

    @Query("DELETE FROM search_photo")
    suspend fun clearAll()

    @Query("SELECT * FROM search_photo")
    fun getAll(): List<UnsplashSearchPhoto>

    @Query("SELECT * FROM search_photo")
    fun pagingSource(): PagingSource<Int, UnsplashSearchPhoto>
}