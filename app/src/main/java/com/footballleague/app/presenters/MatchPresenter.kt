package com.footballleague.app.presenters

import android.os.Handler
import com.footballleague.app.constants.FOOTBALL_LAST_MATCH
import com.footballleague.app.constants.FOOTBALL_NEXT_MATCH
import com.footballleague.app.database.FavoritesDatabase
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchListModel
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.MatchView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchPresenter(private val context: CoroutineContextProvider = CoroutineContextProvider(),
                     private val view: MatchView,
                     private val appRepository: AppRepository) {
    private val favoriteList = mutableListOf<FootballLeagueMatchModel>()

    fun getMatch(type: String, leagueID: String) {
        GlobalScope.launch(context.main) {
            if (type.equals(FOOTBALL_LAST_MATCH, true)) {
                appRepository.getLastMatches(leagueID, object : AppRepositoryCallback<FootballLeagueMatchListModel?> {
                    override fun onDataLoaded(data: FootballLeagueMatchListModel?) {
                        data?.footballMatches?.let {
                            view.loadData(it, FOOTBALL_LAST_MATCH)
                            view.isFailed(false, 0)
                        } ?: kotlin.run {
                            view.isFailed(true, 1)
                        }

                        view.isLoading(false)
                    }

                    override fun onDataError() {
                        view.isFailed(true, 1)
                        view.isLoading(false)
                    }

                })
            } else if (type.equals(FOOTBALL_NEXT_MATCH, true)) {
                appRepository.getNextMatches(leagueID, object : AppRepositoryCallback<FootballLeagueMatchListModel?> {
                    override fun onDataLoaded(data: FootballLeagueMatchListModel?) {
                        data?.footballMatches?.let {
                            view.loadData(it, FOOTBALL_NEXT_MATCH)
                            view.isFailed(false, 0)
                        } ?: kotlin.run {
                            view.isFailed(true, 1)
                        }

                        view.isLoading(false)
                    }

                    override fun onDataError() {
                        view.isFailed(true, 1)
                        view.isLoading(false)
                    }

                })
            }
        }
    }

    fun getFavoriteMatches(type: String, databaseInstance: FavoritesDatabase?, handler: Handler) {
        GlobalScope.launch {
            favoriteList.clear()
            val favorites = databaseInstance?.matchFavoritesDao()?.getAll()

            handler.post {
                if (favorites != null && favorites.isNotEmpty()) {
                    for (fav in favorites) {
                        if (fav.matchType.equals(type, true)) {
                            val match = FootballLeagueMatchModel()
                            match.matchID = fav.matchId
                            match.matchDate = fav.matchDate
                            match.matchTime = fav.matchTime
                            match.matchHome = fav.homeTeam
                            match.matchAway = fav.awayTeam
                            match.matchHomeScore = fav.homeScore
                            match.matchAwayScore = fav.awayScore
                            match.matchHomeID = fav.homeId
                            match.matchAwayID = fav.awayId
                            favoriteList.add(match)
                        }
                    }
                }

                if (favoriteList.isNotEmpty()) {
                    view.loadData(favoriteList, null)
                    view.isFailed(false, 0)
                } else {
                    view.loadData(favoriteList, null)
                    view.isFailed(true,2)
                }

                view.isLoading(false)
            }
        }
    }
}