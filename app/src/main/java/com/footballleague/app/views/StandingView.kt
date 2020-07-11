package com.footballleague.app.views

import com.footballleague.app.models.footballstandings.FootballLeagueStandingModel

interface StandingView {
    fun isLoading(state: Boolean)
    fun isFailed(state: Boolean)
    fun loadData(list: MutableList<FootballLeagueStandingModel>?)
}