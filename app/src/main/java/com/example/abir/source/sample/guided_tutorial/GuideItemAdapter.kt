package com.example.abir.source.sample.guided_tutorial

import android.os.Handler
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abir.source.R
import com.example.abir.source.databinding.AdapterGuideItemBinding
import tourguide.tourguide.TourGuide

class GuideItemAdapter(
    private val layoutManager: LinearLayoutManager,
    private val recyclerViewGuide: RecyclerView
) :
    RecyclerView.Adapter<GuideItemAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "GuideItemAdapter"
    }

    private val handler: Handler by lazy {
        Handler()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = AdapterGuideItemBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
        holder.itemView.tag = position // Add tag to find view for
    }

    class ViewHolder(private val binding: AdapterGuideItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener { }
        }

        fun onBind(position: Int) {
            val drawableItem = if (position % 2 == 0) {
                ContextCompat.getDrawable(itemView.context, R.drawable.ic_smiley_face)
            } else {
                ContextCompat.getDrawable(itemView.context, R.drawable.ic_money)
            }
            binding.ivIcon.setImageDrawable(drawableItem)
            binding.tvSerial.text = "$position"
        }
    }

    fun setTourGuideOnItemWithPos(
        tourGuide: TourGuide,
        viewPosition: Int
    ) {
        val lastVisibleItemPosition = layoutManager.findLastCompletelyVisibleItemPosition()
        val firstVisibleItemPosition = layoutManager.findFirstCompletelyVisibleItemPosition()

        var shouldDelay = false
        if (viewPosition < firstVisibleItemPosition || viewPosition > lastVisibleItemPosition) {
            layoutManager.scrollToPositionWithOffset(viewPosition, 0)
            shouldDelay = true
        }

        //val viewFromPosition = layoutManager.getChildAt(viewPosition)
        //val viewFromPosition = layoutManager.findViewByPosition(viewPosition)
        if (shouldDelay) {
            tourGuide.cleanUp()
            handler.postDelayed({
                // Without the delay, null value is returned
                val viewFromPosition = getViewToHighlight(viewPosition)
                startGuiding(tourGuide, viewPosition, viewFromPosition)
            }, 300)
        } else {
            val viewFromPosition = getViewToHighlight(viewPosition)
            startGuiding(tourGuide, viewPosition, viewFromPosition)
        }
    }

    private fun getViewToHighlight(
        viewPosition: Int
    ) = recyclerViewGuide.findViewWithTag<View>(viewPosition)

    private fun startGuiding(
        tourGuide: TourGuide,
        viewPosition: Int,
        viewFromPosition: View?
    ) {
        Log.d(
            TAG, "startGuiding() called with: tourGuide = " +
                    "$tourGuide, viewPosition = " +
                    "$viewPosition, viewFromPosition = " +
                    "$viewFromPosition"
        )

        tourGuide.apply {
            cleanUp()
            toolTip {
                title { "Hey! $viewPosition" }
                description { "Yo........." }
                gravity { Gravity.BOTTOM }
            }
        }
        viewFromPosition?.let { tourGuide.playOn(it) }
    }
}