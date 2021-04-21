package com.zairussalamdev.moviebox.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.local.entities.DetailEntity
import com.zairussalamdev.moviebox.utils.DummyData
import com.zairussalamdev.moviebox.utils.TestCoroutineRule
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.HttpException
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
@ExperimentalCoroutinesApi
class DetailViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var tmdbRepository: TMDBRepository

    @Mock
    private lateinit var observer: Observer<DetailEntity>

    @Mock
    private lateinit var errorObserver: Observer<String>

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var responseHttpError: HttpException


    @Before
    fun init() {
        detailViewModel = DetailViewModel(tmdbRepository)
        val responseBody = Response.error<Error>(500, ResponseBody.create(null, "".toByteArray()))
        responseHttpError = HttpException(responseBody)
    }

    @Test
    fun `get movie detail success`() {
        testCoroutineRule.runBlockingTest {
            val movieId = 1
            val dummyDetail = DummyData.getDummyDetailData()
            `when`(tmdbRepository.getMovieDetail(movieId)).thenReturn(dummyDetail)
            val result = detailViewModel.getMovieDetail(movieId)
            assertNotNull(result)
            verify(tmdbRepository).getMovieDetail(movieId)
            detailViewModel.getMovieDetail(movieId).observeForever(observer)
            observer.onChanged(dummyDetail)
        }
    }

    @Test
    fun `get movie detail with HTTP error`() {
        testCoroutineRule.runBlockingTest {
            val movieId = 1
            val expectedError = "HTTP Error"
            `when`(tmdbRepository.getMovieDetail(movieId)).thenThrow(responseHttpError)
            val result = detailViewModel.getMovieDetail(movieId)
            assertNotNull(result)
            verify(tmdbRepository).getMovieDetail(movieId)
            detailViewModel.getErrorMessage().observeForever(errorObserver)
            Mockito.verify(errorObserver).onChanged(expectedError)
        }
    }

    @Test
    fun `get tv show detail success`() {
        testCoroutineRule.runBlockingTest {
            val movieId = 1
            val dummyDetail = DummyData.getDummyDetailData()
            `when`(tmdbRepository.getTvShowDetail(movieId)).thenReturn(dummyDetail)
            val result = detailViewModel.getTvShowDetail(movieId)
            assertNotNull(result)
            verify(tmdbRepository).getTvShowDetail(movieId)
            detailViewModel.getTvShowDetail(movieId).observeForever(observer)
            observer.onChanged(dummyDetail)
        }
    }

    @Test
    fun `get tv show detail with HTTP error`() {
        testCoroutineRule.runBlockingTest {
            val movieId = 1
            val expectedError = "HTTP Error"
            `when`(tmdbRepository.getTvShowDetail(movieId)).thenThrow(responseHttpError)
            val result = detailViewModel.getTvShowDetail(movieId)
            assertNotNull(result)
            verify(tmdbRepository).getTvShowDetail(movieId)
            detailViewModel.getErrorMessage().observeForever(errorObserver)
            Mockito.verify(errorObserver).onChanged(expectedError)
        }
    }
}