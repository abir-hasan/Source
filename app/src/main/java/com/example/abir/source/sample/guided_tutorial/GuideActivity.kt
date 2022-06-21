package com.example.abir.source.sample.guided_tutorial

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.databinding.ActivityGuideBinding
import tourguide.tourguide.Pointer
import tourguide.tourguide.TourGuide

class GuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGuideBinding

    private lateinit var enterAnimation: AlphaAnimation

    private lateinit var exitAnimation: AlphaAnimation

    private lateinit var tourGuide: TourGuide

    private lateinit var tourGuideSkipAll: TourGuide


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpAnimation()
        initializeTourGuide()
    }

    /**
     * Initialize TourGuide without playOn()
     */
    private fun initializeTourGuide() {
        tourGuide = TourGuide.create(this) {
            pointer { Pointer() }

            toolTip {
                title { "Hey!" }
                description { "I'm the top fellow" }
                gravity { Gravity.RIGHT }

            }
            overlay {
                setEnterAnimation(enterAnimation)
                setExitAnimation(exitAnimation)
            }
        }
        setUpButtonClicks()
        anotherTourGuideWithToolTipToSkipAll()
        tourGuide.playOn(binding.btnOne)
    }

    private fun anotherTourGuideWithToolTipToSkipAll() {
        tourGuideSkipAll = TourGuide.create(this) {
            toolTip {
                title { "Skip All" }
                mOnClickListener = View.OnClickListener {
                    tourGuideSkipAll.cleanUp()
                    tourGuide.cleanUp()
                }
                gravity { Gravity.TOP or Gravity.CENTER }
            }
        }.playOn(binding.btnThree)
    }

    private fun setUpButtonClicks() {
        binding.btnOne.setOnClickListener {
            tourGuide.apply {
                cleanUp()
                toolTip {
                    title { "Hey there!" }
                    description { "Just the middle man" }
                    gravity { Gravity.BOTTOM or Gravity.LEFT }
                }
            }.playOn(binding.btnTwo)
        }

        /* setup 2nd button, when clicked, cleanUp() and re-run TourGuide on button3 */
        binding.btnTwo.setOnClickListener {
            tourGuide.apply {
                cleanUp()
                toolTip {
                    title { "Hey..." }
                    description { "It's time to say goodbye" }
                    gravity { Gravity.TOP or Gravity.RIGHT }
                }
            }.playOn(binding.btnThree)
        }

        /* setup 3rd button, when clicked, run cleanUp() */
        binding.btnThree.setOnClickListener {
            tourGuide.cleanUp()
            Intent(this, GuideInSideListActivity::class.java).apply {
                startActivity(this)
            }
        }
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