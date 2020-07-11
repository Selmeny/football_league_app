package com.footballleague.app.views

import com.footballleague.app.models.footballleaguedetail.FootballLeagueDetailModel

interface DetailLeagueView {
    fun isLoading(state: Boolean)
    fun isFailed(state: Boolean)
    fun loadData(list: MutableList<FootballLeagueDetailModel>)
}