package com.footballleague.app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.activities.DetailLeagueActivity
import com.footballleague.app.constants.FOOTBALL_LEAGUE_DATA
import com.footballleague.app.models.footballleague.FootballLeagueModel
import kotlinx.android.synthetic.main.item_league.view.*

class FootballLeagueAdapter(private val context:Context, private val items: MutableList<FootballLeagueModel>)
    : RecyclerView.Adapter<FootballLeagueAdapter.FootballLeagueViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FootballLeagueViewHolder {
        return FootballLeagueViewHolder(LayoutInflater.from(context).inflate(R.layout.item_league, parent, false ))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FootballLeagueViewHolder, position: Int) {
        holder.bindData(items[holder.adapterPosition])
    }

    inner class FootballLeagueViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindData(item: FootballLeagueModel) {
            Glide.with(itemView)
                .load(item.image)
                .into(itemView.img_football_league)

            itemView.tv_football_league.text = item.name

            itemView.cv_football_league.setOnClickListener {
                val intent = Intent(context, DetailLeagueActivity::class.java)
                intent.putExtra(FOOTBALL_LEAGUE_DATA, item)
                context.startActivity(intent)
            }
        }
    }
}