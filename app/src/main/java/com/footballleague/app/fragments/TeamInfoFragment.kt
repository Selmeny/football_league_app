package com.footballleague.app.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel
import com.footballleague.app.presenters.TeamInfoPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.TeamInfoView
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.android.synthetic.main.fragment_team_info.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

class TeamInfoFragment(private val teamID: String) : Fragment(), TeamInfoView {
    private lateinit var presenter: TeamInfoPresenter

    private val contextProvider = CoroutineContextProvider()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateObjects()
        presenter.getTeamDetail(teamID)
    }

    override fun onDestroy() {
        if (contextProvider.main.isActive) {
            contextProvider.main.cancel()
        }
        super.onDestroy()
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_team_info_loading?.visibility = View.VISIBLE
        } else{
            fl_team_info_loading?.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean) {
        if (state) {
            fl_team_info_failed?.visibility = View.VISIBLE
        } else {
            fl_team_info_failed?.visibility = View.GONE
        }

    }

    override fun loadData(list: MutableList<FootballLeagueTeamModel>) {
        if (list.isNotEmpty()) {
            val detailTeam = list[0]

            val stadiumThumb = detailTeam.teamStadiumThumb
            if (stadiumThumb != null && stadiumThumb.isNotEmpty()) {
                Glide.with(img_team_stadium)
                    .load(stadiumThumb)
                    .into(img_team_stadium)
            } else {
                Glide.with(img_team_stadium)
                    .load(R.drawable.no_image)
                    .into(img_team_stadium)
            }

            val nickname = detailTeam.teamNickname
            if (nickname != null && nickname.isNotEmpty()) {
                tv_team_nickname.text = nickname
            } else {
                tv_team_nickname.text = "-"
            }

            val stadiumName = detailTeam.teamStadium
            if (stadiumName != null && stadiumName.isNotEmpty()) {
                tv_team_stadium_name.text = stadiumName
            } else {
                tv_team_stadium_name.text = "-"
            }

            val stadiumLocation = detailTeam.teamStadiumLocation
            if (stadiumLocation != null && stadiumLocation.isNotEmpty()) {
                tv_team_stadium_location.text = stadiumLocation
            } else {
                tv_team_stadium_location.text = "-"
            }

            val stadiumCapacity = detailTeam.teamStadiumCapacity
            if (stadiumCapacity != null && stadiumCapacity.isNotEmpty()) {
                tv_team_stadium_capacity.text = stadiumCapacity
            } else {
                tv_team_stadium_capacity.text = "-"
            }

            val stadiumDescription = detailTeam.teamStadiumDescription
            if (stadiumDescription != null && stadiumDescription.isNotEmpty()) {
                tv_team_stadium_description.text = stadiumDescription
            } else {
                tv_detail_team_description.text = "-"
            }
        }
    }

    private fun initiateObjects() {
        presenter = TeamInfoPresenter(contextProvider,this, AppRepository())
    }
}
