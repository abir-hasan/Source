package com.example.abir.source.sample.paging_lib_sample_v1.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.abir.source.BaseActivity
import com.example.abir.source.databinding.ActivitySearchRepositoriesBinding
import com.example.abir.source.sample.paging_lib_sample_v1.Injection
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchRepositoriesActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchRepositoriesBinding

    private lateinit var viewModel: SearchRepositoriesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchRepositoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this, Injection.provideViewModelFactory(this)
        ).get(SearchRepositoriesViewModel::class.java)

        val mAdapter = ReposAdapter()
        with(binding.list) {
            adapter = mAdapter
        }

        lifecycleScope.launch {
            viewModel.stateFlow.collectLatest { pagingData ->
                mAdapter.submitData(pagingData)
            }
        }

        // TODO - Add Search Mechanism
    }
}