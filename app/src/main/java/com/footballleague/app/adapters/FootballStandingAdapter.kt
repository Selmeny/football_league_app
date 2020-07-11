package com.footballleague.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamListModel
import com.footballleague.app.models.footballstandings.FootballLeagueStandingModel
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.repository.AppRepositoryCallback
import kotlinx.android.synthetic.main.item_standing.view.*

class FootballStandingAdapter(private val context: Context, private val items: MutableList<FootballLeagueStandingModel>)
    : RecyclerView.Adapter<FootballStandingAdapter.FootballStandingViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballStandingViewHolder {
        return FootballStandingViewHolder(LayoutInflater.from(context).inflate(R.layout.item_standing, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FootballStandingViewHolder, position: Int) {
        holder.bindData(items[holder.adapterPosition])
    }

    inner class FootballStandingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindData(item: FootballLeagueStandingModel) {
            val teamID = item.teamID
            val played = item.played
            val win = item.win
            val draw = item.draw
            val loss = item.loss
            val goals = item.goals
            val conceded = item.conceded
            val goalDifference = item.goalDifference
            val points = item.points

            played?.let {
                itemView.tv_standing_mp.text = it.toString()
            } ?: kotlin.run {
                itemView.tv_standing_mp.text = "-"
            }

            win?.let {
                itemView.tv_standing_w.text = it.toString()
            } ?: kotlin.run {
                itemView.tv_standing_w.text = "-"
            }

            draw?.let {
                itemView.tv_standing_d.text = it.toString()
            } ?: kotlin.run {
                itemView.tv_standing_d.text = "-"
            }

            loss?.let {
                itemView.tv_standing_l.text = it.toString()
            } ?: kotlin.run {
                itemView.tv_standing_l.text = "-"
            }

            goals?.let {
                itemView.tv_standing_gf.text = it.toString()
            }

            conceded?.let {
                itemView.tv_standing_ga.text = it.toString()
            } ?: kotlin.run {
                itemView.tv_standing_ga.text = "-"
            }

            goalDifference?.let {
                itemView.tv_standing_gd.text = it.toString()
            }

            points?.let {
                itemView.tv_standing_pts.text = it.toString()
            } ?: kotlin.run {
                itemView.tv_standing_pts.text = "-"
            }

            teamID?.let {
                AppRepository().getTeamDetail(teamID, object :
                    AppRepositoryCallback<FootballLeagueTeamListModel?> {
                    override fun onDataLoaded(data: FootballLeagueTeamListModel?) {
                        data?.footballTeams?.let {
                            val team = it[0]
                            val teamBadge = team.teamBadge

                            if (teamBadge != null && teamBadge.isNotEmpty()) {
                                Glide.with(itemView.img_standing_badge)
                                    .load(teamBadge)
                                    .into(itemView.img_standing_badge)
                            } else {
                                Glide.with(itemView.img_standing_badge)
                                    .load(R.drawable.no_image)
                                    .into(itemView.img_standing_badge)
                            }
                        }
                    }

                    override fun onDataError() {
                        Glide.with(itemView.img_standing_badge)
                            .load(R.drawable.no_image)
                            .into(itemView.img_standing_badge)
                    }
                })
            }
        }
    }
}