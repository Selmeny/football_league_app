package com.footballleague.app.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.constants.FOOTBALL_TEAM_DATA
import com.footballleague.app.constants.FOOTBALL_TEAM_INFO
import com.footballleague.app.constants.FOOTBALL_TEAM_PLAYER
import com.footballleague.app.database.FavoritesDatabase
import com.footballleague.app.fragments.TeamInfoFragment
import com.footballleague.app.fragments.TeamPlayerFragment
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.helpers.CustomPagerAdapter
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel
import com.footballleague.app.models.footballleagueteamfavorite.FootballLeagueTeamFavModel
import com.footballleague.app.presenters.DetailTeamPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.DetailTeamView
import kotlinx.android.synthetic.main.activity_detail_team.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailTeamActivity : AppCompatActivity(), DetailTeamView {
    private lateinit var titles: MutableList<String>
    private lateinit var fragments: MutableList<Fragment>
    private lateinit var presenter: DetailTeamPresenter
    private lateinit var favorite: FootballLeagueTeamFavModel
    private var favState: Boolean = false
    private var team: FootballLeagueTeamModel? = null
    private var teamId: String? = null
    private var teamBadge: String? = null
    private var teamName: String? = null
    private var teamFormed: String? = null
    private var teamNickname: String? = null
    private var databaseInstance: FavoritesDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        setSupportActionBar(tb_detail_team)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initiateObjects()
        loadInitialData()
        initiateTask()
    }

    override fun onDestroy() {
        FavoritesDatabase.deleteInstance()
        super.onDestroy()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return false
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_detail_team_loading.visibility = View.VISIBLE
        } else {
            fl_detail_team_loading.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean) {
        if (state) {
            fl_detail_team_failed.visibility = View.VISIBLE
        } else {
            fl_detail_team_failed.visibility = View.GONE
        }
    }

    override fun loadData(list: MutableList<FootballLeagueTeamModel>) {
        if (list.isNotEmpty()) {
            val detailTeam = list[0]

            val badge = detailTeam.teamBadge
            if (badge != null && badge.isNotEmpty()) {
                Glide.with(img_detail_team_logo)
                    .load(badge)
                    .into(img_detail_team_logo)
            } else{
                Glide.with(img_detail_team_logo)
                    .load(R.drawable.no_image)
                    .into(img_detail_team_logo)
            }

            val name = detailTeam.teamName
            if (name != null && name.isNotEmpty()) {
                tv_detail_team_name.text = name
            } else{
                tv_detail_team_name.text = "-"
            }

            val formed = detailTeam.teamFormed
            if (formed != null && formed.isNotEmpty()) {
                tv_detail_team_formed.text = formed
            } else{
                tv_detail_team_formed.text = "-"
            }

            val origin = detailTeam.teamOrigin
            if (origin != null && origin.isNotEmpty()) {
                tv_detail_team_origin.text = origin
            } else{
                tv_detail_team_origin.text = "-"
            }

            val web = detailTeam.teamWebsite
            img_detail_team_web.setOnClickListener {
                if (web != null && web.isNotEmpty()) {
                    openURL(web)
                } else{
                    Toast.makeText(this, getString(R.string.no_address_found), Toast.LENGTH_SHORT).show()
                }
            }

            val facebook = detailTeam.teamFacebook
            img_detail_team_facebook.setOnClickListener {
                if (facebook != null && facebook.isNotEmpty()) {
                    openURL(facebook)
                } else{
                    Toast.makeText(this, getString(R.string.no_acocunt_found), Toast.LENGTH_SHORT).show()
                }
            }

            val twitter = detailTeam.teamTwitter
            img_detail_team_twitter.setOnClickListener {
                if (twitter != null && twitter.isNotEmpty()) {
                    openURL(twitter)
                } else{
                    Toast.makeText(this, getString(R.string.no_acocunt_found), Toast.LENGTH_SHORT).show()
                }
            }

            val youtube = detailTeam.teamYoutube
            img_detail_team_youtube.setOnClickListener {
                if (youtube != null && youtube.isNotEmpty()) {
                    openURL(youtube)
                } else{
                    Toast.makeText(this, getString(R.string.no_acocunt_found), Toast.LENGTH_SHORT).show()
                }
            }

            val description = detailTeam.teamDescription
            if (description != null && description.isNotEmpty()) {
                tv_detail_team_description.text = description
            } else{
                tv_detail_team_description.text = "-"
            }
        }
    }

    private fun initiateTask() {
        team = intent.getParcelableExtra(FOOTBALL_TEAM_DATA)
        teamId = team?.teamID
        teamBadge = team?.teamBadge
        teamName = team?.teamName
        teamFormed = team?.teamFormed
        teamNickname = team?.teamNickname

        teamId?.let {
            presenter.getTeamDetail(it)
            checkFavorite(it)
            setupTabs(it)
        }

        img_detail_team_fav.setOnClickListener {
            if (favState) {
                deleteFavorite()
            } else {
                setFavorite()
            }
        }
    }

    private fun initiateObjects() {
        presenter = DetailTeamPresenter(CoroutineContextProvider(),this, AppRepository())
        databaseInstance = FavoritesDatabase.getInstance(this)
    }

    private fun loadInitialData() {
        Glide.with(img_detail_team_web)
            .load(R.drawable.website)
            .into(img_detail_team_web)

        Glide.with(img_detail_team_facebook)
            .load(R.drawable.facebook)
            .into(img_detail_team_facebook)

        Glide.with(img_detail_team_twitter)
            .load(R.drawable.twitter)
            .into(img_detail_team_twitter)

        Glide.with(img_detail_team_youtube)
            .load(R.drawable.youtube)
            .into(img_detail_team_youtube)
    }

    private fun setupTabs(teamID: String) {
        setupViewPager(vp_detail_team, teamID)
        tab_detail_team.setupWithViewPager(vp_detail_team)
    }

    private fun setupViewPager(view: ViewPager, teamID: String) {
        titles = mutableListOf(FOOTBALL_TEAM_INFO, FOOTBALL_TEAM_PLAYER)
        fragments = mutableListOf(
            TeamInfoFragment(teamID),
            TeamPlayerFragment(teamID))

        val adapter = CustomPagerAdapter(supportFragmentManager, titles, fragments)
        view.adapter = adapter
    }

    private fun checkFavorite(teamID: String?) {
        GlobalScope.launch {
            val favorites = databaseInstance?.teamFavoritesDao()?.getAll()
            if (favorites != null && favorites.isNotEmpty()) {
                for (model in favorites) {
                    if (model.teamID.equals(teamID, true)) {
                        favState = true
                        favorite = model
                        setFavoriteIcon(favState)
                        break
                    }
                }
            }
        }
    }

    private fun setFavorite() {
        GlobalScope.launch {
            val favorite = FootballLeagueTeamFavModel(teamId, teamBadge, teamName, teamFormed, teamNickname)
            databaseInstance?.teamFavoritesDao()?.insert(favorite)
            favState = true
            setFavoriteIcon(favState)
        }
    }

    private fun deleteFavorite() {
        GlobalScope.launch {
            databaseInstance?.teamFavoritesDao()?.delete(favorite)
            favState = false
            setFavoriteIcon(favState)
        }
    }

    private fun setFavoriteIcon(state: Boolean) {
        if (state) {
            img_detail_team_fav.setImageResource(R.drawable.ic_favorite)
        } else{
            img_detail_team_fav.setImageResource(R.drawable.ic_favorite_border)
        }
    }

    private fun openURL(address: String) {
        val page: Uri = Uri.parse("https://$address")
        val intent = Intent(Intent.ACTION_VIEW, page)
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
