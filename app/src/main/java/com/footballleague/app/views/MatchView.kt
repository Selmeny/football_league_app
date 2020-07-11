package com.footballleague.app.views

import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel

interface MatchView {
    fun isLoading(state: Boolean)
    fun isFailed(state: Boolean, code: Int)
    fun loadData(list: MutableList<FootballLeagueMatchModel>, type: String?)
}