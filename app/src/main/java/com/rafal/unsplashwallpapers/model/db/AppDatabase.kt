package com.rafal.unsplashwallpapers.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.model.UnsplashUser
import com.rafal.unsplashwallpapers.model.db.dao.RemoteKeysDao
import com.rafal.unsplashwallpapers.model.db.dao.UnsplashSearchPhotoDao

@Database(
    entities = [UnsplashUser::class, UnsplashSearchPhoto::class, RemoteKeys::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun photoDao(): UnsplashSearchPhotoDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}