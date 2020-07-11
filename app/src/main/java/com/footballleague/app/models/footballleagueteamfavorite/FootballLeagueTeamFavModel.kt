package com.footballleague.app.models.footballleagueteamfavorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.footballleague.app.constants.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = FOOTBALL_FAVORITE_TEAM)
data class FootballLeagueTeamFavModel(
    @ColumnInfo(name = FOOTBALL_TEAM_ID) var teamID: String?,
    @ColumnInfo(name = FOOTBALL_TEAM_BADGE) var teamBadge: String?,
    @ColumnInfo(name = FOOTBALL_TEAM_NAME) var teamName: String?,
    @ColumnInfo(name = FOOTBALL_TEAM_YEAR_FORMED) var teamFormed: String?,
    @ColumnInfo(name = FOOTBALL_TEAM_NICKNAME) var  teamNickname: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = FOOTBALL_ID) var id: Long = 0): Parcelable