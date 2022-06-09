package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.KnjizevnaVrsta
import kotlinx.android.synthetic.main.knjizevna_vrsta_item.view.*
import kotlinx.android.synthetic.main.pisac_item.view.*

class KnjizevnaVrstaAdapter(val knjizevneVrsteDataset: List<KnjizevnaVrsta>, val idKnjige: Int,
                                   private val clickListener: (KnjizevnaVrsta) -> Unit,
                                   private val longClickListener: (KnjizevnaVrsta) -> Unit) :
    RecyclerView.Adapter<KnjizevnaVrstaAdapter.KnjizevnaVrstaViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class KnjizevnaVrstaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(knjizevnaVrsta: KnjizevnaVrsta, idKnjige: Int, clickListener: (KnjizevnaVrsta) -> Unit, longClickListener: (KnjizevnaVrsta) -> Unit) {
            itemView.knjizevna_vrsta_adapter_item.text = knjizevnaVrsta.knjizevnaVrsta
            itemView.isSelected = selectedPosition == adapterPosition
            itemView.setOnClickListener {
                if(adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                notifyItemChanged(selectedPosition)
                selectedPosition = layoutPosition
                notifyItemChanged(selectedPosition)

                clickListener(knjizevnaVrsta) }
            itemView.setOnLongClickListener { longClickListener(knjizevnaVrsta);  true }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnjizevnaVrstaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.knjizevna_vrsta_item, parent, false)
        return KnjizevnaVrstaViewHolder(itemView)
    }

    override fun getItemCount() = knjizevneVrsteDataset.size

    override fun onBindViewHolder(holder: KnjizevnaVrstaViewHolder, position: Int) {
        holder.bind(knjizevneVrsteDataset[position], idKnjige, clickListener, longClickListener)
    }
}