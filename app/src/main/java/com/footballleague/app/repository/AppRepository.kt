package com.footballleague.app.repository

import com.footballleague.app.api.ApiClient
import com.footballleague.app.models.footballleaguedetail.FootballLeagueDetailListModel
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchListModel
import com.footballleague.app.models.footballleaguematch.FootballLeagueSearchMatchListModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.models.footballstandings.FootballLeagueStandingListModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppRepository {
    fun getMatchDetail(matchID: String, callback: AppRepositoryCallback<FootballLeagueMatchListModel?>) {
        ApiClient
            .apiServices
            .getFootballEventDetails(matchID)
            .enqueue(object : Callback<FootballLeagueMatchListModel?> {
                override fun onFailure(call: Call<FootballLeagueMatchListModel?>?, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueMatchListModel?>?, response: Response<FootballLeagueMatchListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getTeamDetail(teamID: String, callback: AppRepositoryCallback<FootballLeagueTeamListModel?>) {
        ApiClient
            .apiServices
            .getFootballTeam(teamID)
            .enqueue(object : Callback<FootballLeagueTeamListModel?> {
                override fun onFailure(call: Call<FootballLeagueTeamListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueTeamListModel?>?, response: Response<FootballLeagueTeamListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getDetailLeague(leagueID: String, callback: AppRepositoryCallback<FootballLeagueDetailListModel?>) {
        ApiClient
            .apiServices
            .getFootballLeagueList(leagueID)
            .enqueue(object : Callback<FootballLeagueDetailListModel?> {
                override fun onFailure(call: Call<FootballLeagueDetailListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueDetailListModel?>?, response: Response<FootballLeagueDetailListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else{
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getNextMatches(matchID: String, callback: AppRepositoryCallback<FootballLeagueMatchListModel?>) {
        ApiClient
            .apiServices
            .getFootballNextEvents(matchID)
            .enqueue(object : Callback<FootballLeagueMatchListModel?> {
                override fun onFailure(call: Call<FootballLeagueMatchListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueMatchListModel?>?, response: Response<FootballLeagueMatchListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getLastMatches(matchID: String, callback: AppRepositoryCallback<FootballLeagueMatchListModel?>) {
        ApiClient
            .apiServices
            .getFootballPreviousEvents(matchID)
            .enqueue(object : Callback<FootballLeagueMatchListModel?> {
                override fun onFailure(call: Call<FootballLeagueMatchListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueMatchListModel?>?, response: Response<FootballLeagueMatchListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getSearchMatches(query: String, callback: AppRepositoryCallback<FootballLeagueSearchMatchListModel?>) {
        ApiClient
            .apiServices
            .getFootballMatches(query)
            .enqueue(object : Callback<FootballLeagueSearchMatchListModel?> {
                override fun onFailure(call: Call<FootballLeagueSearchMatchListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueSearchMatchListModel?>?, response: Response<FootballLeagueSearchMatchListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getStandings(leagueID: String, callback: AppRepositoryCallback<FootballLeagueStandingListModel?>) {
        ApiClient
            .apiServices
            .getStandings(leagueID)
            .enqueue(object : Callback<FootballLeagueStandingListModel?> {
                override fun onFailure(call: Call<FootballLeagueStandingListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueStandingListModel?>?, response: Response<FootballLeagueStandingListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getTeams(leagueID: String, callback: AppRepositoryCallback<FootballLeagueTeamListModel?>) {
        ApiClient
            .apiServices
            .getFootballAllTeams(leagueID)
            .enqueue(object : Callback<FootballLeagueTeamListModel?> {
                override fun onFailure(call: Call<FootballLeagueTeamListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueTeamListModel?>?, response: Response<FootballLeagueTeamListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })
    }

    fun getSearchTeams(query: String, callback: AppRepositoryCallback<FootballLeagueTeamListModel?>) {
        ApiClient
            .apiServices
            .getFootballTeams(query)
            .enqueue(object : Callback<FootballLeagueTeamListModel?> {
                override fun onFailure(call: Call<FootballLeagueTeamListModel?>, t: Throwable) {
                    callback.onDataError()
                }

                override fun onResponse(call: Call<FootballLeagueTeamListModel?>?, response: Response<FootballLeagueTeamListModel?>?) {
                    response?.let {
                        if (it.isSuccessful) {
                            callback.onDataLoaded(it.body())
                        } else {
                            callback.onDataError()
                        }
                    }
                }
            })

    }
}