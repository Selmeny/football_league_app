package com.footballleague.app.models.footballstandings

import com.google.gson.annotations.SerializedName

class FootballLeagueStandingModel {

    @SerializedName("teamid")
    val teamID: String? = null

    @SerializedName("played")
    val played: Number? = null

    @SerializedName("goalsfor")
    val goals: Number? = null

    @SerializedName("goalsagainst")
    val conceded: Number? = null

    @SerializedName("goalsdifference")
    val goalDifference: Number? = null

    @SerializedName("win")
    val win: Number? = null

    @SerializedName("draw")
    val draw: Number? = null

    @SerializedName("loss")
    val loss: Number? = null

    @SerializedName("total")
    val points: Number? = null

}