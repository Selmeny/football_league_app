package com.footballleague.app.views

import com.footballleague.app.models.footballleague.FootballLeagueModel

interface MainView {
    fun isLoading(state: Boolean)
    fun loadData(list: MutableList<FootballLeagueModel>)
}