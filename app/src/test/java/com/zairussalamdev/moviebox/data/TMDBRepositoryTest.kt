package com.zairussalamdev.moviebox.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.data.local.LocalDataSource
import com.zairussalamdev.moviebox.data.remote.RemoteDataSource
import com.zairussalamdev.moviebox.utils.DummyData
import com.zairussalamdev.moviebox.utils.TestCoroutineRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class TMDBRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var remoteDataSource: RemoteDataSource

    @Mock
    private lateinit var localDataSource: LocalDataSource

    private lateinit var tmdbRepository: TMDBRepository

    @Before
    fun init() {
        tmdbRepository = TMDBRepository(remoteDataSource, localDataSource)
    }

    @Test
    fun `get movie list success`() {
        testCoroutineRule.runBlockingTest {
            val response = DummyData.getDummyMovieListResponse()
            `when`(remoteDataSource.getMovieList()).thenReturn(response)
            val result = tmdbRepository.getMovieList()
            assertNotNull(result)
            verify(remoteDataSource).getMovieList()
            assertEquals(response.movies.size, result.size)
        }
    }

    @Test
    fun `get tv show list success`() {
        testCoroutineRule.runBlockingTest {
            val response = DummyData.getDummyTvShowListResponse()
            `when`(remoteDataSource.getTvShowList()).thenReturn(response)
            val result = tmdbRepository.getTvShowsList()
            assertNotNull(result)
            verify(remoteDataSource).getTvShowList()
            assertEquals(response.tvShows.size, result.size)
        }
    }

    @Test
    fun `get movie detail success`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            val response = DummyData.getDummyMovieDetailResponse()
            `when`(remoteDataSource.getMovieDetail(id)).thenReturn(response)
            val result = tmdbRepository.getMovieDetail(id)
            assertNotNull(result)
            verify(remoteDataSource).getMovieDetail(id)
            assertEquals(response.title, result.title)
        }
    }

    @Test
    fun `get tv show detail success`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            val response = DummyData.getDummyTvShowDetailResponse()
            `when`(remoteDataSource.getTvShowDetail(id)).thenReturn(response)
            val result = tmdbRepository.getTvShowDetail(id)
            assertNotNull(result)
            verify(remoteDataSource).getTvShowDetail(id)
            assertEquals(response.title, result.title)
        }
    }
}