package com.sample.githubapp.trending

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.githubapp.MainViewModel
import com.sample.githubapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

private const val TAG = "RepositoriesFragment"

class RepositoriesFragment : Fragment() {

    private lateinit var repoRecycler: RecyclerView
    private lateinit var repoAdapter: RepositoriesAdapter

    private var subscriptions: CompositeDisposable = CompositeDisposable()
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repositories_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        repoRecycler = view.findViewById(R.id.repositoriesList)
        repoRecycler.layoutManager = LinearLayoutManager(context)

        repoAdapter = RepositoriesAdapter(ArrayList())
        repoRecycler.adapter = repoAdapter
    }

    override fun onResume() {
        super.onResume()

        bindViewModel()
    }

    override fun onPause() {
        super.onPause()

        unbindViewModel()
    }

    private fun bindViewModel() {
        val disposable = mainViewModel.getTrendingRepositories(GithubClient.TrendingPeriod.WEEKLY)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { repositories ->
//                            for (repo in repositories) {
//                                Log.d(TAG, repo.toString())
//                            }
                            refreshRepositories(repositories)
                        },
                        {
                            Log.e(TAG, "getTrendingRepositories", it)
                        }
                )
        getSubscriptions()
                .add(disposable)
    }

    private fun refreshRepositories(repositories: List<GithubRepo>) {
        repoAdapter.setRepositories(repositories)
        repoAdapter.notifyDataSetChanged()
    }

    private fun unbindViewModel() {
        subscriptions.dispose()
    }

    private fun getSubscriptions(): CompositeDisposable {
        if (subscriptions.isDisposed) {
            subscriptions = CompositeDisposable()
        }
        return subscriptions
    }
}