package com.footballleague.app.presenters

import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballstandings.FootballLeagueStandingListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.StandingView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class StandingPresenter(private val context: CoroutineContextProvider = CoroutineContextProvider(),
                        private val view: StandingView,
                        private val appRepository: AppRepository) {

    fun getStanding(leagueID: String) {
        GlobalScope.launch(context.main) {
            appRepository.getStandings(leagueID, object : AppRepositoryCallback<FootballLeagueStandingListModel?> {
                override fun onDataLoaded(data: FootballLeagueStandingListModel?) {
                    data?.footballLeagueStandings?.let {
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