package com.rafal.unsplashwallpapers.model.db.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.db.AppDatabase
import com.rafal.unsplashwallpapers.model.db.RemoteKeys
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class RemoteKeysDaoTest {
    private lateinit var remoteKeysDao: RemoteKeysDao
    private lateinit var db: AppDatabase

    private val keys = listOf<RemoteKeys>(
        RemoteKeys(repoId = "1", prevKey = 1, nextKey = 3),
        RemoteKeys(repoId = "2", prevKey = 2, nextKey = 4),
    )

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        remoteKeysDao = db.remoteKeysDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAllIntoDb() = runBlockingTest {
        remoteKeysDao.insertAll(keys)

        val allKeys = remoteKeysDao.getAll()

        assertThat(allKeys).isEqualTo(keys)
    }

    @Test
    fun remoteKeysRepoId_validID_returnNotNull() = runBlockingTest {
        remoteKeysDao.insertAll(keys)

        val result = remoteKeysDao.remoteKeysRepoId(keys[0].repoId)

        assertThat(result).isNotNull()
    }

    @Test
    fun remoteKeysRepoId_invalidID_returnNull() = runBlockingTest {
        remoteKeysDao.insertAll(keys)

        val result = remoteKeysDao.remoteKeysRepoId("invalidID")

        assertThat(result).isNull()
    }

    @Test
    fun clearRemoteKeys() = runBlockingTest {
        remoteKeysDao.insertAll(keys)
        remoteKeysDao.clearRemoteKeys()

        val result = remoteKeysDao.getAll()

        assertThat(result).isEmpty()
    }

}