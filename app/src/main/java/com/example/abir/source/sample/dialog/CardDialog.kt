package com.example.abir.source.sample.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.abir.source.R
import kotlinx.android.synthetic.main.dialog_card.*


class CardDialog() : DialogFragment() {

    private lateinit var message: String

    private var onCrossClick: (() -> Unit)? = null

    private var onButtonClick: (() -> Unit)? = null

    private var iconId: Int? = null

    private var buttonText: String? = null

    constructor(
        titleText: String,
        buttonText: String?,
        cardIcon: Int?,
        onCrossClick: (() -> Unit)?,
        onButtonClick: (() -> Unit)?
    ) : this() {
        this.iconId = cardIcon
        this.message = titleText
        this.buttonText = buttonText
        this.onCrossClick = onCrossClick
        this.onButtonClick = onButtonClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_card, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        try {
            val window = dialog!!.window!!
            window.setBackgroundDrawableResource(android.R.color.transparent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvMessage.text = message

        buttonText?.let {
            tvActionButton.text = buttonText
        } ?: run {
            tvActionButton.visibility = View.GONE
        }

        tvActionButton.setOnClickListener {
            dismiss()
            onButtonClick?.invoke()
        }

        ivCross.setOnClickListener {
            dismiss()
            onCrossClick?.invoke()
        }

        iconId?.let { ivIcon.setImageResource(it) }
    }
}