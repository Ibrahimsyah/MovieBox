package com.zairussalamdev.moviebox.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.zairussalamdev.moviebox.core.data.Resource
import com.zairussalamdev.moviebox.core.domain.model.Movie
import com.zairussalamdev.moviebox.core.domain.usecase.MovieUseCase
import com.zairussalamdev.moviebox.core.utils.DummyData
import com.zairussalamdev.moviebox.core.utils.TestCoroutineRule
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
    private lateinit var movieInteractor: MovieUseCase

    @Mock
    private lateinit var listObserver: Observer<Resource<List<Movie>>>

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var responseHttpError: HttpException

    @Before
    fun init() {
        movieViewModel = MovieViewModel(movieInteractor)
        val responseBody = Response.error<Error>(500, ResponseBody.create(null, "".toByteArray()))
        responseHttpError = HttpException(responseBody)
    }

    @Test
    fun `get movie list success`() {
        testCoroutineRule.runBlockingTest {
            val dummyData = Resource.Success(DummyData.getDummyListData())
            val expectation = MutableLiveData<Resource<List<Movie>>>()
            expectation.value = dummyData
            `when`(movieInteractor.getAllMovies()).thenReturn(expectation)

            val result = movieViewModel.getMovieList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(dummyData)
            verify(movieInteractor).getAllMovies()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.data, result.value?.data)
            assertNull(result.value?.message)
        }
    }

    @Test
    fun `get movie list with no data`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "No Data"
            val dummyData = Resource.Error(errorMessage, listOf<Movie>())
            val expectation = MutableLiveData<Resource<List<Movie>>>()
            expectation.value = dummyData
            `when`(movieInteractor.getAllMovies()).thenReturn(expectation)

            val result = movieViewModel.getMovieList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(dummyData)
            verify(movieInteractor).getAllMovies()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.message, result.value?.message)
            assertEquals(expectation.value?.message, errorMessage)
        }
    }

    @Test
    fun `get TV Show list success`() {
        testCoroutineRule.runBlockingTest {
            val dummyData = Resource.Success(DummyData.getDummyListData())
            val expectation = MutableLiveData<Resource<List<Movie>>>()
            expectation.value = dummyData
            `when`(movieInteractor.getAllTvShows()).thenReturn(expectation)

            val result = movieViewModel.getTvShowsList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(dummyData)
            verify(movieInteractor).getAllTvShows()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.data, result.value?.data)
            assertNull(result.value?.message)
        }
    }

    @Test
    fun `get TV Show list with no data`() {
        testCoroutineRule.runBlockingTest {
            val errorMessage = "No Data"
            val dummyData = Resource.Error(errorMessage, listOf<Movie>())
            val expectation = MutableLiveData<Resource<List<Movie>>>()
            expectation.value = dummyData
            `when`(movieInteractor.getAllTvShows()).thenReturn(expectation)

            val result = movieViewModel.getTvShowsList()
            result.observeForever(listObserver)
            verify(listObserver).onChanged(dummyData)
            verify(movieInteractor).getAllTvShows()

            assertEquals(expectation.value, result.value)
            assertEquals(expectation.value?.message, result.value?.message)
            assertEquals(expectation.value?.message, errorMessage)
        }
    }
}