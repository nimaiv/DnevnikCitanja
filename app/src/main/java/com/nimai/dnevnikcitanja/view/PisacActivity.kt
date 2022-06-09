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
import com.nimai.dnevnikcitanja.model.entities.Pisac
import com.nimai.dnevnikcitanja.view.adapters.PisacAdapter
import com.nimai.dnevnikcitanja.viewmodel.KnjigaViewModel
import kotlinx.android.synthetic.main.activity_pisac.*
import kotlinx.android.synthetic.main.dialog_dodaj_pisca.*
import kotlinx.android.synthetic.main.dialog_edit_pisca.*

class PisacActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var knjigaViewModel: KnjigaViewModel

    private val dialogDodajPiscaTag = "dialogDodajPisca"
    private val dialogObrisiPiscaTag = "dialogObrisiPisca"
    private val dialogUrediPiscaTag = "dialogUrediPisca"

    private lateinit var pisacZaObrisati: Pisac

    var selectedPisac: Pisac? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pisac)

        title = "Odabir pisca"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        knjigaViewModel = KnjigaViewModel(application, intent.getIntExtra("idKnjige", 0))

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_pisci.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }

        knjigaViewModel.getPisci()?.observe(this, Observer { pisci ->
            recyclerView.adapter = PisacAdapter(pisci,
                knjigaViewModel.idKnjige,
                { pisac: Pisac -> itemClicked(pisac)},
                { pisac: Pisac -> itemLongClicked(pisac) })
        })

        val dialog = UnosDialogFragment(R.layout.dialog_dodaj_pisca)

        val dodajPiscaGumb: FloatingActionButton = findViewById(R.id.add_pisac_gumb)
        dodajPiscaGumb.setOnClickListener {
            dialog.show(supportFragmentManager, dialogDodajPiscaTag)
        }

    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if(dialog.tag == dialogDodajPiscaTag) {
            val imePisca = dialog.dialog?.dialog_ime_pisca?.text.toString().trim()
            if (imePisca.isNotEmpty()) {
                knjigaViewModel.addPisac(Pisac(imePisca))
                Toast.makeText(this, "Pisac je spremljen", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite ime pisca", Toast.LENGTH_LONG).show()
            }
        } else if(dialog.tag == dialogObrisiPiscaTag) {
            knjigaViewModel.obrisiPisca(pisacZaObrisati)
            Toast.makeText(this, "${pisacZaObrisati.imePisca} je obrisan/a", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        } else if(dialog.tag == dialogUrediPiscaTag) {
            val imePisca = dialog.dialog?.dialog_edit_ime_pisca?.text.toString().trim()
            if (imePisca.isNotEmpty()) {
                selectedPisac?.imePisca = imePisca
                selectedPisac?.let { knjigaViewModel.updatePisac(it) }
                Toast.makeText(this, "Pisac je spremljen", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite ime pisca", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.add_edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.spremi_menu_button -> {
                if(selectedPisac != null) {
                    pridjeliPisca()
                    onBackPressed()
                } else Toast.makeText(this, "Molim odaberite pisca!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.uredi_menu_button -> {
                urediPisca()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun itemClicked(pisac: Pisac) {
        selectedPisac = pisac
    }

    private fun itemLongClicked(pisac: Pisac) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_pisca, "Obriši")
        pisacZaObrisati = pisac
        dialog.show(supportFragmentManager, dialogObrisiPiscaTag)
    }

    private fun pridjeliPisca() {
        selectedPisac?.idPisca?.let { knjigaViewModel.updatePisacKnjige(it) }
    }

    private fun urediPisca() {
        val dialog = UnosDialogFragment(R.layout.dialog_edit_pisca)
        if(selectedPisac != null)
            dialog.show(supportFragmentManager, dialogUrediPiscaTag)
        else Toast.makeText(this, "Molim odaberite pisca!", Toast.LENGTH_SHORT).show()
    }


}
