package com.rafal.unsplashwallpapers.model

class UnsplashPhotoFactory {

    fun createUnsplashPhoto(value: String): UnsplashSearchPhoto {
        return UnsplashSearchPhoto(
            id = "id_$value",
            description = "description_$value",
            urls = UnsplashPhotoUrlsFactory.createUnsplashPhotoUrls(value),
            likes = 10,
            UnsplashUserFactory.createUnsplashUser(value)
        )
    }
}