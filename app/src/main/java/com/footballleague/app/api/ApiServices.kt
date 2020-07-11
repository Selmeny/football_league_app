package com.footballleague.app.api

import com.footballleague.app.models.footballleaguedetail.FootballLeagueDetailListModel
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchListModel
import com.footballleague.app.models.footballleaguematch.FootballLeagueSearchMatchListModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.models.footballstandings.FootballLeagueStandingListModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("lookupleague.php")
    fun getFootballLeagueList(@Query("id") id: String?): Call<FootballLeagueDetailListModel>

    @GET("eventspastleague.php")
    fun getFootballPreviousEvents(@Query("id") id: String?): Call<FootballLeagueMatchListModel>

    @GET("eventsnextleague.php")
    fun getFootballNextEvents(@Query("id") id: String?): Call<FootballLeagueMatchListModel>

    @GET("lookupevent.php")
    fun getFootballEventDetails(@Query("id") id: String?): Call<FootballLeagueMatchListModel>

    @GET("lookupteam.php")
    fun getFootballTeam(@Query("id") id: String?): Call<FootballLeagueTeamListModel>

    @GET("searchevents.php")
    fun getFootballMatches(@Query("e") query: String?): Call<FootballLeagueSearchMatchListModel>

    @GET("lookuptable.php")
    fun getStandings(@Query("l") id: String?): Call<FootballLeagueStandingListModel>

    @GET("lookup_all_teams.php")
    fun getFootballAllTeams(@Query("id") id: String?): Call<FootballLeagueTeamListModel>

    @GET("searchteams.php")
    fun getFootballTeams(@Query("t") query: String?): Call<FootballLeagueTeamListModel>
}