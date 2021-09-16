package com.rafal.unsplashwallpapers.model.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.db.AppDatabase
import com.rafal.unsplashwallpapers.model.db.RemoteKeys
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
class RemoteKeysDaoTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var remoteKeysDao: RemoteKeysDao

    @Inject
    @Named("test_db")
    lateinit var db: AppDatabase

    private val keys = listOf<RemoteKeys>(
        RemoteKeys(repoId = "1", prevKey = 1, nextKey = 3),
        RemoteKeys(repoId = "2", prevKey = 2, nextKey = 4),
    )

    @Before
    fun createDb() {
        hiltRule.inject()
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