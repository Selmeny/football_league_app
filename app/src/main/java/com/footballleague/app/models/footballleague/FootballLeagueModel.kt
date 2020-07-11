package com.footballleague.app.models.footballleague

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FootballLeagueModel(
    val name:String,
    val id:Number,
    val image:Number
): Parcelable