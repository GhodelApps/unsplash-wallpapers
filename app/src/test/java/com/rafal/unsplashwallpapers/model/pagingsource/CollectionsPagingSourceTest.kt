package com.rafal.unsplashwallpapers.model.pagingsource

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.FakeUnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashCollectionFactory
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class CollectionsPagingSourceTest {
    private val mockCollections = listOf(
        UnsplashCollectionFactory.createUnsplashCollection("Car"),
        UnsplashCollectionFactory.createUnsplashCollection("Cat"),
        UnsplashCollectionFactory.createUnsplashCollection("Car_Audi"),
        UnsplashCollectionFactory.createUnsplashCollection("Dogs"),
        UnsplashCollectionFactory.createUnsplashCollection("Wallpaper"),
    )

    private val fakeAPI = FakeUnsplashApi().apply {
        mockCollections.forEach { collection -> this.addCollection(collection) }
    }

    @Test
    fun `Search collection return Page when successful load`() = runBlockingTest {
        val pagingSource = CollectionsPagingSource(fakeAPI, "Car")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = listOf(mockCollections[0], mockCollections[2]),
            prevKey = null,
            nextKey = 2
        )

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Search with not existing collection return empty Page`() = runBlockingTest {
        val pagingSource = CollectionsPagingSource(fakeAPI, "InvalidCollection")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = null
        )

        assertThat(result).isEqualTo(expected)
    }
}