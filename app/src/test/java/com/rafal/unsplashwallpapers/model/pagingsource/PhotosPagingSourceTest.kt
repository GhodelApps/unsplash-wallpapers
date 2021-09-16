package com.rafal.unsplashwallpapers.model.pagingsource

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.FakeUnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashPhotoFactory
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


class PhotosPagingSourceTest {
    private val mockPhotos = listOf(
        UnsplashPhotoFactory.createUnsplashPhoto("Mike"),
        UnsplashPhotoFactory.createUnsplashPhoto("Jake"),
        UnsplashPhotoFactory.createUnsplashPhoto("Mike123")
    )

    private val fakeApi = FakeUnsplashApi().apply {
        mockPhotos.forEach { photo -> this.addPhoto(photo) }
    }

    @Test
    fun `Find photos returns page on successful load`() = runBlockingTest {
        val pagingSource = PhotosPagingSource(fakeApi, "Mike", "sort")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = listOf(mockPhotos[0], mockPhotos[2]),
            prevKey = null,
            nextKey = 2
        )

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Find photos returns empty page when no photos found`() = runBlockingTest {
        val pagingSource = UsersPagingSource(fakeApi, "InvalidPhotoID")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
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