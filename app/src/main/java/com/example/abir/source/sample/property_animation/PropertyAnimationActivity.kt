package com.example.abir.source.sample.property_animation

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.R
import kotlinx.android.synthetic.main.activity_property_animation.*


/**
 * They are used to alter property of objects (Views or non view objects).
 * We specify certain properties(like translateX, TextScaleX) of the objects to change.
 * Various characteristics of animation which can be manipulated are animation duration,
 * whether to reverse it and for how many times we want to repeat the animation etc
 */
class PropertyAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_animation)

        btn1.setOnClickListener {
            Toast.makeText(this, "Clicked 1", Toast.LENGTH_SHORT).show()
        }

        btn2.setOnClickListener {
            Toast.makeText(this, "Clicked 2", Toast.LENGTH_SHORT).show()
        }

        propertyAnimationWithValueAnimator()
        propertyAnimationWithObjectAnimator()
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
        objectAnimator.interpolator = BounceInterpolator()
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