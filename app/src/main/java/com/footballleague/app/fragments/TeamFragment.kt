package com.footballleague.app.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballleague.app.R
import com.footballleague.app.adapters.FootballTeamAdapter
import com.footballleague.app.database.FavoritesDatabase
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel
import com.footballleague.app.presenters.TeamPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.TeamView
import kotlinx.android.synthetic.main.fragment_team.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

class TeamFragment(private val leagueID: String?) : Fragment(), TeamView {
    private lateinit var teamContext: Context
    private lateinit var adapter: FootballTeamAdapter
    private lateinit var items: MutableList<FootballLeagueTeamModel>
    private lateinit var presenter: TeamPresenter
    private lateinit var handler: Handler
    private var databaseInstance: FavoritesDatabase? = null

    private val contextProvider = CoroutineContextProvider()

    override fun onAttach(context: Context) {
        teamContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateObjects()
        leagueID?.let {
            presenter.getTeams(it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (leagueID == null) {
            presenter.getFavoriteTeams(databaseInstance, handler)
        }
    }

    override fun onDestroy() {
        FavoritesDatabase.deleteInstance()
        if (contextProvider.main.isActive) {
            contextProvider.main.cancel()
        }
        super.onDestroy()
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_team_loading?.visibility = View.VISIBLE
        } else{
            fl_team_loading?.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean, code: Int) {
        when (code) {
            0 -> tv_team_failed?.text = ""
            1 -> tv_team_failed?.text = teamContext.getString(R.string.network_error_try_again_in_few_minutes)
            2 -> tv_team_failed?.text = teamContext.getString(R.string.favorite_team_not_found)
        }

        if (state) {
            fl_team_failed?.visibility = View.VISIBLE
        } else{
            fl_team_failed?.visibility = View.GONE
        }
    }

    override fun loadData(list: MutableList<FootballLeagueTeamModel>?) {
        items.clear()
        list?.let { items.addAll(list) }
        adapter.notifyDataSetChanged()
    }

    private fun initiateObjects() {
        items = mutableListOf()
        adapter = FootballTeamAdapter(teamContext, items)
        presenter= TeamPresenter(contextProvider, this, AppRepository())
        handler = Handler()
        databaseInstance = FavoritesDatabase.getInstance(teamContext)

        rv_team.layoutManager = LinearLayoutManager(teamContext)
        rv_team.adapter = adapter
        rv_team.setHasFixedSize(true)
    }
}
