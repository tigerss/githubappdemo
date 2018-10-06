package com.sample.githubtrending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.disposables.CompositeDisposable

class RepositoriesFragment : Fragment() {

    private lateinit var subscriptions: CompositeDisposable
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.trending_fragment, container, true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView = view.findViewById<RecyclerView>(R.id.trending_list)

    }

    override fun onResume() {
        super.onResume()

//        getSubscriptions()
//                .add(
//                        mainViewModel.get
//                )
    }

    override fun onPause() {
        super.onPause()
    }

    private fun getSubscriptions(): CompositeDisposable {
        if (null == subscriptions || subscriptions.isDisposed) {
            subscriptions = CompositeDisposable()
        }
        return subscriptions
    }
}