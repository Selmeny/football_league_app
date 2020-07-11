package com.footballleague.app.presenters

import android.os.Handler
import android.util.Log
import com.footballleague.app.database.FavoritesDatabase
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import com.footballleague.app.views.TeamView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class TeamPresenter(private val context: CoroutineContextProvider = CoroutineContextProvider(),
                    private val view: TeamView,
                    private val appRepository: AppRepository) {
    private val favoriteList = mutableListOf<FootballLeagueTeamModel>()

    fun getTeams(leagueID: String) {
        GlobalScope.launch(context.main) {
            appRepository.getTeams(leagueID, object :
                AppRepositoryCallback<FootballLeagueTeamListModel?> {
                override fun onDataLoaded(data: FootballLeagueTeamListModel?) {
                    data?.footballTeams?.let {
                        view.loadData(it)
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

    fun getFavoriteTeams(databaseInstance: FavoritesDatabase?, handler: Handler) {
        GlobalScope.launch {
            favoriteList.clear()
            val favorites = databaseInstance?.teamFavoritesDao()?.getAll()

            if (favorites != null) {
                Log.d("FAVORITES", "Team size: ${favorites.size}")
            } else{
                Log.d("FAVORITES", "Team == null")
            }

            handler.post {
                if (favorites != null && favorites.isNotEmpty()) {
                    for (fav in favorites) {
                        val team = FootballLeagueTeamModel()
                        team.teamID = fav.teamID
                        team.teamBadge = fav.teamBadge
                        team.teamName = fav.teamName
                        team.teamFormed = fav.teamFormed
                        team.teamNickname = fav.teamNickname
                        favoriteList.add(team)
                    }

                    view.loadData(favoriteList)
                    view.isFailed(false, 0)
                } else{
                    view.loadData(favoriteList)
                    view.isFailed(true, 2)
                }

                view.isLoading(false)
            }
        }
    }
}