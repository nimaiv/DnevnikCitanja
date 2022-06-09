package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Citat
import kotlinx.android.synthetic.main.citat_list_item.view.*

class PdfCitatAdapter(val citatiDataset: List<Citat>) : RecyclerView.Adapter<PdfCitatAdapter.PdfCitatViewHolder>() {

    inner class PdfCitatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(citat: Citat) {
            itemView.pdf_citat_tekst.text = citat.citat
            itemView.pdf_citat_opis.text = citat.opisCitata
            itemView.pdf_citat_broj_stranice.text = citat.brStranice.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PdfCitatViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.citat_list_item, parent, false)
        return PdfCitatViewHolder(itemView)
    }

    override fun getItemCount() = citatiDataset.size

    override fun onBindViewHolder(holder: PdfCitatViewHolder, position: Int) {
        (holder).bind(citatiDataset[position])
    }
}