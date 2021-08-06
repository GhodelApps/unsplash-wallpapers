package com.rafal.unsplashwallpapers.model.db.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.UnsplashPhotoFactory
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

    private val photoFactory = UnsplashPhotoFactory()

    private val photos = listOf(
        photoFactory.createUnsplashPhoto(),
        photoFactory.createUnsplashPhoto()
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