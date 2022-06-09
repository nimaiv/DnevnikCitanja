package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Pisac
import kotlinx.android.synthetic.main.pisac_item.view.*

class PisacAdapter(val pisciDataset: List<Pisac>, val idKnjige: Int, private val clickListener: (Pisac) -> Unit, private val longClickListener: (Pisac) -> Unit) : RecyclerView.Adapter<PisacAdapter.PisacViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    inner class PisacViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(pisac: Pisac, idKnjige: Int, clickListener: (Pisac) -> Unit, longClickListener: (Pisac) -> Unit) {
            itemView.pisac_adapter_item.text = pisac.imePisca
            itemView.isSelected = selectedPosition == adapterPosition
            itemView.setOnClickListener {
                if(adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener

                notifyItemChanged(selectedPosition)
                selectedPosition = layoutPosition
                notifyItemChanged(selectedPosition)

                clickListener(pisac) }
            itemView.setOnLongClickListener { longClickListener(pisac);  true }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PisacViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pisac_item, parent, false)
        return PisacViewHolder(itemView)
    }

    override fun getItemCount() = pisciDataset.size

    override fun onBindViewHolder(holder: PisacViewHolder, position: Int) {
        holder.bind(pisciDataset[position], idKnjige, clickListener, longClickListener)
    }
}