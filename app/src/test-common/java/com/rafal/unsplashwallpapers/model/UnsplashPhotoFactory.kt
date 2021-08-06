package com.rafal.unsplashwallpapers.model

import java.util.concurrent.atomic.AtomicInteger

class UnsplashPhotoFactory {
    private val counter = AtomicInteger(0)

    fun createUnsplashPhoto(): UnsplashSearchPhoto {
        val id = counter.incrementAndGet()
        return UnsplashSearchPhoto(
            id = "id_$id",
            description = "description_$id",
            urls = UnsplashPhotoUrls(
                raw = "raw_$id",
                full = "full_$id",
                regular = "regular_$id",
                small = "small_$id",
                thumb = "thumb_$id"
            ),
            likes = id,
            user = UnsplashUser(
                id = "id_$id",
                name = "name_$id",
                username = "username_$id",
                profile_image = UnsplashUser.ProfileImage(
                    small = "small_$id",
                    medium = "medium_$id",
                    large = "large_$id"
                ),
                total_likes = id,
                downloads = id,
                total_photos = id
            )
        )
    }
}