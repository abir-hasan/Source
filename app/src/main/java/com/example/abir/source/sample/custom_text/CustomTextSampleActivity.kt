package com.example.abir.source.sample.custom_text

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.abir.source.R
import kotlinx.android.synthetic.main.activity_custom_text_sample.*

class CustomTextSampleActivity : AppCompatActivity() {

    companion object {
        const val text_part_1 = "Hello, Welcome!"
        const val text_part_2 = "Want to know more?"
        const val text_part_3 = "[Link]"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_text_sample)
        showSpannableTextVariation()
    }

    /**
     * Simple + Bold + Underlined with Color
     */
    private fun showSpannableTextVariation() {
        val builder = SpannableStringBuilder()

        val spannableString1 = SpannableString(text_part_1)
        spannableString1.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.lemon)),
            0,
            spannableString1.length,
            0
        )
        builder.append(spannableString1) // Append
        builder.append(" ") // Append

        val spannableString2 = SpannableString(text_part_2)
        spannableString2.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            spannableString2.length,
            0
        )
        builder.append(spannableString2) // Append
        builder.append(" ") // Append

        val spannableString3 = SpannableString(text_part_3)
        spannableString3.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.purple500)),
            0,
            spannableString3.length,
            0
        )

        spannableString3.setSpan(
            UnderlineSpan(),
            0,
            spannableString3.length,
            0
        )

        builder.append(spannableString3) // Append

        // Setting the text
        tvSpannableText.setText(builder, TextView.BufferType.SPANNABLE)
    }
}