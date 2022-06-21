package com.example.abir.source.sample.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.abir.source.databinding.DialogCardBinding


class CardDialog() : DialogFragment() {

    private var _binding: DialogCardBinding? = null

    private val binding: DialogCardBinding
        get() = _binding!!

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
    ): View {
        _binding = DialogCardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

        binding.tvMessage.text = message

        buttonText?.let {
            binding.tvActionButton.text = buttonText
        } ?: run {
            binding.tvActionButton.visibility = View.GONE
        }

        binding.tvActionButton.setOnClickListener {
            dismiss()
            onButtonClick?.invoke()
        }

        binding.ivCross.setOnClickListener {
            dismiss()
            onCrossClick?.invoke()
        }

        iconId?.let { binding.ivIcon.setImageResource(it) }
    }
}