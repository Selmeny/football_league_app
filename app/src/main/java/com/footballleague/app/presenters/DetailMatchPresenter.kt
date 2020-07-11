package com.footballleague.app.presenters

import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchListModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.DetailMatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchPresenter(private val context: CoroutineContextProvider = CoroutineContextProvider(),
                           private val view: DetailMatchView,
                           private val appRepository: AppRepository) {
    fun getEventDetail(matchID: String) {
        GlobalScope.launch(context.main) {
            appRepository.getMatchDetail(matchID, object : AppRepositoryCallback<FootballLeagueMatchListModel?> {
                override fun onDataLoaded(data: FootballLeagueMatchListModel?) {
                    data?.footballMatches?.let {
                        view.loadData(it)
                        view.isFailed(false)
                    } ?: kotlin.run {
                        view.isFailed(true)
                    }

                    view.isLoading(false)
                }

                override fun onDataError() {
                    view.isFailed(true)
                    view.isLoading(false)
                }
            })
        }
    }

    fun getTeamBadge(teamID: String) {
        GlobalScope.launch(context.main) {
            appRepository.getTeamDetail(teamID, object : AppRepositoryCallback<FootballLeagueTeamListModel?> {
                override fun onDataLoaded(data: FootballLeagueTeamListModel?) {
                    data?.footballTeams?.let {
                        view.loadTeam(it)
                        view.isTeamFailed(false, teamID)
                    } ?: kotlin.run {
                        view.isTeamFailed(true, teamID)
                    }
                }

                override fun onDataError() {
                    view.isTeamFailed(true, teamID)
                }
            })
        }
    }
}