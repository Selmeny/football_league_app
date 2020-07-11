package com.footballleague.app.views

import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel

interface TeamView {
    fun isLoading(state: Boolean)
    fun isFailed(state: Boolean, code: Int)
    fun loadData(list: MutableList<FootballLeagueTeamModel>?)
}