package com.footballleague.app.views

import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel

interface DetailMatchView {
    fun isLoading(state: Boolean)
    fun isFailed(state: Boolean)
    fun loadData(list: MutableList<FootballLeagueMatchModel>)
    fun isTeamFailed(state: Boolean, teamID: String)
    fun loadTeam(list: MutableList<FootballLeagueTeamModel>)
}