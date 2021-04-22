package com.zairussalamdev.moviebox.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.data.local.LocalDataSource
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.data.remote.RemoteDataSource
import com.zairussalamdev.moviebox.utils.DummyData
import com.zairussalamdev.moviebox.utils.LiveDataTestUtil
import com.zairussalamdev.moviebox.utils.PagedListUtil
import com.zairussalamdev.moviebox.utils.TestCoroutineRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@Suppress("UNCHECKED_CAST")
@ExperimentalCoroutinesApi
class TMDBRepositoryTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private var remoteDataSource = mock(RemoteDataSource::class.java)

    private var localDataSource = mock(LocalDataSource::class.java)

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

    @Test
    fun `get favorite movies list`() {
        val response = DummyData.getDummyListData()
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getFavoriteMovies()).thenReturn(dataSourceFactory)
        tmdbRepository.getFavoriteMovies()
        val result = PagedListUtil.mockPagedList(response)
        verify(localDataSource).getFavoriteMovies()
        assertNotNull(result)
        assertEquals(response.size, result.size)
    }

    @Test
    fun `get favorite tv shows list`() {
        val response = DummyData.getDummyListData()
        val dataSourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
        `when`(localDataSource.getFavoriteTvShows()).thenReturn(dataSourceFactory)
        tmdbRepository.getFavoriteTvShows()
        val result = PagedListUtil.mockPagedList(response)
        verify(localDataSource).getFavoriteTvShows()
        assertNotNull(result)
        assertEquals(response.size, result.size)
    }

    @Test
    fun checkMovieFavorite() {
        val expected = false
        val movieId = 1
        val isFavorite = MutableLiveData<Boolean>()
        isFavorite.value = false
        `when`(localDataSource.checkMovieFavorite(movieId)).thenReturn(isFavorite)
        val result = LiveDataTestUtil.getValue(tmdbRepository.checkMovieFavorite(movieId))
        verify(localDataSource).checkMovieFavorite(movieId)

        Assert.assertEquals(expected, result)
    }
}