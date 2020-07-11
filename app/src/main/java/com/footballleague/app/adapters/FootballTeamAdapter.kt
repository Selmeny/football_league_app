package com.footballleague.app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.activities.DetailTeamActivity
import com.footballleague.app.constants.FOOTBALL_TEAM_DATA
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel
import kotlinx.android.synthetic.main.item_team.view.*

class FootballTeamAdapter(private val context: Context, private val items: MutableList<FootballLeagueTeamModel>)
    : RecyclerView.Adapter<FootballTeamAdapter.FootballTeamViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballTeamViewHolder {
        return FootballTeamViewHolder(LayoutInflater.from(context).inflate(R.layout.item_team, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FootballTeamViewHolder, position: Int) {
        holder.bindData(items[holder.adapterPosition])
    }

    inner class FootballTeamViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindData(item: FootballLeagueTeamModel) {
            val badge = item.teamBadge
            val name = item.teamName
            val yearFormed = item.teamFormed
            val nickName = item.teamNickname

            if (badge != null && badge.isNotEmpty()) {
                Glide.with(itemView.img_team_badge)
                    .load(badge)
                    .into(itemView.img_team_badge)
            } else {
                Glide.with(itemView.img_team_badge)
                    .load(R.drawable.no_image)
                    .into(itemView.img_team_badge)
            }

            if (name != null && name.isNotEmpty()) {
                itemView.tv_team_name.text = name
            } else {
                itemView.tv_team_name.text = "-"
            }

            if (yearFormed != null && yearFormed.isNotEmpty()) {
                itemView.tv_team_formed.text = yearFormed
            } else {
                itemView.tv_team_formed.text = "-"
            }

            if (nickName != null && nickName.isNotEmpty()) {
                itemView.tv_team_alias.text = nickName
            } else {
                itemView.tv_team_alias.text = "-"
            }

            itemView.cv_team.setOnClickListener {
                val intent = Intent(context, DetailTeamActivity::class.java)
                intent.putExtra(FOOTBALL_TEAM_DATA, item)
                context.startActivity(intent)
            }
        }
    }
}