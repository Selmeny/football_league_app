package com.footballleague.app.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.constants.*
import com.footballleague.app.fragments.AllMatchFragment
import com.footballleague.app.fragments.StandingsFragment
import com.footballleague.app.fragments.TeamFragment
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.helpers.CustomPagerAdapter
import com.footballleague.app.models.footballleague.FootballLeagueModel
import com.footballleague.app.models.footballleaguedetail.FootballLeagueDetailModel
import com.footballleague.app.presenters.DetailLeaguePresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.DetailLeagueView
import kotlinx.android.synthetic.main.activity_detail_league.*

class DetailLeagueActivity : AppCompatActivity(), SearchView.OnQueryTextListener, DetailLeagueView {
    private lateinit var items: MutableList<FootballLeagueDetailModel>
    private lateinit var titles: MutableList<String>
    private lateinit var fragments: MutableList<Fragment>
    private lateinit var presenter: DetailLeaguePresenter
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_league)

        setSupportActionBar(tb_detail_league)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initiateObjects()
        loadInitialData()
        initiateTask()
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_detail_league_loading.visibility = View.VISIBLE
        } else{
            fl_detail_league_loading.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean) {
        if (state) {
            fl_detail_league_failed.visibility = View.VISIBLE
        } else{
            fl_detail_league_failed.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        searchView = searchItem?.actionView as SearchView

        searchView.queryHint = resources.getString(R.string.search_match)
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            if (query.isNotEmpty()){
                val intent = Intent(this, SearchActivity::class.java)
                intent.putExtra(FOOTBALL_SEARCH_QUERY, query)
                startActivity(intent)

                searchView.setQuery("", false)
                searchView.clearFocus()
                searchView.hidekeyboard()
                return true
            }
        }

        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun loadData(list: MutableList<FootballLeagueDetailModel>) {
        if (list.isNotEmpty()) {
            items.clear()
            items.addAll(list)

            val detailLeague = items[0]

            val badge = detailLeague.leagueBadge
            if (badge != null && badge.isNotEmpty()) {
                Glide.with(img_detail_league_logo)
                    .load(badge)
                    .into(img_detail_league_logo)
            } else{
                Glide.with(img_detail_league_logo)
                    .load(R.drawable.no_image)
                    .into(img_detail_league_logo)
            }

            val name = detailLeague.leagueName
            if (name != null && name.isNotEmpty()) {
                tv_detail_league_name.text = name
            } else{
                tv_detail_league_name.text = "-"
            }

            val formed = detailLeague.leagueFormed
            if (formed != null && formed.isNotEmpty()) {
                tv_detail_league_formed.text = formed
            } else{
                tv_detail_league_formed.text = "-"
            }

            val origin = detailLeague.leagueOrigin
            if (origin != null && origin.isNotEmpty()) {
                tv_detail_league_origin.text = origin
            } else{
                tv_detail_league_origin.text = "-"
            }

            val web = detailLeague.leagueWebsite
            img_detail_league_web.setOnClickListener {
                if (web != null && web.isNotEmpty()) {
                    openURL(web)
                } else{
                    Toast.makeText(this, getString(R.string.no_address_found), Toast.LENGTH_SHORT).show()
                }
            }

            val facebook = detailLeague.leagueFB
            img_detail_league_facebook.setOnClickListener {
                if (facebook != null && facebook.isNotEmpty()) {
                    openURL(facebook)
                } else{
                    Toast.makeText(this, getString(R.string.no_acocunt_found), Toast.LENGTH_SHORT).show()
                }
            }

            val twitter = detailLeague.leagueTwitter
            img_detail_league_twitter.setOnClickListener {
                if (twitter != null && twitter.isNotEmpty()) {
                    openURL(twitter)
                } else{
                    Toast.makeText(this, getString(R.string.no_acocunt_found), Toast.LENGTH_SHORT).show()
                }
            }

            val youtube = detailLeague.leagueYoutube
            img_detail_league_youtube.setOnClickListener {
                if (youtube != null && youtube.isNotEmpty()) {
                    openURL(youtube)
                } else{
                    Toast.makeText(this, getString(R.string.no_acocunt_found), Toast.LENGTH_SHORT).show()
                }
            }

            val description = detailLeague.leagueDescription
            if (description != null && description.isNotEmpty()) {
                tv_detail_league_description.text = description
            } else{
                tv_detail_league_description.text = "-"
            }
        }
    }

    private fun setupTabs(leagueID: String) {
        setupViewPager(vp_detail_league, leagueID)
        tab_detail_league.setupWithViewPager(vp_detail_league)
    }

    private fun setupViewPager(view: ViewPager, leagueID: String) {
        titles = mutableListOf(FOOTBALL_LIST_MATCH, FOOTBALL_STANDINGS, FOOTBALL_TEAM)
        fragments = mutableListOf(
            AllMatchFragment(leagueID),
            StandingsFragment(leagueID),
            TeamFragment(leagueID) )

        val adapter = CustomPagerAdapter(supportFragmentManager, titles, fragments)
        view.adapter = adapter
    }

    private fun initiateObjects() {
        items = mutableListOf()
        presenter = DetailLeaguePresenter(CoroutineContextProvider(),this, AppRepository())
    }

    private fun loadInitialData() {
        Glide.with(img_detail_league_web)
            .load(R.drawable.website)
            .into(img_detail_league_web)

        Glide.with(img_detail_league_facebook)
            .load(R.drawable.facebook)
            .into(img_detail_league_facebook)

        Glide.with(img_detail_league_twitter)
            .load(R.drawable.twitter)
            .into(img_detail_league_twitter)

        Glide.with(img_detail_league_youtube)
            .load(R.drawable.youtube)
            .into(img_detail_league_youtube)
    }

    private fun initiateTask() {
        val league: FootballLeagueModel? = intent.getParcelableExtra(FOOTBALL_LEAGUE_DATA)
        league?.id?.let {
            presenter.getDetailLeague(it.toString())
            setupTabs(it.toString())
        }
    }

    private fun openURL(address: String) {
        val page: Uri = Uri.parse("https://$address")
        val intent = Intent(Intent.ACTION_VIEW, page)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun View.hidekeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}

