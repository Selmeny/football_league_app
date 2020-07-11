package com.footballleague.app.models.footballstandings

import com.google.gson.annotations.SerializedName

data class FootballLeagueStandingListModel(
    @SerializedName("table")
    val footballLeagueStandings: MutableList<FootballLeagueStandingModel>
)