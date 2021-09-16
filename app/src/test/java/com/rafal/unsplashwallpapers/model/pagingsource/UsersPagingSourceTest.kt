package com.rafal.unsplashwallpapers.model.pagingsource

import androidx.paging.PagingSource
import com.google.common.truth.Truth.assertThat
import com.rafal.unsplashwallpapers.model.FakeUnsplashApi
import com.rafal.unsplashwallpapers.model.UnsplashUserFactory
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

class UsersPagingSourceTest {
    private val mockUsers = listOf(
        UnsplashUserFactory.createUnsplashUser("Mike"),
        UnsplashUserFactory.createUnsplashUser("Jake"),
        UnsplashUserFactory.createUnsplashUser("Mike123"),
        UnsplashUserFactory.createUnsplashUser("Joe"),
        UnsplashUserFactory.createUnsplashUser("Thomas"),
    )

    private val fakeApi = FakeUnsplashApi().apply {
        mockUsers.forEach { user -> this.addUser(user) }
    }

    @Test
    fun `Find users returns Page on successful load`() = runBlockingTest {
        val pagingSource = UsersPagingSource(fakeApi, "Mike")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 2,
                placeholdersEnabled = false
            )
        )

        val expected = PagingSource.LoadResult.Page(
            data = listOf(mockUsers[0], mockUsers[2]),
            prevKey = null,
            nextKey = 2
        )

        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `Find users returns empty page when no users found`() = runBlockingTest {
        val pagingSource = UsersPagingSource(fakeApi, "InvalidUsername")

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