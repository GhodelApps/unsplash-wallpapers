package com.rafal.unsplashwallpapers.model

import retrofit2.Response

class FakeUnsplashApi : UnsplashApi {
    private val photos = mutableListOf<UnsplashSearchPhoto>()
    private val users = mutableListOf<UnsplashUser>()
    private val collections = mutableListOf<UnsplashCollection>()

    fun addPhoto(photo: UnsplashSearchPhoto) {
        photos.add(photo)
    }

    fun addUser(user: UnsplashUser) {
        users.add(user)
    }

    fun addCollection(collection: UnsplashCollection) {
        collections.add(collection)
    }

    override suspend fun getAllPhotos(
        page: Int,
        orderBy: String
    ): Response<List<UnsplashSearchPhoto>> {
        return Response.success(photos)
    }

    override suspend fun searchPhotos(
        query: String,
        page: Int,
        orderBy: String
    ): Response<UnsplashSearchPhotoResults> {
        return Response.success(UnsplashSearchPhotoResults(results = photos.filter {
            it.id.contains(query)
        }))
    }

    override suspend fun searchUsers(query: String, page: Int): Response<UnsplashUserResults> {
        return Response.success(UnsplashUserResults(results = users.filter {
            it.id.contains(query)
        }))
    }

    override suspend fun searchCollections(
        query: String,
        page: Int
    ): Response<UnsplashCollectionsResults> {
        return Response.success(UnsplashCollectionsResults(results = collections.filter {
            it.id.contains(query)
        }))
    }

    override suspend fun getPhoto(id: String): Response<UnsplashPhoto> {
        TODO("Not yet implemented")
    }

    override suspend fun getUser(username: String): Response<UnsplashUser> {
        return Response.success(users.find {
            it.username == username
        })
    }

    override suspend fun getUserPhotos(
        username: String,
        page: Int
    ): Response<List<UnsplashSearchPhoto>> {
        TODO("Not yet implemented")
    }

    override suspend fun getCollectionPhotos(
        id: String,
        page: Int
    ): Response<List<UnsplashSearchPhoto>> {
        TODO("Not yet implemented")
    }
}