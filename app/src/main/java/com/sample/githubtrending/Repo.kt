package com.sample.githubtrending

class Repo {

    var name: String = ""
    var stars: Int = 0

    override fun toString(): String {
        return "name $name stars $stars"
    }
}
