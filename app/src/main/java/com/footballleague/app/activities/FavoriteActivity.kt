package com.footballleague.app.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.footballleague.app.R
import com.footballleague.app.constants.FOOTBALL_LAST_MATCH
import com.footballleague.app.constants.FOOTBALL_NEXT_MATCH
import com.footballleague.app.constants.FOOTBALL_TEAM
import com.footballleague.app.fragments.MatchFragment
import com.footballleague.app.fragments.TeamFragment
import com.footballleague.app.helpers.CustomPagerAdapter
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {
    private lateinit var titles: MutableList<String>
    private lateinit var fragments: MutableList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        setSupportActionBar(tb_favorite)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        setupTabs()
    }

    private fun setupTabs() {
        setupViewPager(vp_favorite)
        tab_favorite.setupWithViewPager(vp_favorite)
    }

    private fun setupViewPager(view: ViewPager) {
        titles = mutableListOf(FOOTBALL_LAST_MATCH, FOOTBALL_NEXT_MATCH, FOOTBALL_TEAM)
        fragments = mutableListOf(
            MatchFragment(FOOTBALL_LAST_MATCH, null),
            MatchFragment(FOOTBALL_NEXT_MATCH, null),
            TeamFragment(null)
        )

        val adapter = CustomPagerAdapter(supportFragmentManager, titles, fragments)
        view.adapter = adapter
    }
}
