package com.example.abir.source.sample.guided_tutorial

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.abir.source.R
import com.example.abir.source.databinding.ActivityGuideInSideListBinding
import tourguide.tourguide.Overlay
import tourguide.tourguide.Pointer
import tourguide.tourguide.TourGuide

class GuideInSideListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuideInSideListBinding

    private lateinit var enterAnimation: AlphaAnimation

    private lateinit var exitAnimation: AlphaAnimation

    private lateinit var tourGuide: TourGuide

    private lateinit var tourGuideNext: TourGuide

    private var clickCounter = 0

    private val mLayoutManager: LinearLayoutManager by lazy {
        LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private val mAdapter: GuideItemAdapter by lazy {
        GuideItemAdapter(mLayoutManager, binding.rvGuide)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideInSideListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAnimation()
        initializeTourGuide()
        setUpListView()
    }

    private fun setUpListView() {
        with(binding.rvGuide) {
            layoutManager = mLayoutManager
            adapter = mAdapter
        }
    }

    /**
     * Initialize TourGuide without playOn()
     */
    private fun initializeTourGuide() {
        tourGuide = TourGuide.create(this) {
            pointer { Pointer() }

            /*toolTip {
                title { "Hey!" }
                description { "I'm the top fellow" }
                gravity { Gravity.RIGHT }

            }*/


            overlay {
                mStyle = Overlay.Style.RECTANGLE
                setEnterAnimation(enterAnimation)
                setExitAnimation(exitAnimation)
            }
        }
        anotherTourGuideWithToolTipToSkipAll()
        //tourGuide.playOn(btnOne)
    }

    private fun anotherTourGuideWithToolTipToSkipAll() {
        tourGuideNext = TourGuide.create(this) {
            toolTip {
                title { "Next" }
                mBackgroundColor =
                    ContextCompat.getColor(this@GuideInSideListActivity, R.color.DarkSalmon)
                mOnClickListener = View.OnClickListener { handleNextGuideClick() }
                gravity { Gravity.BOTTOM or Gravity.CENTER }
            }
        }.playOn(binding.rvGuide)
    }

    private fun handleNextGuideClick() {
        when (clickCounter) {
            0 -> {
                mAdapter.setTourGuideOnItemWithPos(tourGuide, 0)
            }
            1 -> {
                mAdapter.setTourGuideOnItemWithPos(tourGuide, 2)
            }
            2 -> {
                mAdapter.setTourGuideOnItemWithPos(tourGuide, 9)
            }
            else -> {
                tourGuide.cleanUp()
                tourGuideNext.cleanUp()
            }
        }
        clickCounter++
    }

    /**
     * setup enter and exit animation
     */
    private fun setUpAnimation() {
        enterAnimation = AlphaAnimation(0f, 1f)
            .apply {
                duration = 600
                fillAfter = true
            }

        exitAnimation = AlphaAnimation(1f, 0f)
            .apply {
                duration = 600
                fillAfter = true
            }
    }
}