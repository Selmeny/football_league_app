package com.footballleague.app.models.footballleaguedetail

import com.google.gson.annotations.SerializedName

data class FootballLeagueDetailModel (
    @SerializedName("idLeague")
    val leagueID: String? = null,

    @SerializedName("strLeague")
    val leagueName: String? = null,

    @SerializedName("intFormedYear")
    val leagueFormed: String? = null,

    @SerializedName("strCountry")
    val leagueOrigin: String? = null,

    @SerializedName("strWebsite")
    val leagueWebsite: String? = null,

    @SerializedName("strFacebook")
    val leagueFB: String? = null,

    @SerializedName("strTwitter")
    val leagueTwitter: String? = null,

    @SerializedName("strYoutube")
    val leagueYoutube: String? = null,

    @SerializedName("strDescriptionEN")
    val leagueDescription: String? = null,

    @SerializedName("strBadge")
    val leagueBadge: String? = null
)