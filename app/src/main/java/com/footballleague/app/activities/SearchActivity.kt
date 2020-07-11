package com.footballleague.app.activities

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.footballleague.app.R
import com.footballleague.app.adapters.FootballMatchAdapter
import com.footballleague.app.adapters.FootballTeamAdapter
import com.footballleague.app.constants.FOOTBALL_EVENT_SORT
import com.footballleague.app.constants.FOOTBALL_SEARCH_QUERY
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel
import com.footballleague.app.presenters.SearchDataPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.SearchDataView
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), SearchDataView, SearchView.OnQueryTextListener {
    private lateinit var matches: MutableList<FootballLeagueMatchModel>
    private lateinit var teams: MutableList<FootballLeagueTeamModel>
    private lateinit var matchAdapter: FootballMatchAdapter
    private lateinit var teamAdapter: FootballTeamAdapter
    private lateinit var presenter: SearchDataPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(tb_search_data)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        sv_search_match.setOnQueryTextListener(this)

        initiateObjects()
        initiateTask()
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_search_data_loading.visibility = View.VISIBLE
        } else{
            fl_search_data_loading.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean) {
        if (state) {
            fl_search_data_failed.visibility = View.VISIBLE
        } else {
            fl_search_data_failed.visibility = View.GONE
        }
    }

    override fun loadMatches(list: MutableList<FootballLeagueMatchModel>) {
        if (list.isNotEmpty()) {
            val sortedList: MutableList<FootballLeagueMatchModel> = mutableListOf()

            for (match in list) {
                if (match.matchType.equals(FOOTBALL_EVENT_SORT, true)) {
                    sortedList.add(match)
                }
            }

            if (matches.isEmpty() && teams.isEmpty()) {
                tv_search_data_failed.text = getString(R.string.data_not_found)
            }

            matches.clear()
            matches.addAll(sortedList)
            matchAdapter.notifyDataSetChanged()
        }
    }

    override fun loadTeams(list: MutableList<FootballLeagueTeamModel>) {
        if (list.isNotEmpty()) {
            val sortedList: MutableList<FootballLeagueTeamModel> = mutableListOf()

            for (team in list) {
                if (team.teamType.equals(FOOTBALL_EVENT_SORT, true)) {
                    sortedList.add(team)
                }
            }

            if (matches.isEmpty() && teams.isEmpty()) {
                tv_search_data_failed.text = getString(R.string.data_not_found)
            }

            teams.clear()
            teams.addAll(sortedList)
            teamAdapter.notifyDataSetChanged()
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            if (query.isNotEmpty()) {
                isLoading(true)
                presenter.getSearchTeams(query)
                presenter.getSearchMatches(query)
                sv_search_match.hidekeyboard()
                return true
            }
        }
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun initiateObjects() {
        matches = mutableListOf()
        teams = mutableListOf()

        matchAdapter = FootballMatchAdapter(this, matches, null)
        teamAdapter = FootballTeamAdapter(this, teams)

        presenter = SearchDataPresenter(CoroutineContextProvider(), this, AppRepository())

        rv_search_match.layoutManager = LinearLayoutManager(this)
        rv_search_match.adapter = matchAdapter

        rv_search_team.layoutManager = LinearLayoutManager(this)
        rv_search_team.adapter = teamAdapter
    }

    private fun initiateTask() {
        val query: String? = intent.getStringExtra(FOOTBALL_SEARCH_QUERY)
        query?.let {
            isLoading(true)
            presenter.getSearchTeams(query)
            presenter.getSearchMatches(query)
        }?: kotlin.run {
            isFailed(false)
            isLoading(false)
        }
    }

    private fun View.hidekeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
