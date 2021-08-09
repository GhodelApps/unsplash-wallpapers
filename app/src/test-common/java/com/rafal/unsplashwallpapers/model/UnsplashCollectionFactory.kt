package com.rafal.unsplashwallpapers.model

class UnsplashCollectionFactory {
    companion object {
        fun createUnsplashCollection(id: String) : UnsplashCollection {
            return UnsplashCollection(
                title = id,
                id = id,
                cover_photo = UnsplashCollection.CollectionCoverPhoto(
                    UnsplashPhotoUrlsFactory.createUnsplashPhotoUrls(id),
                    UnsplashUserFactory.createUnsplashUser(id)
                ),
                total_photos = 10
            )
        }
    }
}