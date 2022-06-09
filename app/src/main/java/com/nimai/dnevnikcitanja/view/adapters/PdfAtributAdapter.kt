package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Atribut
import com.nimai.dnevnikcitanja.model.entities.AtributKnjiga
import com.nimai.dnevnikcitanja.model.entities.Kategorija
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.NazivAtributKnjiga
import kotlinx.android.synthetic.main.kategorija_item.view.*
import kotlinx.android.synthetic.main.pdf_atribut_item.view.*
import kotlinx.android.synthetic.main.pdf_atributi.view.*

class PdfAtributAdapter(val atributiDataset: List<NazivAtributKnjiga>) : RecyclerView.Adapter<PdfAtributAdapter.PdfAtributViewHolder>() {

    inner class PdfAtributViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(atribut: NazivAtributKnjiga) {
            itemView.pdf_ime_atribut.text = atribut.nazivAtributa
            itemView.pdf_opis_atribut.text = atribut.atributKnjiga.vrijednostAtributa
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfAtributViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_atribut_item, parent, false)
        return PdfAtributViewHolder(itemView)
    }

    override fun getItemCount() = atributiDataset.size

    override fun onBindViewHolder(holder: PdfAtributViewHolder, position: Int) {
        holder.bind(atributiDataset[position])
    }
}