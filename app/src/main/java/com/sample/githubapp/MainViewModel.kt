package com.sample.githubapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.sample.githubapp.trending.GithubClient
import com.sample.githubapp.trending.GithubRepo
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var cacheGithubRepos: HashMap<GithubClient.TrendingPeriod, List<GithubRepo>> = HashMap()

    fun getTrendingRepositories(period: GithubClient.TrendingPeriod): Observable<List<GithubRepo>> {
        return Observable.concat(
                getLocalTrendingRepositories(period),
                getRemoteTrendingRepositories(period)
        ).filter { list -> !list.isEmpty()}
    }

    private fun getRemoteTrendingRepositories(period: GithubClient.TrendingPeriod): Observable<List<GithubRepo>> {
        val githubClient = ServiceGenerator.createService(GithubClient::class.java)
        return githubClient
                .fetchTrendingRepos(period.periodString)
                .doOnNext { repoList -> cacheGithubRepos[period] = repoList }
                .subscribeOn(Schedulers.io())
    }

    private fun getLocalTrendingRepositories(period: GithubClient.TrendingPeriod): Observable<List<GithubRepo>> {
        val repoList = if (cacheGithubRepos.containsKey(period)) {
            cacheGithubRepos[period]
        } else {
            ArrayList()
        }

        return Observable.just(repoList)
    }
}