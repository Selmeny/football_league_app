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
import com.footballleague.app.adapters.FootballMatchAdapter
import com.footballleague.app.database.FavoritesDatabase
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.presenters.MatchPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.MatchView
import kotlinx.android.synthetic.main.fragment_match.*
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive

class MatchFragment(private val flag: String, private val leagueID: String?): Fragment(), MatchView {
    private lateinit var matchContext: Context
    private lateinit var adapter: FootballMatchAdapter
    private lateinit var items: MutableList<FootballLeagueMatchModel>
    private lateinit var presenter: MatchPresenter
    private lateinit var handler: Handler
    private var databaseInstance: FavoritesDatabase? = null

    private val contextProvider = CoroutineContextProvider()

    override fun onAttach(context: Context) {
        matchContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initiateObjects()
        leagueID?.let {
            presenter.getMatch(flag, it)
        }
    }

    override fun onResume() {
        super.onResume()
        if (leagueID == null) {
            presenter.getFavoriteMatches(flag, databaseInstance, handler)
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
            fl_match_loading?.visibility = View.VISIBLE
        } else{
            fl_match_loading?.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean, code: Int) {
        when (code) {
            0 -> tv_match_failed?.text = ""
            1 -> tv_match_failed?.text = matchContext.getString(R.string.network_error_try_again_in_few_minutes)
            2 -> tv_match_failed?.text = matchContext.getString(R.string.favorite_match_not_found)
        }

        if (state) {
            fl_match_failed?.visibility = View.VISIBLE
        } else{
            fl_match_failed?.visibility = View.GONE
        }
    }

    override fun loadData(list: MutableList<FootballLeagueMatchModel>, type: String?) {
        items.clear()
        items.addAll(list)
        adapter.notifyDataSetChanged()
    }

    private fun initiateObjects() {
        items = mutableListOf()
        adapter = FootballMatchAdapter(matchContext, items, flag)
        presenter = MatchPresenter(contextProvider, this, AppRepository())
        handler = Handler()
        databaseInstance = FavoritesDatabase.getInstance(matchContext)

        rv_match.layoutManager = LinearLayoutManager(matchContext)
        rv_match.adapter = adapter
        rv_match.setHasFixedSize(true)
    }
}
