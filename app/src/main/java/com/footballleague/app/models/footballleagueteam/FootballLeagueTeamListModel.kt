package com.footballleague.app.models.footballleagueteam

import com.google.gson.annotations.SerializedName

data class FootballLeagueTeamListModel (
    @SerializedName("teams")
    val footballTeams: MutableList<FootballLeagueTeamModel>
)