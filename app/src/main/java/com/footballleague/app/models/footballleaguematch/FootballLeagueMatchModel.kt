package com.footballleague.app.models.footballleaguematch

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FootballLeagueMatchModel (
    @SerializedName("idEvent")
    var matchID: String? = null,

    @SerializedName("strEvent")
    var matchName: String? = null,

    @SerializedName("strSport")
    var matchType: String? = null,

    @SerializedName("strDate")
    var matchDate: String? = null,

    @SerializedName("strTime")
    var matchTime: String? = null,

    @SerializedName("strHomeTeam")
    var matchHome: String? = null,

    @SerializedName("strAwayTeam")
    var matchAway: String? = null,

    @SerializedName("idHomeTeam")
    var matchHomeID: String? = null,

    @SerializedName("idAwayTeam")
    var matchAwayID: String? = null,

    @SerializedName("intHomeScore")
    var matchHomeScore: String? = null,

    @SerializedName("intAwayScore")
    var matchAwayScore: String? = null,

    @SerializedName("strHomeGoalDetails")
    var matchHomeScoreDetail: String? = null,

    @SerializedName("strAwayGoalDetails")
    var matchAwayScoreDetail: String? = null,

    @SerializedName("strHomeYellowCards")
    var matchHomeYellowCards: String? = null,

    @SerializedName("strAwayYellowCards")
    var matchAwayYellowCards: String? = null,

    @SerializedName("strHomeRedCards")
    var matchHomeRedCards: String? = null,

    @SerializedName("strAwayRedCards")
    var matchAwayRedCards: String? = null,

    @SerializedName("strHomeLineupGoalkeeper")
    var matchHomeGoalkeeper: String? = null,

    @SerializedName("strAwayLineupGoalkeeper")
    var matchAwayGoalkeeper: String? = null,

    @SerializedName("strHomeLineupDefense")
    var matchHomeDefense: String? = null,

    @SerializedName("strAwayLineupDefense")
    var matchAwayDefense: String? = null,

    @SerializedName("strHomeLineupMidfield")
    var matchHomeMidfield: String? = null,

    @SerializedName("strAwayLineupMidfield")
    var matchAwayMidfield: String? = null,

    @SerializedName("strHomeLineupForward")
    var matchHomeForward: String? = null,

    @SerializedName("strAwayLineupForward")
    var matchAwayForward: String? = null,

    @SerializedName("strHomeLineupSubstitutes")
    var matchHomeSubstitute: String? = null,

    @SerializedName("strAwayLineupSubstitutes")
    var matchAwaySubstitute: String? = null,

    @SerializedName("strHomeFormation")
    var matchHomeFormation: String? = null,

    @SerializedName("strAwayFormation")
    var matchAwayFormation: String? = null,

    @SerializedName("intHomeShots")
    var matchHomeShots: String? = null,

    @SerializedName("intAwayShots")
    var matchAwayShots: String? = null,

    @SerializedName("intSpectators")
    var matchSpectators: String? = null
): Parcelable