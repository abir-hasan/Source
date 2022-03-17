package com.example.abir.source.sample.paging_lib_sample_v1.ui

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.android.codelabs.paging.model.Repo

class ReposAdapter : PagingDataAdapter<Repo, RepoViewHolder>(REPOR_COMPARATOR) {

    companion object {
        private val REPOR_COMPARATOR = object : DiffUtil.ItemCallback<Repo>() {
            override fun areItemsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem.fullName == newItem.fullName
            }

            override fun areContentsTheSame(oldItem: Repo, newItem: Repo): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repoItem = getItem(position)
        repoItem?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        return RepoViewHolder.create(parent)
    }
}