package com.footballleague.app.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballleague.app.R
import com.footballleague.app.adapters.FootballStandingAdapter
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballstandings.FootballLeagueStandingModel
import com.footballleague.app.presenters.StandingPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.StandingView
import kotlinx.android.synthetic.main.fragment_standings.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

class StandingsFragment(private val leagueID: String) : Fragment(), StandingView {
    private lateinit var standingContext: Context
    private lateinit var adapter: FootballStandingAdapter
    private lateinit var items: MutableList<FootballLeagueStandingModel>
    private lateinit var presenter: StandingPresenter

    private val contextProvider = CoroutineContextProvider()

    override fun onAttach(context: Context) {
        standingContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_standings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateObjects()
        presenter.getStanding(leagueID)
    }

    override fun onDestroy() {
        if (contextProvider.main.isActive) {
            contextProvider.main.cancel()
        }
        super.onDestroy()
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_standing_loading?.visibility = View.VISIBLE
        } else{
            fl_standing_loading?.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean) {
        if (state) {
            fl_standing_failed?.visibility = View.VISIBLE
        } else{
            fl_standing_failed?.visibility = View.GONE
        }
    }

    override fun loadData(list: MutableList<FootballLeagueStandingModel>?) {
        items.clear()
        list?.let { items.addAll(list) }
        adapter.notifyDataSetChanged()
    }

    private fun initiateObjects() {
        items = mutableListOf()
        adapter = FootballStandingAdapter(standingContext, items)
        presenter= StandingPresenter(contextProvider, this, AppRepository())

        rv_standing.layoutManager = LinearLayoutManager(standingContext)
        rv_standing.adapter = adapter
        rv_standing.setHasFixedSize(true)
    }
}
