package com.zairussalamdev.moviebox.ui.favoritemovies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.nhaarman.mockitokotlin2.verify
import com.zairussalamdev.moviebox.core.data.TMDBRepository
import com.zairussalamdev.moviebox.core.data.source.local.entities.MovieEntity
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
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
    private lateinit var tmdbRepository: TMDBRepository

    @Mock
    private lateinit var listObserver: Observer<List<MovieEntity>>

    @Mock
    private lateinit var errorObserver: Observer<String>

    @Mock
    private lateinit var pagedList: PagedList<MovieEntity>

    private lateinit var favoriteViewModel: FavoriteMoviesViewModel

    @Before
    fun init() {
        favoriteViewModel = FavoriteMoviesViewModel(tmdbRepository)
    }

    @Test
    fun `get favorite movies success`() {
        val dummyList = pagedList
        `when`(dummyList.size).thenReturn(10)
        val expectation = MutableLiveData<PagedList<MovieEntity>>()
        expectation.value = dummyList
        `when`(tmdbRepository.getFavoriteMovies()).thenReturn(expectation)
        val favoriteMovies = favoriteViewModel.getFavoriteMovieList().value
        verify(tmdbRepository).getFavoriteMovies()
        assertNotNull(favoriteMovies)
        assertEquals(dummyList.size, favoriteMovies?.size)
        favoriteViewModel.getFavoriteMovieList().observeForever(listObserver)
        favoriteViewModel.getErrorMessage().observeForever(errorObserver)
        verify(listObserver).onChanged(dummyList)
    }

    @Test
    fun `get favorite tv shows success`() {
        val dummyList = pagedList
        `when`(dummyList.size).thenReturn(10)
        val expectation = MutableLiveData<PagedList<MovieEntity>>()
        expectation.value = dummyList
        `when`(tmdbRepository.getFavoriteTvShows()).thenReturn(expectation)
        val favoriteMovies = favoriteViewModel.getFavoriteTvShowList().value
        verify(tmdbRepository).getFavoriteTvShows()
        assertNotNull(favoriteMovies)
        assertEquals(dummyList.size, favoriteMovies?.size)
        favoriteViewModel.getFavoriteTvShowList().observeForever(listObserver)
        favoriteViewModel.getErrorMessage().observeForever(errorObserver)
        verify(listObserver).onChanged(dummyList)
    }
}