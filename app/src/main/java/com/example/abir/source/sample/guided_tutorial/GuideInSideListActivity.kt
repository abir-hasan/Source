package com.example.abir.source.sample.guided_tutorial

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abir.source.R
import kotlinx.android.synthetic.main.activity_guide_in_side_list.*

class GuideInSideListActivity : AppCompatActivity() {

    private val mLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private val mAdapter : GuideItemAdapter by lazy {
        GuideItemAdapter(mLayoutManager)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide_in_side_list)
        setUpListView()
    }

    private fun setUpListView() {
        with(rvGuide) {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }
}