package com.nimai.dnevnikcitanja.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Citat
import com.nimai.dnevnikcitanja.view.adapters.CitatAdapter
import com.nimai.dnevnikcitanja.viewmodel.CitatViewModel
import kotlinx.android.synthetic.main.activity_citat.*
import kotlinx.android.synthetic.main.dialog_dodaj_citat.*

class CitatActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var citatViewModel: CitatViewModel

    private val dodajCitatDialogTag = "dodajCitatDialog"
    private val obrisiCitatDialogTag = "obrisiCitatDialog"

    private lateinit var citatZaObrisati: Citat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citat)

        title = "Citati"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        citatViewModel = CitatViewModel(application, intent.getIntExtra("idKnjige", 0))

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_citati.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }

        citatViewModel.getCitatiKnjige()?.observe(this, Observer { citati ->
            recyclerView.adapter = CitatAdapter(citati,
                { citat: Citat -> itemClicked(citat) },
                { citat: Citat -> itemLongClicked(citat) })
        })
        val dialog = UnosDialogFragment(R.layout.dialog_dodaj_citat)

        val dodajCitatGumb: FloatingActionButton = findViewById(R.id.add_citat_button)
        dodajCitatGumb.setOnClickListener {
            dialog.show(supportFragmentManager, dodajCitatDialogTag)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if(dialog.tag == dodajCitatDialogTag) {
            val citat = dialog.dialog?.dialog_citat?.text.toString().trim()
            val brojStranice = dialog.dialog?.dialog_citat_broj_stranice?.text.toString()
            if (citat.isNotEmpty() && brojStranice.isNotEmpty() && brojStranice.isDigitsOnly()) {
                citatViewModel.addCitat(citat, brojStranice.toInt())
                Toast.makeText(this, "Citat je spremljen", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite citat i valjani broj stranice", Toast.LENGTH_LONG).show()
            }
        } else if(dialog.tag == obrisiCitatDialogTag) {
            citatViewModel.obrisiCitat(citatZaObrisati)
            Toast.makeText(this, "Citat je obrisan", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    private fun itemClicked(citat: Citat) {
        val intent = Intent(this, CitatEditActivity::class.java)
        intent.putExtra("idCitata", citat.idCitata)
        startActivity(intent)
    }
    private fun itemLongClicked(citat: Citat) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_citat, "Obriši")
        citatZaObrisati = citat
        dialog.show(supportFragmentManager, obrisiCitatDialogTag)
    }
}
