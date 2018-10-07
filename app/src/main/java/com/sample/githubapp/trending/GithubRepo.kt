package com.sample.githubapp.trending

class GithubRepo {

    var name: String = ""
    var stars: Int = 0

    override fun toString(): String {
        return "name $name stars $stars"
    }
}
