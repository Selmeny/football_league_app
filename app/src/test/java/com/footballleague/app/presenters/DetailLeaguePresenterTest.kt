package com.footballleague.app.presenters

import com.footballleague.app.helpers.TestContextProvider
import com.footballleague.app.models.footballleaguedetail.FootballLeagueDetailListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.DetailLeagueView
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailLeaguePresenterTest {
    private val detailLeagueId = "4328" // English premiere league

    @Mock
    private lateinit var detailLeagueView: DetailLeagueView

    @Mock
    private lateinit var leagueResponse: FootballLeagueDetailListModel

    @Mock
    private lateinit var appRepository: AppRepository

    private lateinit var presenter: DetailLeaguePresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = DetailLeaguePresenter(TestContextProvider(), detailLeagueView, appRepository)
    }

    @Test
    fun getDetailLeague() {
        // Run presenter
        presenter.getDetailLeague(detailLeagueId)

        argumentCaptor<AppRepositoryCallback<FootballLeagueDetailListModel?>>().apply {
            verify(appRepository).getDetailLeague(eq(detailLeagueId), capture())
            firstValue.onDataLoaded(leagueResponse)
        }

        // Verify functions
        Mockito.verify(detailLeagueView).loadData(leagueResponse.footballLeagues)
        Mockito.verify(detailLeagueView).isFailed(false)
        Mockito.verify(detailLeagueView).isLoading(false)
    }
}