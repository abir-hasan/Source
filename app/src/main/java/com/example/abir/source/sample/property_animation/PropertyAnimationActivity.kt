package com.example.abir.source.sample.property_animation

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.R
import kotlinx.android.synthetic.main.activity_property_animation.*


class PropertyAnimationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_property_animation)

        btn1.setOnClickListener {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        propertyAnimationWithValueAnimator()
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