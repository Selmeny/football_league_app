package com.footballleague.app.presenters

import com.footballleague.app.constants.FOOTBALL_LAST_MATCH
import com.footballleague.app.constants.FOOTBALL_NEXT_MATCH
import com.footballleague.app.helpers.TestContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.MatchView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchPresenterTest {
    private val leagueId = "4328"

    @Mock
    private lateinit var matchView: MatchView

    @Mock
    private lateinit var matchResponse: FootballLeagueMatchListModel

    @Mock
    private lateinit var appRepository: AppRepository


    private lateinit var presenter: MatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchPresenter(TestContextProvider(), matchView, appRepository)
    }

    @Test
    fun getLastEvent() {
        // Run presenter
        presenter.getMatch(FOOTBALL_LAST_MATCH, leagueId)

        argumentCaptor<AppRepositoryCallback<FootballLeagueMatchListModel?>>().apply {
            verify(appRepository).getLastMatches(eq(leagueId), capture())
            firstValue.onDataLoaded(matchResponse)
        }

        // Verify functions
        Mockito.verify(matchView).loadData(matchResponse.footballMatches, FOOTBALL_LAST_MATCH)
        Mockito.verify(matchView).isFailed(false,0)
        Mockito.verify(matchView).isLoading(false)
    }

    @Test
    fun getNextEvent() {
        // Run presenter
        presenter.getMatch(FOOTBALL_NEXT_MATCH, leagueId)

        argumentCaptor<AppRepositoryCallback<FootballLeagueMatchListModel?>>().apply {
            verify(appRepository).getNextMatches(eq(leagueId), capture())
            firstValue.onDataLoaded(matchResponse)
        }

        // Verify functions
        Mockito.verify(matchView).loadData(matchResponse.footballMatches, FOOTBALL_NEXT_MATCH)
        Mockito.verify(matchView).isFailed(false,0)
        Mockito.verify(matchView).isLoading(false)
    }
}