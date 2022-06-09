package com.nimai.dnevnikcitanja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Atribut
import com.nimai.dnevnikcitanja.model.entities.AtributKnjiga
import com.nimai.dnevnikcitanja.model.entities.Kategorija
import com.nimai.dnevnikcitanja.view.adapters.KategorijaAdapter
import com.nimai.dnevnikcitanja.viewmodel.KategorijaAtributViewModel
import kotlinx.android.synthetic.main.activity_pridjeli_kategoriju.*
import kotlinx.android.synthetic.main.dialog_dodaj_atribut.*
import kotlinx.android.synthetic.main.dialog_dodaj_kategoriju.*
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.selects.select

class PridjeliKategorijuActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var kategorijaAtributViewModel: KategorijaAtributViewModel

    private val dialogDodajKategorijuTag = "dialogDodajKategoriju"
    private val dialogObrisiKategorijuTag = "dialogObrisiKategoriju"
    private val dialogDodajAtributTag = "dialogDodajAtribut"

    private lateinit var kategorijaZaObrisati: Kategorija

    private var selectedKategorija: Kategorija? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pridjeli_kategoriju)

        title = "Odabir kategorije"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        kategorijaAtributViewModel = KategorijaAtributViewModel(application, intent.getIntExtra("idKnjige", 0))

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_pridjeli_kategoriju.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }

        kategorijaAtributViewModel.getNepridjeljeneKategorijeKnjige()?.observe(this, Observer { kategorije ->
            recyclerView.adapter = KategorijaAdapter(kategorije,
                { kategorija: Kategorija -> itemClicked(kategorija)},
                { kategorija: Kategorija -> itemLongClicked(kategorija) })
        })

        val dialog = UnosDialogFragment(R.layout.dialog_dodaj_kategoriju)

        val dodajKategorijuGumb: FloatingActionButton = findViewById(R.id.add_kategorija_gumb)
        dodajKategorijuGumb.setOnClickListener {
            dialog.show(supportFragmentManager, dialogDodajKategorijuTag)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if(dialog.tag == dialogDodajKategorijuTag) {
            val nazivKategorije = dialog.dialog?.dialog_naziv_kategorije?.text.toString().trim()
            if (nazivKategorije.isNotEmpty()) {
                kategorijaAtributViewModel.addKategorija(Kategorija(nazivKategorije))
                Toast.makeText(this, "Kategorija je spremljena", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite naziv kategorije", Toast.LENGTH_LONG).show()
            }
        } else if(dialog.tag == dialogObrisiKategorijuTag) {
            kategorijaAtributViewModel.obrisiKategoriju(kategorijaZaObrisati)
            Toast.makeText(this, "Kategorija je obrisana", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        } else if(dialog.tag == dialogDodajAtributTag) {
            val nazivAtributa = dialog.dialog?.dialog_naziv_atributa?.text.toString().trim()
            if (nazivAtributa.isNotEmpty()) {
                if(selectedKategorija != null) {
                    kategorijaAtributViewModel.addAtribut(Atribut(selectedKategorija!!.idKategorije, nazivAtributa))
                    Toast.makeText(this, "Atribut je spremljen", Toast.LENGTH_LONG).show()
                }
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite ime atributa", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.save_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun itemClicked(kategorija: Kategorija) {
        selectedKategorija = kategorija
    }

    private fun itemLongClicked(kategorija: Kategorija) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_kategoriju, "Obriši")
        kategorijaZaObrisati = kategorija
        dialog.show(supportFragmentManager, dialogObrisiKategorijuTag)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.spremi_option -> {
                if(selectedKategorija != null) {
                    pridjeliKategoriju()
                } else Toast.makeText(this, "Molim odaberite kategoriju!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun pridjeliKategoriju() {
        kategorijaAtributViewModel.getAtributByIdKategorije(selectedKategorija!!.idKategorije)?.observe(this, Observer { atributi ->
            if(atributi.isEmpty()) {
                val dialog = UnosDialogFragment(R.layout.dialog_dodaj_atribut1)
                dialog.show(supportFragmentManager, dialogDodajAtributTag)
            }
            else {
                kategorijaAtributViewModel.getAtributByIdKategorije(selectedKategorija!!.idKategorije)?.observe(this, Observer {
                    for (atribut in it) {
                        kategorijaAtributViewModel.addAtributKnjiga(AtributKnjiga(intent.getIntExtra("idKnjige", 0), atribut.idAtributa))
                    }
                    Toast.makeText(this, "Kategorija je pridjeljena", Toast.LENGTH_SHORT).show()
                    onBackPressed()
                })
            }
        })
    }
}
