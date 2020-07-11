package com.footballleague.app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.footballleague.app.constants.FOOTBALL_FAVORITE_MATCH
import com.footballleague.app.models.footballleaguematchfavorite.FootballLeagueMatchFavModel

@Dao
interface MatchFavoritesDao {
    @Query("SELECT * from $FOOTBALL_FAVORITE_MATCH")
    fun getAll(): List<FootballLeagueMatchFavModel>

    @Insert(onConflict = REPLACE)
    fun insert(favorite: FootballLeagueMatchFavModel)

    @Delete
    fun delete(favorite: FootballLeagueMatchFavModel)
}