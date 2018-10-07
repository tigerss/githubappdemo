package com.sample.githubapp.trending

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.githubapp.MainViewModel
import com.sample.githubapp.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class RepositoriesFragment : Fragment() {

    companion object {
        private const val TAG = "RepositoriesFragment"
    }

    private lateinit var loading: ProgressBar
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
        loading = view.findViewById(R.id.repositoriesLoading)

        repoRecycler = view.findViewById(R.id.repositoriesList)
        repoRecycler.layoutManager = LinearLayoutManager(context)

        repoAdapter = RepositoriesAdapter(ArrayList(), this::onRepositoryClicked)
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

    private fun onRepositoryClicked(view: View, repository: GithubRepo) {
        var repositoryDetailFragment = RepositoryDetailFragment.newInstance(repository)
        fragmentManager
                ?.beginTransaction()
                ?.replace(R.id.main_content, repositoryDetailFragment, RepositoryDetailFragment.TAG)
                ?.addToBackStack(null)
                ?.commit()
    }

    private fun bindViewModel() {
        val disposable = mainViewModel.getTrendingRepositories(GithubClient.TrendingPeriod.WEEKLY)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showLoading() }
                .doOnTerminate { hideLoading() }
                .subscribe(
                        { repositories ->
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

    private fun showLoading() {
        loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        loading.visibility = View.GONE
    }

    private fun getSubscriptions(): CompositeDisposable {
        if (subscriptions.isDisposed) {
            subscriptions = CompositeDisposable()
        }
        return subscriptions
    }
}