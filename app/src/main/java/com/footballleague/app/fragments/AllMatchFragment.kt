package com.footballleague.app.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.footballleague.app.R
import com.footballleague.app.adapters.FootballAllMatchAdapter
import com.footballleague.app.constants.FOOTBALL_LAST_MATCH
import com.footballleague.app.constants.FOOTBALL_NEXT_MATCH
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.presenters.MatchPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.MatchView
import kotlinx.android.synthetic.main.fragment_all_match.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

class AllMatchFragment(private val leagueID: String) : Fragment(), MatchView {
    private lateinit var allMatchContext: Context
    private lateinit var nextMatchAdapter: FootballAllMatchAdapter
    private lateinit var lastMatchAdapter: FootballAllMatchAdapter
    private lateinit var nextItems: MutableList<FootballLeagueMatchModel>
    private lateinit var lastItems: MutableList<FootballLeagueMatchModel>
    private lateinit var presenter: MatchPresenter

    private val contextProvider = CoroutineContextProvider()

    override fun onAttach(context: Context) {
        allMatchContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateObjects()

        presenter.getMatch(FOOTBALL_NEXT_MATCH, leagueID)
        presenter.getMatch(FOOTBALL_LAST_MATCH, leagueID)
    }

    override fun onDestroy() {
        if (contextProvider.main.isActive) {
            contextProvider.main.cancel()
        }
        super.onDestroy()
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_all_match_loading?.visibility = View.VISIBLE
        } else{
            fl_all_match_loading?.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean, code: Int) {
        when (code) {
            0 -> tv_all_match_failed?.text = ""
            1 -> tv_all_match_failed?.text = allMatchContext.getString(R.string.network_error_try_again_in_few_minutes)
            2 -> tv_all_match_failed?.text = allMatchContext.getString(R.string.favorite_match_not_found)
        }

        if (state) {
            fl_all_match_failed?.visibility = View.VISIBLE
        } else{
            fl_all_match_failed?.visibility = View.GONE
        }
    }

    override fun loadData(list: MutableList<FootballLeagueMatchModel>, type: String?) {
        if (list.isNotEmpty()) {
            type?.let {
                if (type.equals(FOOTBALL_LAST_MATCH, false)) {
                    lastItems.clear()
                    lastItems.addAll(list)
                    lastMatchAdapter.notifyDataSetChanged()
                } else if (type.equals(FOOTBALL_NEXT_MATCH, false)) {
                    nextItems.clear()
                    nextItems.addAll(list)
                    nextMatchAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun initiateObjects() {
        nextItems = mutableListOf()
        lastItems = mutableListOf()
        nextMatchAdapter = FootballAllMatchAdapter(allMatchContext, nextItems, FOOTBALL_NEXT_MATCH)
        lastMatchAdapter = FootballAllMatchAdapter(allMatchContext, lastItems, FOOTBALL_LAST_MATCH)
        presenter = MatchPresenter(contextProvider, this, AppRepository())

        rv_last_match.layoutManager = LinearLayoutManager(allMatchContext, LinearLayoutManager.HORIZONTAL, false)
        rv_last_match.adapter = lastMatchAdapter
        rv_last_match.setHasFixedSize(true)

        rv_next_match.layoutManager = LinearLayoutManager(allMatchContext, LinearLayoutManager.HORIZONTAL, false)
        rv_next_match.adapter = nextMatchAdapter
        rv_last_match.setHasFixedSize(true)
    }
}
