package com.zairussalamdev.moviebox.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.utils.DummyData
import com.zairussalamdev.moviebox.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class MovieViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var tmdbRepository: TMDBRepository

    @Mock
    private lateinit var listObserver: Observer<List<MovieEntity>>

    @Mock
    private lateinit var errorObserver: Observer<String>

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var responseHttpError: HttpException

    @Before
    fun init() {
        movieViewModel = MovieViewModel(tmdbRepository)
        val responseBody = Response.error<Error>(500, ResponseBody.create(null, "".toByteArray()))
        responseHttpError = HttpException(responseBody)
    }

    @Test
    fun `get movie list success`() {
        testCoroutineRule.runBlockingTest {
            val dummyList = DummyData.getDummyListData()
            `when`(tmdbRepository.getMovieList()).thenReturn(dummyList)
            val movies = tmdbRepository.getMovieList()
            assertNotNull(movies)
            verify(tmdbRepository).getMovieList()
            movieViewModel.getMovieList().observeForever(listObserver)
            verify(listObserver).onChanged(dummyList)
        }
    }

    @Test
    fun `get movie list with no data`() {
        testCoroutineRule.runBlockingTest {
            val expectedError = "No Data Found"
            `when`(tmdbRepository.getMovieList()).thenReturn(listOf())
            val movies = movieViewModel.getMovieList()
            assertNotNull(movies)
            verify(tmdbRepository).getMovieList()
            movieViewModel.getErrorMessage().observeForever(errorObserver)
            verify(errorObserver).onChanged(expectedError)
        }
    }

    @Test
    fun `get movie list with HTTP error`() {
        testCoroutineRule.runBlockingTest {
            val expectedError = "HTTP Error"
            `when`(tmdbRepository.getMovieList()).thenThrow(responseHttpError)
            val movies = movieViewModel.getMovieList()
            assertNotNull(movies)
            verify(tmdbRepository).getMovieList()
            movieViewModel.getErrorMessage().observeForever(errorObserver)
            verify(errorObserver).onChanged(expectedError)
        }
    }


    @Test
    fun `get TV Show list success`() {
        testCoroutineRule.runBlockingTest {
            val dummyList = DummyData.getDummyListData()
            `when`(tmdbRepository.getTvShowsList()).thenReturn(dummyList)
            val tvShows = tmdbRepository.getTvShowsList()
            assertNotNull(tvShows)
            verify(tmdbRepository).getTvShowsList()
            movieViewModel.getTvShowsList().observeForever(listObserver)
            verify(listObserver).onChanged(dummyList)
        }
    }

    @Test
    fun `get TV Show list with no data`() {
        testCoroutineRule.runBlockingTest {
            val expectedError = "No Data Found"
            `when`(tmdbRepository.getTvShowsList()).thenReturn(listOf())
            val tvShows = movieViewModel.getTvShowsList()
            assertNotNull(tvShows)
            verify(tmdbRepository).getTvShowsList()
            movieViewModel.getErrorMessage().observeForever(errorObserver)
            verify(errorObserver).onChanged(expectedError)
        }
    }

    @Test
    fun `get TV Show list with HTTP error`() {
        testCoroutineRule.runBlockingTest {
            val expectedError = "HTTP Error"
            `when`(tmdbRepository.getTvShowsList()).thenThrow(responseHttpError)
            val movies = movieViewModel.getTvShowsList()
            assertNotNull(movies)
            verify(tmdbRepository).getTvShowsList()
            movieViewModel.getErrorMessage().observeForever(errorObserver)
            verify(errorObserver).onChanged(expectedError)
        }
    }
}