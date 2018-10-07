package com.sample.githubapp.trending

import com.sample.githubapp.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class RepositoryDetailFragment : Fragment() {

    companion object {
        const val TAG = "RepositoryDetail"

        private const val ARG_REPO = "arg_repo"

        fun newInstance(repo: GithubRepo): RepositoryDetailFragment {
            val args = Bundle()
            args.putParcelable(ARG_REPO, repo)
            val fragment = RepositoryDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.repository_detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val githubRepo = readGithubRepo()
        view.findViewById<TextView>(R.id.repoDetailAuthorValue).text = githubRepo.author
        view.findViewById<TextView>(R.id.repoDetailNameValue).text = githubRepo.name
        view.findViewById<TextView>(R.id.repoDetailUrlValue).text = githubRepo.url
        view.findViewById<TextView>(R.id.repoDetailDescriptionValue).text = githubRepo.description
        view.findViewById<TextView>(R.id.repoDetailLangValue).text = githubRepo.language
        view.findViewById<TextView>(R.id.repoDetailStarsValue).text = githubRepo.stars.toString()
    }

    private fun readGithubRepo(): GithubRepo {
        if (null == arguments) {
            return GithubRepo()
        }

        val hasRepo = arguments!!.containsKey(ARG_REPO)
        if (!hasRepo) {
            return GithubRepo()
        }

        return arguments!!.getParcelable(ARG_REPO)
    }
}