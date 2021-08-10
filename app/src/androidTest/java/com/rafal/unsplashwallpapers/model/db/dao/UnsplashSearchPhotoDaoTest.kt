package com.rafal.unsplashwallpapers.model.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.UnsplashPhotoFactory
import com.rafal.unsplashwallpapers.model.db.AppDatabase
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class UnsplashSearchPhotoDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var photoDao: UnsplashSearchPhotoDao

    @Inject
    @Named("test_db")

    lateinit var db: AppDatabase

    private val photoFactory = UnsplashPhotoFactory()

    private val photos = listOf(
        UnsplashPhotoFactory.createUnsplashPhoto("1"),
        UnsplashPhotoFactory.createUnsplashPhoto("2")
    )

    @Before
    fun createDb() {
        hiltRule.inject()
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