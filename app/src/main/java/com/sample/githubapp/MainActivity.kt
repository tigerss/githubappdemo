package com.sample.githubapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sample.githubapp.trending.RepositoriesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (null == savedInstanceState) {
            addTrendingFragment()
        }
    }

    private fun addTrendingFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.main_content, RepositoriesFragment())
                .commit()
    }
}
