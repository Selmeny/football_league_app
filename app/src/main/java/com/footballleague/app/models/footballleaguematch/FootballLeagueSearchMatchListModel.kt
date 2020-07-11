package com.footballleague.app.models.footballleaguematch

import com.google.gson.annotations.SerializedName

data class FootballLeagueSearchMatchListModel(
    @SerializedName("event")
    val footballSearches: MutableList<FootballLeagueMatchModel>
)