package com.footballleague.app.presenters

import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueSearchMatchListModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.SearchDataView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchDataPresenter(private val context: CoroutineContextProvider = CoroutineContextProvider(),
                          private val view: SearchDataView,
                          private val appRepository: AppRepository) {
    fun getSearchTeams(query: String)  {
        GlobalScope.launch(context.main) {
            appRepository.getSearchTeams(query, object : AppRepositoryCallback<FootballLeagueTeamListModel?> {
                override fun onDataLoaded(data: FootballLeagueTeamListModel?) {
                    data?.footballTeams?.let {
                        view.loadTeams(it)
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

    fun getSearchMatches(query: String)  {
        GlobalScope.launch(context.main) {
            appRepository.getSearchMatches(query, object : AppRepositoryCallback<FootballLeagueSearchMatchListModel?> {
                override fun onDataLoaded(data: FootballLeagueSearchMatchListModel?) {
                    data?.footballSearches?.let {
                        view.loadMatches(it)
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