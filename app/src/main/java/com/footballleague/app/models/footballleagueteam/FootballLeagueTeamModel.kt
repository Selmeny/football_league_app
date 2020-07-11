package com.footballleague.app.models.footballleagueteam

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FootballLeagueTeamModel(
    @SerializedName("idTeam")
    var teamID: String? = null,

    @SerializedName("strTeam")
    var teamName: String? = null,

    @SerializedName("strSport")
    var teamType: String? = null,

    @SerializedName("strAlternate")
    var teamNickname: String? = null,

    @SerializedName("intFormedYear")
    var teamFormed: String? = null,

    @SerializedName("strCountry")
    var teamOrigin: String? = null,

    @SerializedName("strStadium")
    var teamStadium: String? = null,

    @SerializedName("strStadiumThumb")
    var teamStadiumThumb: String? = null,

    @SerializedName("strStadiumDescription")
    var teamStadiumDescription: String? = null,

    @SerializedName("strStadiumLocation")
    var teamStadiumLocation: String? = null,

    @SerializedName("intStadiumCapacity")
    var teamStadiumCapacity: String? = null,

    @SerializedName("strTeamBadge")
    var teamBadge: String? = null,

    @SerializedName("strWebsite")
    var teamWebsite: String? = null,

    @SerializedName("strFacebook")
    var teamFacebook: String? = null,

    @SerializedName("strTwitter")
    var teamTwitter: String? = null,

    @SerializedName("strInstagram")
    var teamInstagram: String? = null,

    @SerializedName("strYoutube")
    var teamYoutube: String? = null,

    @SerializedName("strDescriptionEN")
    var teamDescription: String? = null
): Parcelable