package com.footballleague.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.footballleague.app.constants.FOOTBALL_DB
import com.footballleague.app.models.footballleaguematchfavorite.FootballLeagueMatchFavModel
import com.footballleague.app.models.footballleagueteamfavorite.FootballLeagueTeamFavModel

@Database(entities = [FootballLeagueMatchFavModel::class, FootballLeagueTeamFavModel::class], version = 1)
abstract class FavoritesDatabase: RoomDatabase() {
    abstract fun matchFavoritesDao(): MatchFavoritesDao
    abstract fun teamFavoritesDao(): TeamFavoritesDao

    companion object {
        private var databaseInstance: FavoritesDatabase? = null

        fun getInstance(context: Context): FavoritesDatabase? {
            if (databaseInstance == null) {
                synchronized(FavoritesDatabase::class) {
                    databaseInstance = Room.databaseBuilder(context, FavoritesDatabase::class.java, FOOTBALL_DB)
                        .build()
                }
            }
            return databaseInstance
        }

        fun deleteInstance() {
            databaseInstance = null
        }
    }

}