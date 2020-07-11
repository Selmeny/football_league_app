package com.footballleague.app

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.footballleague.app.activities.DetailLeagueActivity
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailLeagueActivityTest {
    private lateinit var query: String

    @Rule
    @JvmField var activityRule = ActivityTestRule(DetailLeagueActivity::class.java)

    @Before
    fun initQuery() {
        query = "Chelsea" // Query for testing
    }

    fun testSearchBehaviour() {
        
    }
}