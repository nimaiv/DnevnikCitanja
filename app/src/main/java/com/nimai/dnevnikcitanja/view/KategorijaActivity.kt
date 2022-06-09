package com.nimai.dnevnikcitanja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.NazivAtributKnjiga
import com.nimai.dnevnikcitanja.view.adapters.AtributKnjigaAdapter
import com.nimai.dnevnikcitanja.viewmodel.KategorijaAtributViewModel
import kotlinx.android.synthetic.main.activity_kategorija.*
import kotlinx.android.synthetic.main.dialog_dodaj_atribut.*
import kotlinx.android.synthetic.main.dialog_edit_atribut.*

class KategorijaActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var kategorijaAtributViewModel: KategorijaAtributViewModel

    private val obrisiAtributDialogTag = "obrisiAtributDialog"
    private val urediAtributDialogTag = "urediAtributDialog"
    private val dodajAtributDialogTag = "dodajAtributDialog"

    private var idAtributaZaObrisati: Int = 0

    private var idKliknutogAtributa: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kategorija)


        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        title = intent.getStringExtra("nazivKategorije")

        kategorijaAtributViewModel = KategorijaAtributViewModel(application, intent.getIntExtra("idKnjige", 0))

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_atributi.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }

        kategorijaAtributViewModel.getAtributiKnjigeByIdKategorije(intent.getIntExtra("idKategorije", 0))
            ?.observe(this, Observer { atributiKnjige ->
                recyclerView.adapter = AtributKnjigaAdapter(atributiKnjige,
                    { atributKnjiga: NazivAtributKnjiga -> itemClicked(atributKnjiga) },
                    { atributKnjiga: NazivAtributKnjiga -> itemLongClicked(atributKnjiga) })
            })

        val dialog = UnosDialogFragment(R.layout.dialog_dodaj_atribut)


        val dodajAtributGumb: FloatingActionButton = findViewById(R.id.add_atribut_button)
        dodajAtributGumb.setOnClickListener {
            dialog.show(supportFragmentManager, dodajAtributDialogTag)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if(dialog.tag == dodajAtributDialogTag) {
            val nazivAtributa = dialog.dialog?.dialog_naziv_atributa?.text.toString().trim()
            val vrijednostAtributa = dialog.dialog?.dialog_vrijednost_atributa?.text.toString().trim()
            if (nazivAtributa.isNotEmpty()) {
                kategorijaAtributViewModel.dodajAtributIVrijednost(intent.getIntExtra("idKategorije", 0), nazivAtributa, vrijednostAtributa, this)
                Toast.makeText(this, "Atribut je spremljen", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite naziv atributa", Toast.LENGTH_LONG).show()
            }
        } else if(dialog.tag == obrisiAtributDialogTag) {
            kategorijaAtributViewModel.obrisiAtribut(idAtributaZaObrisati)
            Toast.makeText(this, "Atribut je obrisan", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        } else if(dialog.tag == urediAtributDialogTag) {
            val nazivAtributa = dialog.dialog?.dialog_uredi_naziv_atributa?.text.toString().trim()
            val vrijednostAtributa = dialog.dialog?.dialog_uredi_vrijednost_atributa?.text.toString().trim()
            if (nazivAtributa.isNotEmpty()) {
                kategorijaAtributViewModel.updateAtributNazivIVrijednost(idKliknutogAtributa, nazivAtributa, vrijednostAtributa)
                Toast.makeText(this, "Atribut je spremljen", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite naziv atributa", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun itemClicked(atributKnjiga: NazivAtributKnjiga) {
        val dialog = UnosDialogFragment(R.layout.dialog_edit_atribut)
        idKliknutogAtributa = atributKnjiga.atributKnjiga.idAtributa
        dialog.show(supportFragmentManager, urediAtributDialogTag)
        kategorijaAtributViewModel.getAtributNazivIVrijednost(idKliknutogAtributa)?.observe(this, Observer {
            dialog.dialog?.dialog_uredi_vrijednost_atributa?.setText(it.atributKnjiga.vrijednostAtributa)
            dialog.dialog?.dialog_uredi_naziv_atributa?.setText(it.nazivAtributa)
        })

    }

    private fun itemLongClicked(atributKnjiga: NazivAtributKnjiga) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_atribut, "Obriši")
        dialog.show(supportFragmentManager, obrisiAtributDialogTag)
        idAtributaZaObrisati = atributKnjiga.atributKnjiga.idAtributa
    }
}
