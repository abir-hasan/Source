package com.example.abir.source.sample.dialog_on_full_screen

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.abir.source.R
import com.example.abir.source.databinding.DialogCustomScreenBinding
import com.example.abir.source.utils.logDebug

class CustomScreenDialog() : DialogFragment() {

    private var onCrossClick: (() -> Unit)? = null

    private var _binding: DialogCustomScreenBinding? = null

    private val binding: DialogCustomScreenBinding
        get() = _binding!!

    constructor(
        onCrossClick: (() -> Unit)? = null
    ) : this() {
        this.onCrossClick = onCrossClick
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = DialogCustomScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fullScreenWithAnimation()

        binding.buttonClose.setOnClickListener {
            dismiss()
            onCrossClick?.invoke()
        }

        binding.innerLayout.setOnClickListener { }

        // Dismiss the dialog on outside click
        binding.rootLayout.setOnClickListener {
            dismiss()
        }
    }

    private fun fullScreenWithAnimation() {
        "fullScreenWithAnimation() called".logDebug()
        try {
            val window = dialog?.window!!
            window.setBackgroundDrawableResource(android.R.color.transparent)
            val layoutParams = window.attributes
            layoutParams.windowAnimations = R.style.FullScreenDialogAnimation
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
            window.attributes = layoutParams
            // To make Status-bar transparent
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)// required
            window.statusBarColor = Color.TRANSPARENT
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}