package com.rafal.unsplashwallpapers.model.db.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.UnsplashPhotoUrls
import com.rafal.unsplashwallpapers.model.UnsplashSearchPhoto
import com.rafal.unsplashwallpapers.model.UnsplashUser
import com.rafal.unsplashwallpapers.model.db.AppDatabase
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class UnsplashSearchPhotoDaoTest {
    private lateinit var photoDao: UnsplashSearchPhotoDao
    private lateinit var db: AppDatabase

    private val photos = listOf<UnsplashSearchPhoto>(
        UnsplashSearchPhoto(
            id = "1",
            description = "desc",
            urls = UnsplashPhotoUrls("raw", "full", "regular", "small", "thumb"),
            likes = 5,
            user = UnsplashUser(
                id = "1",
                name = "Jake",
                username = "jake123",
                profile_image = UnsplashUser.ProfileImage("small", "medium", "large"),
                10,
                10,
                10
            )
        ),
        UnsplashSearchPhoto(
            id = "2",
            description = "desc",
            urls = UnsplashPhotoUrls("raw", "full", "regular", "small", "thumb"),
            likes = 5,
            user = UnsplashUser(
                id = "1",
                name = "Mike",
                username = "mike123",
                profile_image = UnsplashUser.ProfileImage("small", "medium", "large"),
                5,
                101,
                1
            )
        )
    )

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        photoDao = db.photoDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAllIntoDb() = runBlockingTest {
        photoDao.insertAll(photos)

        val allPhotos = photoDao.getAll()

        assertThat(allPhotos).isEqualTo(photos)
    }

    @Test
    fun clearAll() = runBlockingTest {
        photoDao.insertAll(photos)
        photoDao.clearAll()

        val allPhotos = photoDao.getAll()

        assertThat(allPhotos).isEmpty()
    }

    @Test
    fun getAll() = runBlockingTest {
        photoDao.insertAll(photos)

        val allPhotos = photoDao.getAll()

        assertThat(allPhotos).isEqualTo(photos)
    }
}