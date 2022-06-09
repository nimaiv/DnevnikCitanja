package com.nimai.dnevnikcitanja.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Lik
import com.nimai.dnevnikcitanja.view.adapters.LikAdapter
import com.nimai.dnevnikcitanja.viewmodel.LikViewModel
import kotlinx.android.synthetic.main.activity_lik.*
import kotlinx.android.synthetic.main.dialog_dodaj_lika.*

class LikActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var likViewModel: LikViewModel

    private val dodajLikaDialogTag = "dodajCitatDialog"
    private val obrisiLikaDialogTag = "obrisiCitatDialog"

    private lateinit var likZaObrisati: Lik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lik)

        title = "Likovi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        likViewModel = LikViewModel(application, intent.getIntExtra("idKnjige", 0))

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_likovi.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }

        likViewModel.getLikoviKnjige()?.observe(this, Observer { likovi ->
            recyclerView.adapter = LikAdapter(likovi,
                { lik: Lik -> itemClicked(lik) },
                { lik: Lik -> itemLongClicked(lik) })
        })


        val dialog = UnosDialogFragment(R.layout.dialog_dodaj_lika)


        val dodajLikaGumb: FloatingActionButton = findViewById(R.id.add_lik_button)
        dodajLikaGumb.setOnClickListener {
            dialog.show(supportFragmentManager, dodajLikaDialogTag)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if(dialog.tag == dodajLikaDialogTag) {
            val imeLika = dialog.dialog?.dialog_ime_lika?.text.toString().trim()
            if (imeLika.isNotEmpty()) {
                likViewModel.dodajLika(imeLika)
                Toast.makeText(this, "Lik je spremljen", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite ime lika", Toast.LENGTH_LONG).show()
            }
        } else if(dialog.tag == obrisiLikaDialogTag) {
            likViewModel.obrisiLika(likZaObrisati)
            Toast.makeText(this, "Lik je obrisan", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun itemClicked(lik: Lik) {
        val intent = Intent(this, LikEditActivity::class.java)
        intent.putExtra("idLika", lik.idLika)
        startActivity(intent)
    }

    private fun itemLongClicked(lik: Lik) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_lika, "Obriši")
        likZaObrisati = lik
        dialog.show(supportFragmentManager, obrisiLikaDialogTag)
    }
}
