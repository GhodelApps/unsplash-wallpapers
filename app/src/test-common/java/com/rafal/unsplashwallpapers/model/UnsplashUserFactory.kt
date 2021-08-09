package com.rafal.unsplashwallpapers.model

class UnsplashUserFactory {
    companion object {
        fun createUnsplashUser(user: String): UnsplashUser {
            return UnsplashUser(
                id = "id_$user",
                name = "name_$user",
                username = "username_$user",
                profile_image = UnsplashUser.ProfileImage(
                    small = "small_$user",
                    medium = "medium_$user",
                    large = "large_$user"
                ),
                total_likes = 5,
                downloads = 10,
                total_photos = 15
            )
        }
    }
}