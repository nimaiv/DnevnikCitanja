package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Lik
import kotlinx.android.synthetic.main.lik_item.view.*
import kotlinx.android.synthetic.main.likovi_list_item.view.*

class PdfLikAdapter(val likoviDataset: List<Lik>) : RecyclerView.Adapter<PdfLikAdapter.PdfLikViewHolder>() {

    inner class PdfLikViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(lik: Lik) {
            itemView.pdf_ime_lik.text = lik.imeLika
            itemView.pdf_opis_lika.text = lik.opisLika
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfLikViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.likovi_list_item, parent, false)
        return PdfLikViewHolder(itemView)
    }

    override fun getItemCount() = likoviDataset.size

    override fun onBindViewHolder(holder: PdfLikViewHolder, position: Int) {
        (holder).bind(likoviDataset[position])
    }
}