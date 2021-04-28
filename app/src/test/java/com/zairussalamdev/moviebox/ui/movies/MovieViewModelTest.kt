package com.zairussalamdev.moviebox.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.local.entities.MovieEntity
import com.zairussalamdev.moviebox.utils.TestCoroutineRule
import com.zairussalamdev.moviebox.vo.Resource
import com.zairussalamdev.moviebox.vo.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
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
    private lateinit var pagedList: PagedList<MovieEntity>

    @Mock
    private lateinit var listObserver: Observer<Resource<PagedList<MovieEntity>>>

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
            val dummyList = pagedList
            val value = Resource(Status.SUCCESS, dummyList, null)
            val expectation = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            expectation.value = value
            `when`(tmdbRepository.getMovieList()).thenReturn(expectation)

            val result = movieViewModel.getMovieList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(value)
            verify(tmdbRepository).getMovieList()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.data, result.value?.data)
            assertNull(result.value?.message)
        }
    }

    @Test
    fun `get movie list with no data`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "No Data Found"
            val dummyList = pagedList
            val value = Resource(Status.ERROR, dummyList, errorMessage)
            val expectation = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            expectation.value = value
            `when`(tmdbRepository.getMovieList()).thenReturn(expectation)

            val result = movieViewModel.getMovieList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(value)
            verify(tmdbRepository).getMovieList()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.message, result.value?.message)
            assertEquals(expectation.value?.message, errorMessage)
        }
    }

    @Test
    fun `get TV Show list success`() {
        testCoroutineRule.runBlockingTest {
            val dummyList = pagedList
            val value = Resource(Status.SUCCESS, dummyList, null)
            val expectation = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            expectation.value = value
            `when`(tmdbRepository.getTvShowsList()).thenReturn(expectation)

            val result = movieViewModel.getTvShowsList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(value)
            verify(tmdbRepository).getTvShowsList()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.data, result.value?.data)
            assertNull(result.value?.message)
        }
    }

    @Test
    fun `get TV Show list with no data`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "No Data Found"
            val dummyList = pagedList
            val value = Resource(Status.ERROR, dummyList, errorMessage)
            val expectation = MutableLiveData<Resource<PagedList<MovieEntity>>>()
            expectation.value = value
            `when`(tmdbRepository.getTvShowsList()).thenReturn(expectation)

            val result = movieViewModel.getTvShowsList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(value)
            verify(tmdbRepository).getTvShowsList()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.message, result.value?.message)
            assertEquals(expectation.value?.message, errorMessage)
        }
    }
}