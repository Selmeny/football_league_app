package com.footballleague.app.views

import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel

interface TeamInfoView {
    fun isLoading(state: Boolean)
    fun isFailed(state: Boolean)
    fun loadData(list: MutableList<FootballLeagueTeamModel>)
}