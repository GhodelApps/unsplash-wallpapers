package com.rafal.unsplashwallpapers.model.pagingsource

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.FakeUnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashPhotoFactory
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


class PhotosPagingSourceTest {
    private val photoFactory = UnsplashPhotoFactory()

    private val mockPhotos = listOf(
        photoFactory.createUnsplashPhoto(),
        photoFactory.createUnsplashPhoto(),
        photoFactory.createUnsplashPhoto()
    )

    private val mockApi = FakeUnsplashApi().apply {
        mockPhotos.forEach { photo -> this.addPhoto(photo) }
    }

    @Test
    fun `load returns page when on successful load`() = runBlockingTest {
        val pagingSource = PhotosPagingSource(mockApi, "id", "sort")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 3,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = listOf(mockPhotos[0], mockPhotos[1], mockPhotos[2]),
            prevKey = null,
            nextKey = 2
        )

        assertThat(result).isEqualTo(expected)
    }
}