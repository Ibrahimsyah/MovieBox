package com.zairussalamdev.moviebox.ui.favoritemovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.core.domain.model.Movie
import com.zairussalamdev.moviebox.core.domain.usecase.MovieUseCase
import com.zairussalamdev.moviebox.core.utils.DummyData
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteMoviesViewModelTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var movieInteractor: MovieUseCase

    @Mock
    private lateinit var listObserver: Observer<List<Movie>>

    @Mock
    private lateinit var errorObserver: Observer<String>

    private lateinit var favoriteViewModel: FavoriteMoviesViewModel

    @Before
    fun init() {
        favoriteViewModel = FavoriteMoviesViewModel(movieInteractor)
    }

    @Test
    fun `get favorite movies success`() {
        val dummyData = DummyData.getDummyListData()
        val expectation = MutableLiveData<List<Movie>>()
        expectation.value = dummyData
        `when`(movieInteractor.getFavoriteMovies()).thenReturn(expectation)

        favoriteViewModel.getFavoriteMovieList().observeForever(listObserver)
        favoriteViewModel.getErrorMessage().observeForever(errorObserver)
        verify(movieInteractor).getFavoriteMovies()
        verify(listObserver).onChanged(dummyData)
    }

    @Test
    fun `get favorite tv shows success`() {
        val dummyData = DummyData.getDummyListData()
        val expectation = MutableLiveData<List<Movie>>()
        expectation.value = dummyData
        `when`(movieInteractor.getFavoriteTvShows()).thenReturn(expectation)

        favoriteViewModel.getFavoriteTvShowList().observeForever(listObserver)
        favoriteViewModel.getErrorMessage().observeForever(errorObserver)
        verify(movieInteractor).getFavoriteTvShows()
        verify(listObserver).onChanged(dummyData)
    }
}