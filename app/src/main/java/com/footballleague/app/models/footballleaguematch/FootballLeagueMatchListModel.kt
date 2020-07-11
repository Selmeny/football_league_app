package com.footballleague.app.models.footballleaguematch

import com.google.gson.annotations.SerializedName

data class FootballLeagueMatchListModel (
    @SerializedName("events")
    val footballMatches: MutableList<FootballLeagueMatchModel>
)

