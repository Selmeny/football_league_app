package com.footballleague.app.models.footballleaguedetail

import com.google.gson.annotations.SerializedName

data class FootballLeagueDetailListModel(
    @SerializedName("leagues")
    val footballLeagues: MutableList<FootballLeagueDetailModel>
)