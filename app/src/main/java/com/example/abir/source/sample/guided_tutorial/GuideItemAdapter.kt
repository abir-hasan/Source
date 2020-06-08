package com.example.abir.source.sample.guided_tutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.abir.source.R
import kotlinx.android.synthetic.main.adapter_guide_item.view.*

class GuideItemAdapter(layoutManager: LinearLayoutManager) : RecyclerView.Adapter<GuideItemAdapter.ViewHolder>() {

    companion object {
        private const val TAG = "GuideItemAdapter"
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_guide_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 15
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(position)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {  }
        }

        fun onBind(position: Int) {
            val drawableItem = if (position % 2 == 0) {
                ContextCompat.getDrawable(itemView.context, R.drawable.ic_smiley_face)
            } else {
                ContextCompat.getDrawable(itemView.context, R.drawable.ic_money)
            }
            itemView.ivIcon.setImageDrawable(drawableItem)
            itemView.tvSerial.text = "$position"
        }
    }
}