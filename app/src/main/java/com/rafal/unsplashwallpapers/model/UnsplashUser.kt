package com.rafal.unsplashwallpapers.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user")
data class UnsplashUser(
    @PrimaryKey @ColumnInfo(name = "user_id") val id: String,
    val name: String,
    val username: String,
    @Embedded(prefix = "user_profile_image_") val profile_image: ProfileImage,
    val total_likes: Int,
    val downloads: Int,
    val total_photos: Int
) {
    data class ProfileImage(
        val small: String,
        val medium: String,
        val large: String
    )
}