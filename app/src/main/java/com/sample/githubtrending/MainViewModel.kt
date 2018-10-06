package com.sample.githubtrending

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {

    }

    fun getTrendingRepositories() {
        val githubClient = ServiceGenerator.createService(GithubClient::class.java)
        githubClient.fetchTrendingRepos(GithubClient.TrendingPeriod.WEEKLY.name)
    }

}