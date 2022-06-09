package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Citat
import kotlinx.android.synthetic.main.citat_item.view.*

class CitatAdapter(val citatiDataset: List<Citat>, private val clickListener: (Citat) -> Unit, private val longClickListener: (Citat) -> Unit) :
    RecyclerView.Adapter<CitatAdapter.CitatViewHolder>() {

    inner class CitatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(citat: Citat, clickListener: (Citat) -> Unit, longClickListener: (Citat) -> Unit) {
            itemView.text_view_citat_tekst.text = citat.citat
            itemView.text_view_citat_stranica.text = citat.brStranice.toString()
            itemView.text_view_citat_opis.text = citat.opisCitata
            itemView.setOnClickListener { clickListener(citat) }
            itemView.setOnLongClickListener { longClickListener(citat); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.citat_item, parent, false)
        return CitatViewHolder(itemView)
    }

    override fun getItemCount() = citatiDataset.size

    override fun onBindViewHolder(holder: CitatViewHolder, position: Int) {
        (holder).bind(citatiDataset[position], clickListener, longClickListener)
    }
}