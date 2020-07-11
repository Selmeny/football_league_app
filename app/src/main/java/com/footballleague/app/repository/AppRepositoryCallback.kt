package com.footballleague.app.repository

interface AppRepositoryCallback<T> {
    fun onDataLoaded(data: T)
    fun onDataError()
}