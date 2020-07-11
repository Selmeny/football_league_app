package com.footballleague.app.views

import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel

interface SearchDataView {
    fun isLoading(state: Boolean)
    fun isFailed(state: Boolean)
    fun loadMatches(list: MutableList<FootballLeagueMatchModel>)
    fun loadTeams(list: MutableList<FootballLeagueTeamModel>)
}