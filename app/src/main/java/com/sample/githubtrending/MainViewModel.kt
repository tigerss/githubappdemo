package com.sample.githubtrending

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    init {

    }

    fun getTrendingRepositories(period: GithubClient.TrendingPeriod): Observable<List<Repo>> {
        val githubClient = ServiceGenerator.createService(GithubClient::class.java)
        return githubClient
                .fetchTrendingRepos(period.periodString)
                .subscribeOn(Schedulers.io())
    }

}