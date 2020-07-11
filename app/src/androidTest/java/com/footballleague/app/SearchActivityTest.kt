package com.footballleague.app

import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.footballleague.app.activities.SearchActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchActivityTest {
    private lateinit var query: String

    @Rule
    @JvmField var activityRule = ActivityTestRule(SearchActivity::class.java)

    @Before
    fun initQuery() {
        query = "Chelsea" // Query for testing
    }
    @Test
    fun testSearchBehaviour() {
        // Check if searchView is displayed/not
        onView(withId(R.id.sv_search_match)).check(matches(isDisplayed()))

        // Click searchView to gain focus
        onView(withId(R.id.sv_search_match)).perform(click())

        // Assign EditText.Class to perform search action
        onView(isAssignableFrom(EditText::class.java)).perform(typeText(query))
        onView(isAssignableFrom(EditText::class.java)).check(matches(hasImeAction(EditorInfo.IME_ACTION_SEARCH)))
        onView(isAssignableFrom(EditText::class.java)).perform(pressImeActionButton())

        // Wait for API to get the data loading and loading indicator gone
        Thread.sleep(5000)

        // Check if recyclerView is displayed
        onView(withId(R.id.rv_search_match)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_search_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(R.id.rv_search_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))

        // Wait for API to get details and loading indicator gone
        Thread.sleep(5000)
    }
}