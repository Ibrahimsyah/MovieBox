package com.zairussalamdev.moviebox.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.core.data.Resource
import com.zairussalamdev.moviebox.core.data.TMDBRepository
import com.zairussalamdev.moviebox.core.data.source.local.entities.DetailEntity
import com.zairussalamdev.moviebox.core.utils.DummyData
import com.zairussalamdev.moviebox.core.utils.TestCoroutineRule
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
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
    private lateinit var observer: Observer<Resource<DetailEntity>>

    @Mock
    private lateinit var isFavoriteObserver: Observer<Boolean>

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var responseHttpError: HttpException

    private var dummyDetail = DummyData.getDummyDetailData()
    private var dummyEntity = DummyData.getDummyListData()[0]

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
            val resource = Resource.success(dummyDetail)
            val expectation = MutableLiveData<Resource<DetailEntity>>()
            expectation.value = resource
            `when`(tmdbRepository.getMovieDetail(movieId)).thenReturn(expectation)

            val result = detailViewModel.getMovieDetail(movieId)
            result.observeForever(observer)
            verify(tmdbRepository).getMovieDetail(movieId)

            assertNotNull(result)
            observer.onChanged(resource)
        }
    }

    @Test
    fun `get tv show detail success`() {
        testCoroutineRule.runBlockingTest {
            val movieId = 1
            val resource = Resource.success(dummyDetail)
            val expectation = MutableLiveData<Resource<DetailEntity>>()
            expectation.value = resource
            `when`(tmdbRepository.getTvShowDetail(movieId)).thenReturn(expectation)

            val result = detailViewModel.getTvShowDetail(movieId)
            assertNotNull(result)

            verify(tmdbRepository).getTvShowDetail(movieId)
            detailViewModel.getTvShowDetail(movieId).observeForever(observer)
            observer.onChanged(resource)
        }
    }

    @Test
    fun `check if the movie is favorite`() {
        val movieId = 1
        val expectation = MutableLiveData<Boolean>()
        expectation.value = false
        `when`(tmdbRepository.checkMovieFavorite(movieId)).thenReturn(expectation)

        val result = detailViewModel.checkIsMovieFavorite(movieId)
        result.observeForever(isFavoriteObserver)
        verify(tmdbRepository).checkMovieFavorite(movieId)
        verify(isFavoriteObserver).onChanged(expectation.value)

        assertNotNull(result.value)
        assertEquals(expectation.value, result.value)
    }

    @Test
    fun `add a movie to favorite`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            `when`(tmdbRepository.insertFavoriteMovie(id)).thenReturn(Unit)
            detailViewModel.addMovieToFavorite(dummyEntity)
            verify(tmdbRepository).insertFavoriteMovie(id)
        }
    }

    @Test
    fun `remove a movie from favorite`() {
        testCoroutineRule.runBlockingTest {
            val id = 1
            `when`(tmdbRepository.deleteFavoriteMovie(id)).thenReturn(Unit)
            detailViewModel.deleteMovieFromFavorite(dummyEntity)
            verify(tmdbRepository).deleteFavoriteMovie(id)
        }
    }
}