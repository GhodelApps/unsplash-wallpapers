package com.rafal.unsplashwallpapers.model

class UnsplashPhotoUrlsFactory {
    companion object {
        fun createUnsplashPhotoUrls(url: String): UnsplashPhotoUrls {
            return UnsplashPhotoUrls(
                raw = "raw_$url",
                full = "full_$url",
                regular = "regular_$url",
                small = "small_$url",
                thumb = "thumb_$url"
            )
        }
    }
}