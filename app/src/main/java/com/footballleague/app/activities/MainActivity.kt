package com.footballleague.app.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.footballleague.app.R
import com.footballleague.app.adapters.FootballLeagueAdapter
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleague.FootballLeagueModel
import com.footballleague.app.presenters.MainPresenter
import com.footballleague.app.views.MainView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var items: MutableList<FootballLeagueModel>
    private lateinit var adapter: FootballLeagueAdapter
    private lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initiateObjects()
        initiateTask()

        presenter.generateData()
    }

    override fun isLoading(state: Boolean) {
        if (state) {
            fl_main_loading.visibility = View.VISIBLE
        } else{
            fl_main_loading.visibility = View.GONE
        }
    }

    override fun loadData(list: MutableList<FootballLeagueModel>) {
        if (list.isNotEmpty()) {
            rv_main.layoutManager = GridLayoutManager(this, 2)
            rv_main.adapter = adapter
            items.clear()
            items.addAll(list)
            adapter.notifyDataSetChanged()
        }
    }

    private fun initiateObjects() {
        items = mutableListOf()
        adapter = FootballLeagueAdapter(this, items)
        presenter = MainPresenter(CoroutineContextProvider(), this, this)
    }

    private fun initiateTask() {
        img_main_fav.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }
    }
}
