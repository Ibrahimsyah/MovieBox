package com.zairussalamdev.moviebox.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.entities.DetailEntity
import com.zairussalamdev.moviebox.utils.DummyData
import com.zairussalamdev.moviebox.utils.TestCoroutineRule
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
class DetailViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var tmdbRepository: TMDBRepository

    @Mock
    private lateinit var observer: Observer<DetailEntity>

    private lateinit var detailViewModel: DetailViewModel

    @Before
    fun init() {
        detailViewModel = DetailViewModel(tmdbRepository)
    }

    @Test
    fun getMovieDetail() {
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
    fun getTvShowDetail() {
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
}