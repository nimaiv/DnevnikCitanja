package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.AtributKnjiga
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.NazivAtributKnjiga
import kotlinx.android.synthetic.main.atribut_item.view.*

class AtributKnjigaAdapter(val atributiKnjigeDataset: List<NazivAtributKnjiga>,
                           private val clickListener: (NazivAtributKnjiga) -> Unit,
                           private val longClickListener: (NazivAtributKnjiga) -> Unit) :
    RecyclerView.Adapter<AtributKnjigaAdapter.AtributKnjigaViewHolder>() {

    inner class AtributKnjigaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(atributKnjiga: NazivAtributKnjiga, clickListener: (NazivAtributKnjiga) -> Unit, longClickListener: (NazivAtributKnjiga) -> Unit) {
            itemView.text_view_ime_atributa.text = atributKnjiga.nazivAtributa
            itemView.text_view_opis_atributa.text = atributKnjiga.atributKnjiga.vrijednostAtributa
            itemView.setOnClickListener { clickListener(atributKnjiga) }
            itemView.setOnLongClickListener { longClickListener(atributKnjiga); true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AtributKnjigaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.atribut_item, parent, false)
        return AtributKnjigaViewHolder(itemView)
    }

    override fun getItemCount() = atributiKnjigeDataset.size

    override fun onBindViewHolder(holder: AtributKnjigaViewHolder, position: Int) {
        (holder).bind(atributiKnjigeDataset[position], clickListener, longClickListener)
    }
}