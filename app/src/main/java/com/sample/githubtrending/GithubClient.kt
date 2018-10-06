package com.sample.githubtrending

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubClient {

    enum class TrendingPeriod(val period: String) {
        DAILY("daily"),
        WEEKLY("weekly"),
        MONTHLY("monthly")
    }

    @GET("/repositories")
    fun fetchTrendingRepos(@Query("since") since: String): Observable<Repo>
}