package com.zairussalamdev.moviebox.ui.main

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.zairussalamdev.moviebox.R
import com.zairussalamdev.moviebox.utils.IdlingResource
import org.hamcrest.Matchers.not
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(IdlingResource.espressoTestIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(IdlingResource.espressoTestIdlingResource)
    }

    @Test
    fun movie_list_loaded_successfully() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.error_message)).check(matches(not(isDisplayed())))
    }


    @Test
    fun movie_detail_loaded_successfully() {
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        check_detail_components()
    }

    @Test
    fun tv_show_list_loaded_successfully() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_movies)).check(matches(isDisplayed()))
        onView(withId(R.id.error_message)).check(matches(not(isDisplayed())))
    }

    @Test
    fun tv_show_detail_loaded_successfully() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_movies)).apply {
            check(matches(isDisplayed()))
            perform(
                    RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )
        }
        check_detail_components()
    }

    @Test
    fun favorite_movies_empty_list() {
        val expectedErrorMessage = "No Data"
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.error_message)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(expectedErrorMessage)))
        }
    }

    @Test
    fun favorite_tv_shows_empty_list() {
        val expectedErrorMessage = "No Data"
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.error_message)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(expectedErrorMessage)))
        }
    }

    @Test
    fun add_favorite_movie_and_check_if_it_exists_in_favorite_list() {
        val expectedErrorMessage = "No Data"
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.fab)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.error_message)).apply {
            check(matches(not(isDisplayed())))
        }
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.fab)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.error_message)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(expectedErrorMessage)))
        }
    }

    @Test
    fun add_favorite_tv_show_and_check_if_it_exists_in_favorite_list() {
        val expectedErrorMessage = "No Data"
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.fab)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.error_message)).apply {
            check(matches(not(isDisplayed())))
        }
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.fab)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.error_message)).apply {
            check(matches(isDisplayed()))
            check(matches(withText(expectedErrorMessage)))
        }
    }

    @Test
    fun show_favorite_movie_detail() {
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.fab)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        check_detail_components()
    }

    @Test
    fun show_favorite_tv_show_detail() {
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        onView(withId(R.id.fab)).perform(click())
        onView(isRoot()).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText("TV SHOWS")).perform(click())
        onView(withId(R.id.rv_movies)).perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
        )
        check_detail_components()
    }

    private fun check_detail_components() {
        onView(withId(R.id.movie_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_genre)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_popularity)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_rating)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_tagline)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_poster)).perform(swipeUp())
        onView(withId(R.id.movie_status)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_homepage)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_overview)).perform(swipeUp())
        onView(withId(R.id.error_message)).check(matches(not(isDisplayed())))
    }
}