package com.footballleague.app.presenters

import com.footballleague.app.helpers.TestContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchListModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.DetailMatchView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailMatchPresenterTest {
    private val detailEventId = "602336" // Liverpool vs Sheffield utd
    private val detailTeamId = "133604" // Arsenal

    @Mock
    private lateinit var detailMatchView: DetailMatchView

    @Mock
    private lateinit var matchResponse: FootballLeagueMatchListModel

    @Mock
    private lateinit var teamResponse: FootballLeagueTeamListModel

    @Mock
    private lateinit var appRepository: AppRepository


    private lateinit var presenter: DetailMatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailMatchPresenter(TestContextProvider(), detailMatchView, appRepository)
    }

    @Test
    fun getEventDetail() {
        // Run presenter
        presenter.getEventDetail(detailEventId)

        argumentCaptor<AppRepositoryCallback<FootballLeagueMatchListModel?>>().apply {
            verify(appRepository).getMatchDetail(eq(detailEventId), capture())
            firstValue.onDataLoaded(matchResponse)
        }

        // Verify functions
        Mockito.verify(detailMatchView).loadData(matchResponse.footballMatches)
        Mockito.verify(detailMatchView).isFailed(false)
        Mockito.verify(detailMatchView).isLoading(false)

    }

    @Test
    fun getTeamDetail() {
        // Run presenter
        presenter.getTeamBadge(detailTeamId)

        argumentCaptor<AppRepositoryCallback<FootballLeagueTeamListModel?>>().apply {
            verify(appRepository).getTeamDetail(eq(detailTeamId), capture())
            firstValue.onDataLoaded(teamResponse)
        }

        // Verify functions
        Mockito.verify(detailMatchView).loadTeam(teamResponse.footballTeams)
        Mockito.verify(detailMatchView).isTeamFailed(false,  detailTeamId)
    }
}