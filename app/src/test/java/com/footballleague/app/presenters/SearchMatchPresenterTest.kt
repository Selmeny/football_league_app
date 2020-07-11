package com.footballleague.app.presenters

import com.footballleague.app.helpers.TestContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueSearchMatchListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.SearchDataView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest {
    private val searchQuery = "chelsea"

    @Mock
    private lateinit var searchDataView: SearchDataView

    @Mock
    private lateinit var searchMatchResponse: FootballLeagueSearchMatchListModel

    @Mock
    private lateinit var appRepository: AppRepository

    private lateinit var presenter: SearchDataPresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchDataPresenter(TestContextProvider(), searchDataView, appRepository)
    }

    @Test
    fun getSearchEvent() {
        // Run presenter
        presenter.getSearchMatches(searchQuery)

        argumentCaptor<AppRepositoryCallback<FootballLeagueSearchMatchListModel?>>().apply {
            verify(appRepository).getSearchMatches(eq(searchQuery), capture())
            firstValue.onDataLoaded(searchMatchResponse)
        }

        // Verify functions
        Mockito.verify(searchDataView).loadMatches(searchMatchResponse.footballSearches)
        Mockito.verify(searchDataView).isFailed(false)
        Mockito.verify(searchDataView).isLoading(false)
    }
}