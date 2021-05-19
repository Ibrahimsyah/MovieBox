package com.zairussalamdev.moviebox.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.core.data.source.local.LocalDataSource
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import com.zairussalamdev.moviebox.core.data.source.remote.RemoteDataSource
import com.zairussalamdev.moviebox.core.utils.DummyData
import com.zairussalamdev.moviebox.core.utils.LiveDataTestUtil
import com.zairussalamdev.moviebox.core.utils.PagedListUtil
import com.zairussalamdev.moviebox.core.utils.TestCoroutineRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
            val data = DummyData.getDummyListData()
            val dataSourceFactory =
                mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
            `when`(localDataSource.getMovies()).thenReturn(dataSourceFactory)
            tmdbRepository.getMovieList()

            val result = Resource.success(PagedListUtil.mockPagedList(data))
            verify(localDataSource).getMovies()
            assertNotNull(result.data)
        }
    }

    @Test
    fun `get tv show list success`() {
        testCoroutineRule.runBlockingTest {
            val data = DummyData.getDummyListData()
            val dataSourceFactory =
                mock(DataSource.Factory::class.java) as DataSource.Factory<Int, MovieEntity>
            `when`(localDataSource.getTvShows()).thenReturn(dataSourceFactory)
            tmdbRepository.getTvShowsList()

            val result = Resource.success(PagedListUtil.mockPagedList(data))
            verify(localDataSource).getTvShows()
            assertNotNull(result.data)
        }
    }

    @Test
    fun `get movie detail success`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            val response = DummyData.getDummyDetailData()
            val expectation = MutableLiveData<DetailEntity>()
            expectation.value = response
            `when`(localDataSource.getMovieDetail(id)).thenReturn(expectation)

            val result = tmdbRepository.getMovieDetail(id)
            assertNotNull(result)

            verify(localDataSource).getMovieDetail(id)
        }
    }

    @Test
    fun `get tv show detail success`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            val response = DummyData.getDummyDetailData()
            val expectation = MutableLiveData<DetailEntity>()
            expectation.value = response
            `when`(localDataSource.getTvShowDetail(id)).thenReturn(expectation)

            val result = tmdbRepository.getTvShowDetail(id)
            assertNotNull(result)

            verify(localDataSource).getTvShowDetail(id)
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
    fun `add a movie to favorite`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            `when`(localDataSource.addFavoriteMovie(id)).thenReturn(Unit)
            tmdbRepository.insertFavoriteMovie(id)

            verify(localDataSource).addFavoriteMovie(id)
        }
    }

    @Test
    fun `remove a movie from favorite`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            `when`(localDataSource.deleteFavoriteMovie(id)).thenReturn(Unit)
            tmdbRepository.deleteFavoriteMovie(id)

            verify(localDataSource).deleteFavoriteMovie(id)
        }
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

        assertEquals(expected, result)
    }
}