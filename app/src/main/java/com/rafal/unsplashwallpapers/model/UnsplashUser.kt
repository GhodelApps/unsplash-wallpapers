package com.rafal.unsplashwallpapers.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UnsplashUser(
    val id: String,
    val name: String,
    val profile_image: ProfileImage
) : Parcelable {
    @Parcelize
    data class ProfileImage(
        val small: String,
        val medium: String,
        val large: String
    ) : Parcelable
}