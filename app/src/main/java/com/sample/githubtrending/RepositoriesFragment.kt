package com.sample.githubtrending

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

private const val TAG = "RepositoriesFragment"

class RepositoriesFragment : Fragment() {

    private var subscriptions: CompositeDisposable = CompositeDisposable()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.trending_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.trending_list)

    }

    override fun onResume() {
        super.onResume()

        val disposable = mainViewModel.getTrendingRepositories(GithubClient.TrendingPeriod.WEEKLY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { repositories ->
                            for (repo in repositories) {
                                Log.d(TAG, repo.toString())
                            }
                        },
                        {
                            Log.e(TAG, "getTrendingRepositories", it)
                        }
                )
        getSubscriptions()
                .add(disposable)
    }

    override fun onPause() {
        super.onPause()

        subscriptions.dispose()
    }

    private fun getSubscriptions(): CompositeDisposable {
        if (subscriptions.isDisposed) {
            subscriptions = CompositeDisposable()
        }
        return subscriptions
    }
}