package com.example.abir.source.sample.paging_lib_sample_v1.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.abir.source.sample.paging_lib_sample_v1.data.GithubRepository
import com.example.abir.source.utils.logDebug
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.flow.*

class SearchRepositoriesViewModel(
    private val repository: GithubRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    companion object{
        private const val TAG = "SearchRepositoriesViewM"
    }

    val stateFlow: StateFlow<PagingData<Repo>>

    init {
        val initialQuery = "Android"
        stateFlow = getRepos(initialQuery).stateIn(
            viewModelScope, SharingStarted.Lazily,
            PagingData.empty()
        )
    }

    fun getRepos(query: String): Flow<PagingData<Repo>> {
        "getRepos() called with: query = $query".logDebug(TAG)
        return repository.getSearchResultStream(query).cachedIn(viewModelScope)
    }

}