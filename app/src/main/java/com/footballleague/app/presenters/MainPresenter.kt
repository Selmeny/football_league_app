package com.footballleague.app.presenters

import android.content.Context
import com.footballleague.app.R
import com.footballleague.app.helpers.CoroutineContextProvider
import com.footballleague.app.models.footballleague.FootballLeagueModel
import com.footballleague.app.views.MainView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainPresenter(private val corContext: CoroutineContextProvider = CoroutineContextProvider(),
                    private val context: Context,
                    private val view: MainView) {
    fun generateData() {
        GlobalScope.launch(corContext.main) {
            val names = context.resources.getStringArray(R.array.leagueNames)
            val ids = context.resources.getIntArray(R.array.leagueIDs)
            val images = context.resources.obtainTypedArray(R.array.leagueImages)

            val data: MutableList<FootballLeagueModel> = mutableListOf()
            data.clear()

            for (i in names.indices) {
                data.add(
                    FootballLeagueModel(
                        names[i],
                        ids[i],
                        images.getResourceId(i, 0)
                    )
                )
            }

            images.recycle()
            view.loadData(data)
            view.isLoading(false)
        }
    }
}