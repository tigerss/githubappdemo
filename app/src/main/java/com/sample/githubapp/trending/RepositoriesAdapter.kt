package com.sample.githubapp.trending

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.repositories_list_item.view.*
import com.sample.githubapp.R

typealias RepoClickListener = (View, GithubRepo) -> Unit

class RepositoriesAdapter(
        items:List<GithubRepo>,
        private val onRepoClickListener: RepoClickListener
): RecyclerView.Adapter<RepositoriesAdapter.ViewHolder>() {

    private var repositories: List<GithubRepo> = items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
                LayoutInflater.from(parent.context)
                .inflate(R.layout.repositories_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repository = repositories[position]
        holder.tvRepoName.text = repository.name

        holder.itemView.setOnClickListener { view ->
            onRepoClickListener(view, repository)
        }
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    fun setRepositories(repositoriesList: List<GithubRepo>) {
        this.repositories = repositoriesList
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvRepoName: TextView = view.repositoriesName
    }
}