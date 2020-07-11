package com.footballleague.app.presenters

import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.DetailTeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamPresenter(private val context: CoroutineContextProvider = CoroutineContextProvider(),
                          private val view: DetailTeamView,
                          private val appRepository: AppRepository) {
    fun getTeamDetail(teamID: String) {
        GlobalScope.launch(context.main) {
            appRepository.getTeamDetail(teamID, object :
                AppRepositoryCallback<FootballLeagueTeamListModel?> {
                override fun onDataLoaded(data: FootballLeagueTeamListModel?) {
                    data?.footballTeams?.let {
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
}