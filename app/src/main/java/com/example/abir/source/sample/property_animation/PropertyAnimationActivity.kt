package com.example.abir.source.sample.property_animation

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.R
import com.example.abir.source.utils.logDebug
import kotlinx.android.synthetic.main.activity_property_animation.*


/**
 * They are used to alter property of objects (Views or non view objects).
 * We specify certain properties(like translateX, TextScaleX) of the objects to change.
 * Various characteristics of animation which can be manipulated are animation duration,
 * whether to reverse it and for how many times we want to repeat the animation etc
 */
class PropertyAnimationActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "PropertyAnimation"
        private const val SEARCH_ANIMATION_DURATION = 1000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_animation)

        btn1.setOnClickListener { multiplePropertyAnimationsOnShowSearch() }
        btn2.setOnClickListener { multiplePropertyAnimationsOnHideSearch() }

        ivSearch.setOnClickListener { multiplePropertyAnimationsOnShowSearch() }
        ivCancel.setOnClickListener { multiplePropertyAnimationsOnHideSearch() }
        etSearch.isClickable = false
        etSearch.isFocusable = false
        ivCancel.isClickable = false
        ivCancel.isFocusable = false

        propertyAnimationWithValueAnimator()
        propertyAnimationWithObjectAnimator()
    }

    /**
     * Show search option when search buttons is clicked with follwing animations
     * Search icon will go from right to start of the screen
     * Edit text will fade in
     * Cancel icon will fade in
     */
    private fun multiplePropertyAnimationsOnShowSearch() {
        val displayMetrics = resources.displayMetrics
        val pixelsToGoLeft = (-(displayMetrics.widthPixels - ivSearch.width)).toFloat()

        val searchIconLeftAnimation =
            ObjectAnimator.ofFloat(ivSearch, "translationX", pixelsToGoLeft)
        searchIconLeftAnimation.duration = SEARCH_ANIMATION_DURATION

        val editTextFadeInAnimation =
            ObjectAnimator.ofFloat(etSearch, "alpha", 0f, 1f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val cancelIconFadeInAnimation =
            ObjectAnimator.ofFloat(ivCancel, "alpha", 0f, 1f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val animatorSet = AnimatorSet()
        animatorSet.play(searchIconLeftAnimation).with(editTextFadeInAnimation)
        animatorSet.play(searchIconLeftAnimation).with(cancelIconFadeInAnimation)
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                "onAnimationEnd()".logDebug(TAG)
                etSearch.isClickable = true
                etSearch.isFocusableInTouchMode = true
                ivCancel.isClickable = true
                ivCancel.isFocusable = true
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
            ObjectAnimator.ofFloat(ivSearch, "translationX", 0f)
        searchIconLeftAnimation.duration = SEARCH_ANIMATION_DURATION

        val editTextFadeInAnimation =
            ObjectAnimator.ofFloat(etSearch, "alpha", 1f, 0f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val cancelIconFadeInAnimation =
            ObjectAnimator.ofFloat(ivCancel, "alpha", 1f, 0f)
        editTextFadeInAnimation.duration = SEARCH_ANIMATION_DURATION

        val animatorSet = AnimatorSet()
        animatorSet.play(searchIconLeftAnimation).with(editTextFadeInAnimation)
        animatorSet.play(searchIconLeftAnimation).with(cancelIconFadeInAnimation)

        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {

            }

            override fun onAnimationEnd(animation: Animator?) {
                etSearch.isClickable = false
                etSearch.isFocusable = false
                ivCancel.isClickable = false
                ivCancel.isFocusable = false
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }

        })
        animatorSet.start()
    }

    /**
     * Same animation as [propertyAnimationWithValueAnimator] just using the ObjectAnimator Subclass
     * Also on a different view with a few different parameters
     */
    private fun propertyAnimationWithObjectAnimator() {
        val objectAnimator: ObjectAnimator = ObjectAnimator.ofFloat(
            btn2, "translationY", 0f, -500f
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
            btn1.translationY = animation.animatedValue.toString().toFloat()
        }
        valueAnimator.start()
    }
}