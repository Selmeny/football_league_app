package com.footballleague.app.presenters

import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguedetail.FootballLeagueDetailListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.DetailLeagueView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailLeaguePresenter(private val context: CoroutineContextProvider,
                            private val view: DetailLeagueView,
                            private val apiRepository: AppRepository) {
    fun getDetailLeague(leagueID: String) {
        GlobalScope.launch(context.main) {
            apiRepository.getDetailLeague(leagueID, object : AppRepositoryCallback<FootballLeagueDetailListModel?> {
                override fun onDataLoaded(data: FootballLeagueDetailListModel?) {
                    data?.footballLeagues?.let {
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