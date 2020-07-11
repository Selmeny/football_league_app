package com.footballleague.app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.activities.DetailMatchActivity
import com.footballleague.app.constants.FOOTBALL_MATCH
import com.footballleague.app.constants.FOOTBALL_MATCH_TYPE
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import kotlinx.android.synthetic.main.item_match.view.*

class FootballMatchAdapter(private val context:Context,
                           private val items: MutableList<FootballLeagueMatchModel>,
                           private val matchType: String?)
    : RecyclerView.Adapter<FootballMatchAdapter.FootballMatchFavViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballMatchFavViewHolder {
        return FootballMatchFavViewHolder(LayoutInflater.from(context).inflate(R.layout.item_match, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holderFav: FootballMatchFavViewHolder, position: Int) {
        holderFav.bindData(items[holderFav.adapterPosition])
    }

    inner class FootballMatchFavViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindData(item: FootballLeagueMatchModel) {
            val date = item.matchDate
            val time = item.matchTime
            val homeTeam = item.matchHome
            val awayTeam = item.matchAway
            val homeScore = item.matchHomeScore
            val awayScore = item.matchAwayScore
            val homeTeamID = item.matchHomeID
            val awayTeamID = item.matchAwayID

            if (date != null && date.isNotEmpty()) {
                itemView.tv_match_date.text = date
            } else{
                itemView.tv_match_date.text = "-"
            }

            if (time != null && time.isNotEmpty()) {
                itemView.tv_match_time.text = time
            } else{
                itemView.tv_match_time.text = "-"
            }

            if (homeTeam != null && homeTeam.isNotEmpty()) {
                itemView.tv_match_home_team.text = homeTeam
            } else{
                itemView.tv_match_home_team.text = "-"
            }

            if (awayTeam != null && awayTeam.isNotEmpty()) {
                itemView.tv_match_away_team.text = awayTeam
            } else{
                itemView.tv_match_away_team.text = awayTeam
            }

            if (homeScore != null && homeScore.isNotEmpty()) {
                itemView.tv_match_home_score.text = homeScore
            } else{
                itemView.tv_match_home_score.text = "-"
            }

            if (awayScore != null && awayScore.isNotEmpty()) {
                itemView.tv_match_away_score.text = awayScore
            } else{
                itemView.tv_match_away_score.text = "-"
            }

            if (homeScore != null && homeScore.isNotEmpty() && awayScore != null && awayScore.isNotEmpty())
                itemView.tv_match_status.visibility = View.VISIBLE
            else {
                itemView.tv_match_status.visibility = View.GONE
            }

            itemView.cv_match.setOnClickListener {
                val intent = Intent(context, DetailMatchActivity::class.java)
                intent.putExtra(FOOTBALL_MATCH_TYPE, matchType)
                intent.putExtra(FOOTBALL_MATCH, item)
                context.startActivity(intent)
            }

            homeTeamID?.let {
                AppRepository().getTeamDetail(homeTeamID, object : AppRepositoryCallback<FootballLeagueTeamListModel?> {
                    override fun onDataLoaded(data: FootballLeagueTeamListModel?) {
                        data?.footballTeams?.let {
                            val team = it[0]
                            val teamBadge = team.teamBadge

                            if (teamBadge != null && teamBadge.isNotEmpty()) {
                                Glide.with(itemView.img_detail_match_badge_home)
                                    .load(teamBadge)
                                    .into(itemView.img_detail_match_badge_home)
                            } else {
                                Glide.with(itemView.img_detail_match_badge_home)
                                    .load(R.drawable.no_image)
                                    .into(itemView.img_detail_match_badge_home)
                            }
                        }
                    }

                    override fun onDataError() {
                        Glide.with(itemView.img_detail_match_badge_home)
                            .load(R.drawable.no_image)
                            .into(itemView.img_detail_match_badge_home)
                    }
                })
            }

            awayTeamID?.let {
                AppRepository().getTeamDetail(awayTeamID, object : AppRepositoryCallback<FootballLeagueTeamListModel?> {
                    override fun onDataLoaded(data: FootballLeagueTeamListModel?) {
                        data?.footballTeams?.let {
                            val team = it[0]
                            val teamBadge = team.teamBadge

                            if (teamBadge != null && teamBadge.isNotEmpty()) {
                                Glide.with(itemView.img_detail_match_badge_away)
                                    .load(teamBadge)
                                    .into(itemView.img_detail_match_badge_away)
                            } else {
                                Glide.with(itemView.img_detail_match_badge_away)
                                    .load(R.drawable.no_image)
                                    .into(itemView.img_detail_match_badge_away)
                            }
                        }
                    }

                    override fun onDataError() {
                        Glide.with(itemView.img_detail_match_badge_away)
                            .load(R.drawable.no_image)
                            .into(itemView.img_detail_match_badge_away)
                    }
                })
            }
        }
    }
}