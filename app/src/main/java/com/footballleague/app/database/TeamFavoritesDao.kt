package com.footballleague.app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.footballleague.app.constants.FOOTBALL_FAVORITE_TEAM
import com.footballleague.app.models.footballleagueteamfavorite.FootballLeagueTeamFavModel

@Dao
interface TeamFavoritesDao {
    @Query("SELECT * from $FOOTBALL_FAVORITE_TEAM")
    fun getAll(): List<FootballLeagueTeamFavModel>

    @Insert(onConflict = REPLACE)
    fun insert(favorite: FootballLeagueTeamFavModel)

    @Delete
    fun delete(favorite: FootballLeagueTeamFavModel)
}