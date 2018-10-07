package com.sample.githubapp.trending

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class GithubRepo(
        var author: String = "",
        var name: String = "",
        var url: String = "",
        var description: String = "",
        var language: String = "",
        var stars: Int = 0,
        var forks: Int = 0
): Parcelable {

    override fun toString(): String {
        return "name $name author $author stars $stars"
    }
}
