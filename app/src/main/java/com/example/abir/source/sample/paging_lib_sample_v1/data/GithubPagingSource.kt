package com.example.abir.source.sample.paging_lib_sample_v1.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.abir.source.sample.paging_lib_sample_v1.api.GithubService
import com.example.abir.source.sample.paging_lib_sample_v1.api.IN_QUALIFIER
import com.example.abir.source.utils.logInfo
import com.example.abir.source.utils.logVerbose
import com.example.abir.source.utils.logWarn
import com.example.android.codelabs.paging.model.Repo
import retrofit2.HttpException
import java.io.IOException

// GitHub page API is 1 based: https://developer.github.com/v3/#pagination
private const val GITHUB_STARTING_PAGE_INDEX = 1

class GithubPagingSource(
    private val service: GithubService,
    private val query: String
) : PagingSource<Int, Repo>() {

    companion object {
        private const val TAG = "GithubPagingSource"
    }

    override fun getRefreshKey(state: PagingState<Int, Repo>): Int {
        "getRefreshKey() called with: state = $state".logVerbose(TAG)
        return GITHUB_STARTING_PAGE_INDEX
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repo> {
        val position = params.key ?: GITHUB_STARTING_PAGE_INDEX
        val apiQuery = query + IN_QUALIFIER
        val loadSize = params.loadSize
        "load() apiQuery: $apiQuery pageNumber: $position loadSize: $loadSize".logWarn(TAG)
        return try {
            val response = service.searchRepos(apiQuery, position, loadSize)
            val repos: List<Repo> = response.items
            val nextKey = if (repos.isEmpty()) {
                null // Null when no more item left to load
            } else {
                position + 1
            }
            "load() Success! nextKey: $nextKey listSize: ${repos.size}".logInfo(TAG)
            LoadResult.Page(
                data = repos,
                nextKey = nextKey,
                prevKey = null // Only paging forward.
            )
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        }
    }

}