package com.example.abir.source.sample.property_animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.databinding.ActivityPropertyAnimationBinding
import com.example.abir.source.utils.logDebug


/**
 * They are used to alter property of objects (Views or non view objects).
 * We specify certain properties(like translateX, TextScaleX) of the objects to change.
 * Various characteristics of animation which can be manipulated are animation duration,
 * whether to reverse it and for how many times we want to repeat the animation etc
 *
 * Properties to be animated:-
 * translationX, translationY
 * rotation, rotationX, rotationY
 * scaleX, scaleY
 * pivotX, pivotY
 * alpha
 * x, y [These are simple utility properties to describe the final location of the View
 * in its container, as a sum of the left and top values and translationX and translationY values.]
 */
class PropertyAnimationActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PropertyAnimation"
        private const val SEARCH_ANIMATION_DURATION = 1000L
    }

    private lateinit var binding: ActivityPropertyAnimationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPropertyAnimationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvHello.setOnClickListener { multiplePropertyAnimationsOnSingleView() }

        binding.btn1.setOnClickListener { multiplePropertyAnimationsOnShowSearch() }
        binding.btn2.setOnClickListener { multiplePropertyAnimationsOnHideSearch() }

        binding.ivSearch.setOnClickListener { multiplePropertyAnimationsOnShowSearch() }
        binding.ivCancel.setOnClickListener { multiplePropertyAnimationsOnHideSearch() }
        binding.etSearch.isClickable = false
        binding.etSearch.isFocusable = false
        binding.ivCancel.isClickable = false
        binding.ivCancel.isFocusable = false

        propertyAnimationWithValueAnimator()
        propertyAnimationWithObjectAnimator()
    }

    /**
     * Same animation as [propertyAnimationWithValueAnimator] just using the ObjectAnimator Subclass
     * Also on a different view with a few different parameters
     */
    private fun propertyAnimationWithObjectAnimator() {
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(
            binding.btn2, "translationY", 0f, -500f
        )
        objectAnimator.duration = 2000
        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator.start()
    }

    private fun propertyAnimationWithValueAnimator() {
        val valueAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 500f)
        valueAnimator.interpolator =
            AccelerateDecelerateInterpolator() // increase the speed first and then decrease

        valueAnimator.duration = 2000
        valueAnimator.addUpdateListener { animation ->
            binding.btn1.translationY = animation.animatedValue.toString().toFloat()
        }
        valueAnimator.start()
    }

    /**
     * Perform Multiple Animations on a single View
     * Note: Animation always takes the Views First Setup point as (0,0)
     */
    private fun multiplePropertyAnimationsOnSingleView() {
        val displayMetrics = resources.displayMetrics

        val positionOnScreen = IntArray(2)
        binding.tvHello.getLocationOnScreen(positionOnScreen)
        val currentXPositionOnScreen = positionOnScreen[0]

        val bringToCenterPosition = 0f
        val pixelsToGoLeft = (-currentXPositionOnScreen).toFloat()
        val pixelsToGoRight =
            (displayMetrics.widthPixels - currentXPositionOnScreen - binding.tvHello.width).toFloat()

        val goLeftAnimation =
            ObjectAnimator.ofFloat(binding.tvHello, "translationX", pixelsToGoLeft)
        goLeftAnimation.duration = 3000

        val scaleUpAnimation = ObjectAnimator.ofFloat(binding.tvHello, "scaleX", 1.5f)
        scaleUpAnimation.duration = 2500

        val scaleBackAnimation = ObjectAnimator.ofFloat(binding.tvHello, "scaleX", 1f)
        scaleBackAnimation.duration = 1000

        val goRightAnimation =
            ObjectAnimator.ofFloat(binding.tvHello, "translationX", pixelsToGoRight)
        goRightAnimation.startDelay = 500
        goRightAnimation.duration = 3000

        val rightRotateAnimation = ObjectAnimator.ofFloat(binding.tvHello, "rotation", 360f)
        rightRotateAnimation.startDelay = 500
        rightRotateAnimation.repeatCount = 5
        goRightAnimation.duration = 3000

        val bringToCenterAnimation =
            ObjectAnimator.ofFloat(binding.tvHello, "translationX", bringToCenterPosition)
        bringToCenterAnimation.startDelay = 500
        bringToCenterAnimation.duration = 2000

        // Go Right and Rotate
        val animatorSet2 = AnimatorSet()
        animatorSet2.play(goRightAnimation).with(rightRotateAnimation)

        // Go Left and Scale
        val animatorSet1 = AnimatorSet()
        animatorSet1.playTogether(scaleUpAnimation, goLeftAnimation)


        // Main Animation Timeline
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(
            animatorSet1,
            animatorSet2,
            bringToCenterAnimation,
            scaleBackAnimation
        )
        animatorSet.start()
    }

    /**
     * Show search option when search buttons is clicked with follwing animations
     * Search icon will go from right to start of the screen
     * Edit text will fade in
     * Cancel icon will fade in
     */
    private fun multiplePropertyAnimationsOnShowSearch() {
        val displayMetrics = resources.displayMetrics
        val pixelsToGoLeft = (-(displayMetrics.widthPixels - binding.ivSearch.width)).toFloat()

        val searchIconLeftAnimation =
            ObjectAnimator.ofFloat(binding.ivSearch, "translationX", pixelsToGoLeft)
        searchIconLeftAnimation.duration = SEARCH_ANIMATION_DURATION

        val editTextFadeInAnimation =
            ObjectAnimator.ofFloat(binding.etSearch, "alpha", 0f, 1f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val cancelIconFadeInAnimation =
            ObjectAnimator.ofFloat(binding.ivCancel, "alpha", 0f, 1f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val animatorSet = AnimatorSet()
        animatorSet.play(searchIconLeftAnimation).with(editTextFadeInAnimation)
        animatorSet.play(searchIconLeftAnimation).with(cancelIconFadeInAnimation)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                "onAnimationEnd()".logDebug(TAG)
                binding.etSearch.isClickable = true
                binding.etSearch.isFocusableInTouchMode = true
                binding.ivCancel.isClickable = true
                binding.ivCancel.isFocusable = true
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animatorSet.start()

    }

    /**
     * Reverse animation of [multiplePropertyAnimationsOnShowSearch]
     */
    private fun multiplePropertyAnimationsOnHideSearch() {
        val searchIconLeftAnimation =
            ObjectAnimator.ofFloat(binding.ivSearch, "translationX", 0f)
        searchIconLeftAnimation.duration = SEARCH_ANIMATION_DURATION

        val editTextFadeInAnimation =
            ObjectAnimator.ofFloat(binding.etSearch, "alpha", 1f, 0f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val cancelIconFadeInAnimation =
            ObjectAnimator.ofFloat(binding.ivCancel, "alpha", 1f, 0f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val animatorSet = AnimatorSet()
        animatorSet.play(searchIconLeftAnimation).with(editTextFadeInAnimation)
        animatorSet.play(searchIconLeftAnimation).with(cancelIconFadeInAnimation)

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                binding.etSearch.isClickable = false
                binding.etSearch.isFocusable = false
                binding.ivCancel.isClickable = false
                binding.ivCancel.isFocusable = false
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animatorSet.start()
    }
}