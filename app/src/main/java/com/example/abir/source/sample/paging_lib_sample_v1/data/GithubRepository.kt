package com.example.abir.source.sample.paging_lib_sample_v1.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.abir.source.sample.paging_lib_sample_v1.api.GithubService
import com.example.abir.source.utils.logDebug
import com.example.android.codelabs.paging.model.Repo
import kotlinx.coroutines.flow.Flow

class GithubRepository(private val service: GithubService) {

    companion object {
        private const val TAG = "GithubRepository"
        const val NETWORK_PAGE_SIZE = 10
    }

    fun getSearchResultStream(query: String): Flow<PagingData<Repo>> {
        "getSearchResultStream() called with: query = $query".logDebug(TAG)
        return Pager(
            config = PagingConfig(
                initialLoadSize = NETWORK_PAGE_SIZE,
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
            ),
            pagingSourceFactory = { GithubPagingSource(service, query) }
        ).flow
    }
}