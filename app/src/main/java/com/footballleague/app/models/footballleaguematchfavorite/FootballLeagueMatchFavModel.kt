package com.footballleague.app.models.footballleaguematchfavorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.footballleague.app.constants.*
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = FOOTBALL_FAVORITE_MATCH)
data class FootballLeagueMatchFavModel(
    @ColumnInfo(name = FOOTBALL_MATCH_TYPE) var matchType: String?,
    @ColumnInfo(name = FOOTBALL_MATCH_ID) var matchId: String?,
    @ColumnInfo(name = FOOTBALL_DATE) var matchDate: String?,
    @ColumnInfo(name = FOOTBALL_TIME) var matchTime: String?,
    @ColumnInfo(name = FOOTBALL_HOME_TEAM) var homeTeam: String?,
    @ColumnInfo(name = FOOTBALL_AWAY_TEAM) var awayTeam: String?,
    @ColumnInfo(name = FOOTBALL_HOME_SCORE) var homeScore: String?,
    @ColumnInfo(name = FOOTBALL_AWAY_SCORE) var awayScore: String?,
    @ColumnInfo(name = FOOTBALL_HOME_ID) var homeId: String?,
    @ColumnInfo(name = FOOTBALL_AWAY_ID) var awayId: String?,
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = FOOTBALL_ID) var id: Long = 0): Parcelable