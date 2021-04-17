package com.zairussalamdev.moviebox.ui.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.zairussalamdev.moviebox.data.TMDBRepository
import com.zairussalamdev.moviebox.data.entities.MovieEntity
import com.zairussalamdev.moviebox.utils.DummyData
import com.zairussalamdev.moviebox.utils.TestCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

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
    private lateinit var observer: Observer<List<MovieEntity>>


    private lateinit var movieViewModel: MovieViewModel

    @Before
    fun init() {
        movieViewModel = MovieViewModel(tmdbRepository)
    }

    @Test
    fun getMovieList() {
        testCoroutineRule.runBlockingTest {
            val dummy = DummyData.getDummyListData()
            `when`(tmdbRepository.getMovieList()).thenReturn(dummy)
            val movies = tmdbRepository.getMovieList()
            assertNotNull(movies)
            verify(tmdbRepository).getMovieList()
            movieViewModel.getMovieList().observeForever(observer)
            verify(observer).onChanged(dummy)
        }
    }

    @Test
    fun getTvShowsList() {
        testCoroutineRule.runBlockingTest {
            val dummy = DummyData.getDummyListData()
            `when`(tmdbRepository.getTvShowsList()).thenReturn(dummy)
            val tvShows = tmdbRepository.getTvShowsList()
            assertNotNull(tvShows)
            verify(tmdbRepository).getTvShowsList()
            movieViewModel.getTvShowsList().observeForever(observer)
            verify(observer).onChanged(dummy)
        }
    }
}