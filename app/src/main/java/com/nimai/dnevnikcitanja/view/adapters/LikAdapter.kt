package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Lik
import kotlinx.android.synthetic.main.lik_item.view.*

class LikAdapter(val likoviDataset: List<Lik>, private val clickListener: (Lik) -> Unit, private val longClickListener: (Lik) -> Unit) :
    RecyclerView.Adapter<LikAdapter.LikViewHolder>() {

    inner class LikViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lik: Lik, clickListener: (Lik) -> Unit, longClickListener: (Lik) -> Unit) {
            itemView.text_view_lik.text = lik.imeLika
            itemView.text_view_lik_opis.text = lik.opisLika
            itemView.setOnClickListener { clickListener(lik) }
            itemView.setOnLongClickListener { longClickListener(lik); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.lik_item, parent, false)
        return LikViewHolder(itemView)
    }

    override fun getItemCount() = likoviDataset.size

    override fun onBindViewHolder(holder: LikViewHolder, position: Int) {
        (holder).bind(likoviDataset[position], clickListener, longClickListener)
    }
}