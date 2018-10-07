package com.sample.githubapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sample.githubapp.trending.GithubClient
import com.sample.githubapp.trending.GithubRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    fun getTrendingRepositories(period: GithubClient.TrendingPeriod): Observable<List<GithubRepo>> {
        val githubClient = ServiceGenerator.createService(GithubClient::class.java)
        return githubClient
                .fetchTrendingRepos(period.periodString)
                .subscribeOn(Schedulers.io())
    }

}