package com.nimai.dnevnikcitanja.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Knjiga
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.KnjigaPisac
import kotlinx.android.synthetic.main.knjiga_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class KnjigaAdapter(val knjigeDataset: MutableList<KnjigaPisac>, private val clickListener: (Knjiga) -> Unit, private val longClickListener: (Knjiga) -> Unit) :
    RecyclerView.Adapter<KnjigaAdapter.KnjigeViewHolder>() {


    private val knjigeAll: List<KnjigaPisac> = ArrayList<KnjigaPisac>(knjigeDataset)

    inner class KnjigeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(knjigaPisac: KnjigaPisac, clickListener: (Knjiga) -> Unit, longClickListener: (Knjiga) -> Unit) {
            itemView.text_view_naslov.text = knjigaPisac.knjiga.naslovKnjige
            itemView.text_view_pisac.text = knjigaPisac.imePisca
            itemView.setOnClickListener { clickListener(knjigaPisac.knjiga) }
            itemView.setOnLongClickListener { longClickListener(knjigaPisac.knjiga);  true }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KnjigeViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.knjiga_item, parent, false)
        return KnjigeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: KnjigeViewHolder, position: Int) {
        (holder).bind(knjigeDataset[position], clickListener, longClickListener)
    }

    override fun getItemCount() = knjigeDataset.size

}