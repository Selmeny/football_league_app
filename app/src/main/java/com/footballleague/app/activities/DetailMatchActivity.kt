package com.footballleague.app.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.footballleague.app.R
import com.footballleague.app.constants.FOOTBALL_MATCH
import com.footballleague.app.constants.FOOTBALL_MATCH_TYPE
import com.footballleague.app.database.FavoritesDatabase
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleaguematch.FootballLeagueMatchModel
import com.footballleague.app.models.footballleaguematchfavorite.FootballLeagueMatchFavModel
import com.footballleague.app.models.footballleagueteam.FootballLeagueTeamModel
import com.footballleague.app.presenters.DetailMatchPresenter
import com.footballleague.app.repository.AppRepository
import com.footballleague.app.views.DetailMatchView
import kotlinx.android.synthetic.main.activity_detail_match.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailMatchActivity : AppCompatActivity(), DetailMatchView {
    private lateinit var presenter: DetailMatchPresenter
    private lateinit var favorite: FootballLeagueMatchFavModel
    private var favState: Boolean = false
    private var match: FootballLeagueMatchModel? = null
    private var matchType: String? = null
    private var matchId: String? = null
    private var matchDate: String? = null
    private var matchTime: String? = null
    private var homeTeam: String? = null
    private var awayTeam: String? = null
    private var homeScore: String? = null
    private var awayScore: String? = null
    private var homeID: String? = null
    private var awayID: String? = null
    private var databaseInstance: FavoritesDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_match)

        setSupportActionBar(tb_detail_match)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        initiateObjects()
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
            fl_detail_match_loading.visibility = View.VISIBLE
        } else {
            fl_detail_match_loading.visibility = View.GONE
        }
    }

    override fun isFailed(state: Boolean) {
        if (state) {
            fl_detail_match_failed.visibility = View.VISIBLE
        } else {
            fl_detail_match_failed.visibility = View.GONE
        }
    }

    override fun isTeamFailed(state: Boolean, teamID: String) {
        if (state && teamID.equals(homeID, true)) {
            Glide.with(img_detail_match_badge_home)
                .load(R.drawable.no_image)
                .into(img_detail_match_badge_home)
        } else if (state && teamID.equals(awayID, true)) {
            Glide.with(img_detail_match_badge_away)
                .load(R.drawable.no_image)
                .into(img_detail_match_badge_away)
        }
    }

    override fun loadTeam(list: MutableList<FootballLeagueTeamModel>) {
        if (list.isNotEmpty()) {
            val team = list[0]
            val teamID = team.teamID
            val teamBadge = team.teamBadge

            if (teamID != null && teamBadge != null && teamID.equals(homeID, true)) {
                Glide.with(img_detail_match_badge_home)
                    .load(teamBadge)
                    .into(img_detail_match_badge_home)
            } else if (teamID != null && teamBadge != null && teamID.equals(awayID, true)) {
                Glide.with(img_detail_match_badge_away)
                    .load(teamBadge)
                    .into(img_detail_match_badge_away)
            }
        }
    }

    override fun loadData(list: MutableList<FootballLeagueMatchModel>) {
        if (list.isNotEmpty()) {
            val detailEvent = list[0]

            val date = detailEvent.matchDate
            if (date != null && date.isNotEmpty()) {
                tv_detail_match_date.text = date
            } else{
                tv_detail_match_date.text = "-"
            }

            val time = detailEvent.matchTime
            if (time != null && time.isNotEmpty()) {
                tv_detail_match_time.text = time
            } else{
                tv_detail_match_time.text = "-"
            }

            val homeScore = detailEvent.matchHomeScore
            if (homeScore != null && homeScore.isNotEmpty()) {
                tv_detail_match_home_score.text = homeScore
            } else {
                tv_detail_match_home_score.text = "-"
            }

            val awayScore = detailEvent.matchAwayScore
            if (awayScore != null && awayScore.isNotEmpty()) {
                tv_detail_match_away_score.text = awayScore
            } else{
                tv_detail_match_away_score.text = "-"
            }

            val home = detailEvent.matchHome
            if (home != null && home.isNotEmpty()) {
                tv_detail_match_home_team.text = (home + " " + resources.getString(R.string.H))
            } else{
                tv_detail_match_home_team.text = "-"
            }

            val away = detailEvent.matchAway
            if (away != null && away.isNotEmpty()) {
                tv_detail_match_away_team.text = (away + " " + resources.getString(R.string.A))
            } else{
                tv_detail_match_away_team.text = "-"
            }

            val homeFormation = detailEvent.matchHomeFormation
            if (homeFormation != null && homeFormation.isNotEmpty()) {
                tv_detail_match_formation_home.text = homeFormation
            } else {
                tv_detail_match_formation_home.text = "-"
            }

            val awayFormation = detailEvent.matchAwayFormation
            if (awayFormation != null && awayFormation.isNotEmpty()) {
                tv_detail_match_formation_away.text = awayFormation
            } else{
                tv_detail_match_formation_away.text = "-"
            }

            val homeRedCards = detailEvent.matchHomeRedCards
            if (homeRedCards != null && homeRedCards.isNotEmpty()) {
                tv_detail_match_red_cards_home.text = homeRedCards
            } else {
                tv_detail_match_red_cards_home.text = "-"
            }

            val awayRedCards = detailEvent.matchAwayRedCards
            if (awayRedCards != null && awayRedCards.isNotEmpty()) {
                tv_detail_match_red_cards_away.text = awayRedCards
            } else{
                tv_detail_match_red_cards_away.text = "-"
            }

            val homeYellowCards = detailEvent.matchHomeYellowCards
            if (homeYellowCards != null && homeYellowCards.isNotEmpty()) {
                tv_detail_match_yellow_cards_home.text = homeYellowCards
            } else{
                tv_detail_match_yellow_cards_home.text = "-"
            }

            val awayYellowCards = detailEvent.matchAwayYellowCards
            if (awayYellowCards != null && awayYellowCards.isNotEmpty()) {
                tv_detail_match_yellow_cards_away.text = awayYellowCards
            } else {
                tv_detail_match_yellow_cards_away.text = "-"
            }

            val homeShots = detailEvent.matchHomeShots
            if (homeShots != null && homeShots.isNotEmpty()) {
                tv_detail_match_shots_home.text = homeShots
            } else {
                tv_detail_match_shots_home.text = "-"
            }

            val awayShots = detailEvent.matchAwayShots
            if (awayShots != null && awayShots.isNotEmpty()) {
                tv_detail_match_shots_away.text = awayShots
            } else{
                tv_detail_match_shots_away.text = "-"
            }

            val homeScoreDetail = detailEvent.matchHomeScoreDetail
            if (homeScoreDetail != null && homeScoreDetail.isNotEmpty()) {
                tv_detail_match_goalscorers_home.text = homeScoreDetail
            } else {
                tv_detail_match_goalscorers_home.text = "-"
            }

            val awayScoreDetail = detailEvent.matchAwayScoreDetail
            if (awayScoreDetail != null && awayScoreDetail.isNotEmpty()) {
                tv_detail_match_goalscorers_away.text = awayScoreDetail
            } else{
                tv_detail_match_goalscorers_away.text = "-"
            }

            val homeGoalkeeper = detailEvent.matchHomeGoalkeeper
            if (homeGoalkeeper != null && homeGoalkeeper.isNotEmpty()) {
                tv_detail_match_goalkeeper_home.text = homeGoalkeeper
            } else {
                tv_detail_match_goalkeeper_home.text = "-"
            }

            val homeDefense = detailEvent.matchHomeDefense
            if (homeDefense != null && homeDefense.isNotEmpty()) {
                tv_detail_match_defense_home.text = homeDefense
            } else{
                tv_detail_match_defense_home.text = "-"
            }

            val homeMidfield = detailEvent.matchHomeMidfield
            if (homeMidfield != null && homeMidfield.isNotEmpty()) {
                tv_detail_match_midfield_home.text = homeMidfield
            } else{
                tv_detail_match_midfield_home.text = "-"
            }

            val homeForward = detailEvent.matchHomeForward
            if (homeForward != null && homeForward.isNotEmpty()) {
                tv_detail_match_forward_home.text = homeForward
            } else{
                tv_detail_match_forward_home.text = "-"
            }

            val homeSubstitute = detailEvent.matchHomeSubstitute
            if (homeSubstitute != null && homeSubstitute.isNotEmpty()) {
                tv_detail_match_subs_home.text = homeSubstitute
            } else{
                tv_detail_match_subs_home.text = "-"
            }

            val awayGoalkeeper = detailEvent.matchAwayGoalkeeper
            if (awayGoalkeeper != null && awayGoalkeeper.isNotEmpty()) {
                tv_detail_match_goalkeeper_away.text = awayGoalkeeper
            } else {
                tv_detail_match_goalkeeper_away.text = "-"
            }

            val awayDefense = detailEvent.matchAwayDefense
            if (awayDefense != null && awayDefense.isNotEmpty()) {
                tv_detail_match_defense_away.text = awayDefense
            } else{
                tv_detail_match_defense_away.text = "-"
            }

            val awayMidfield = detailEvent.matchAwayMidfield
            if (awayMidfield != null && awayMidfield.isNotEmpty()) {
                tv_detail_match_midfield_away.text = awayMidfield
            } else {
                tv_detail_match_midfield_away.text = "-"
            }

            val awayForward = detailEvent.matchAwayForward
            if (awayForward != null && awayForward.isNotEmpty()) {
                tv_detail_match_forward_away.text = awayForward
            } else {
                tv_detail_match_forward_away.text = "-"
            }

            val awaySubstitute = detailEvent.matchAwaySubstitute
            if (awaySubstitute != null && awaySubstitute.isNotEmpty()) {
                tv_detail_match_subs_away.text = awaySubstitute
            } else{
                tv_detail_match_subs_away.text = "-"
            }

            if (homeScore != null && homeScore.isNotEmpty() && awayScore != null && awayScore.isNotEmpty())
                tv_detail_match_status.visibility = View.VISIBLE
            else {
                tv_detail_match_status.visibility = View.GONE
            }
        }
    }

    private fun initiateObjects() {
        presenter = DetailMatchPresenter(CoroutineContextProvider(),this, AppRepository())
        databaseInstance = FavoritesDatabase.getInstance(this)
    }

    private fun initiateTask() {
        match = intent.getParcelableExtra(FOOTBALL_MATCH)
        matchType = intent.getStringExtra(FOOTBALL_MATCH_TYPE)
        matchDate = match?.matchDate
        matchTime = match?.matchTime
        homeTeam = match?.matchHome
        awayTeam = match?.matchAway
        homeScore = match?.matchHomeScore
        awayScore = match?.matchAwayScore
        matchId = match?.matchID
        homeID = match?.matchHomeID
        awayID = match?.matchAwayID

        matchId?.let {
            presenter.getEventDetail(it)
            checkFavorite(matchId)
        }

        homeID?.let {
            presenter.getTeamBadge(it)
        }

        awayID?.let {
            presenter.getTeamBadge(it)
        }

        img_detail_match_fav.setOnClickListener {
            if (favState) {
                deleteFavorite()
            } else{
                setFavorite()
            }
        }
    }

    private fun checkFavorite(matchId: String?) {
        GlobalScope.launch {
            val favorites = databaseInstance?.matchFavoritesDao()?.getAll()
            if (favorites != null && favorites.isNotEmpty()) {
                for (model in favorites) {
                    if (model.matchId.equals(matchId, true)) {
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
            val favorite = FootballLeagueMatchFavModel(matchType, matchId, matchDate, matchTime, homeTeam, awayTeam, homeScore, awayScore, homeID, awayID)
            databaseInstance?.matchFavoritesDao()?.insert(favorite)
            favState = true
            setFavoriteIcon(favState)
        }
    }

    private fun deleteFavorite() {
        GlobalScope.launch {
            databaseInstance?.matchFavoritesDao()?.delete(favorite)
            favState = false
            setFavoriteIcon(favState)
        }
    }

    private fun setFavoriteIcon(state: Boolean) {
        if (state) {
            img_detail_match_fav.setImageResource(R.drawable.ic_favorite)
        } else{
            img_detail_match_fav.setImageResource(R.drawable.ic_favorite_border)
        }
    }
}
