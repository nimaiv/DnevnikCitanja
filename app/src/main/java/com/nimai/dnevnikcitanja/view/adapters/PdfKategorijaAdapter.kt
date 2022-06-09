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
import kotlinx.android.synthetic.main.pdf_atributi.view.*

class PdfKategorijaAdapter(val kategorijeDataset: List<Kategorija>, val atributiKategorije: HashMap<Int, List<NazivAtributKnjiga>>) :
    RecyclerView.Adapter<PdfKategorijaAdapter.PdfKategorijaViewHolder>() {

    inner class PdfKategorijaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val recyclerView: RecyclerView = itemView.pdf_view_atributi
        fun bind(kategorija: Kategorija) {
            itemView.pdf_ime_kategorija.text = kategorija.nazivKategorije
            recyclerView.apply {
                layoutManager = LinearLayoutManager(recyclerView.context)
                adapter = PdfAtributAdapter(atributiKategorije[kategorija.idKategorije]!!)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfKategorijaViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pdf_atributi, parent, false)
        return PdfKategorijaViewHolder(itemView)
    }

    override fun getItemCount() = kategorijeDataset.size

    override fun onBindViewHolder(holder: PdfKategorijaViewHolder, position: Int) {
        holder.bind(kategorijeDataset[position])

    }
}