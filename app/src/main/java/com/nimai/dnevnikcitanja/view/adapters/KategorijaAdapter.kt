package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Kategorija
import kotlinx.android.synthetic.main.kategorija_item.view.*

class KategorijaAdapter(val kategorijeDataset: List<Kategorija>, private val clickListener: (Kategorija) -> Unit,
                        private val longClickListener: (Kategorija) -> Unit, private val nonSelectable: Boolean = false) :
                        RecyclerView.Adapter<KategorijaAdapter.KategorijaViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class KategorijaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(kategorija: Kategorija, clickListener: (Kategorija) -> Unit, longClickListener: (Kategorija) -> Unit, nonSelectable: Boolean) {
            itemView.pridjeli_kategoriju_adapter_item.text = kategorija.nazivKategorije
            itemView.isSelected = selectedPosition == adapterPosition
            itemView.setOnClickListener {
                if(adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                notifyItemChanged(selectedPosition)
                selectedPosition = layoutPosition
                notifyItemChanged(selectedPosition)

                clickListener(kategorija) }
            itemView.setOnLongClickListener { longClickListener(kategorija);  true }
            if(nonSelectable) {
                itemView.setBackgroundResource(R.color.cardViewBackground)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KategorijaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.kategorija_item, parent, false)
        return KategorijaViewHolder(itemView)
    }

    override fun getItemCount() = kategorijeDataset.size

    override fun onBindViewHolder(holder: KategorijaViewHolder, position: Int) {
        holder.bind(kategorijeDataset[position], clickListener, longClickListener, nonSelectable)
    }
}