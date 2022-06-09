package com.nimai.dnevnikcitanja.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.KnjizevnaVrsta
import com.nimai.dnevnikcitanja.view.adapters.KnjizevnaVrstaAdapter
import com.nimai.dnevnikcitanja.viewmodel.KnjigaViewModel
import kotlinx.android.synthetic.main.activity_knjizevna_vrsta.*
import kotlinx.android.synthetic.main.dialog_dodaj_knjizevnu_vrstu.*
import kotlinx.android.synthetic.main.dialog_edit_knjizevna_vrsta.*

class KnjizevnaVrstaActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var knjigaViewModel: KnjigaViewModel

    private val dialogDodajKVTag = "dialogDodajKnjizevnuVrstu"
    private val dialogObrisiKVTag = "dialogObrisiKnjizevnuVrstu"
    private val dialogUrediKVTag = "dialogUrediKnjizevnuVrstu"

    private lateinit var knjizevnaVrstaZaObrisati: KnjizevnaVrsta

    var selectedKnjizevnaVrsta: KnjizevnaVrsta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knjizevna_vrsta)

        title = "Književna vrsta"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        knjigaViewModel = KnjigaViewModel(application, intent.getIntExtra("idKnjige", 0))

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_knjizevne_vrste.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }

        knjigaViewModel.getKnjizevneVrste()?.observe(this, Observer { knjizevneVrste ->
            recyclerView.adapter = KnjizevnaVrstaAdapter(knjizevneVrste,
                knjigaViewModel.idKnjige,
                { knjizevnaVrsta: KnjizevnaVrsta -> itemClicked(knjizevnaVrsta)},
                { knjizevnaVrsta: KnjizevnaVrsta -> itemLongClicked(knjizevnaVrsta) })
        })

        val dialog = UnosDialogFragment(R.layout.dialog_dodaj_knjizevnu_vrstu)

        val dodajKVGumb: FloatingActionButton = findViewById(R.id.add_knjizevna_vrsta_gumb)
        dodajKVGumb.setOnClickListener {
            dialog.show(supportFragmentManager, dialogDodajKVTag)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if(dialog.tag == dialogDodajKVTag) {
            val nazivKnjizevneVrste = dialog.dialog?.dialog_knjizevna_vrsta?.text.toString().trim()
            if (nazivKnjizevneVrste.isNotEmpty()) {
                knjigaViewModel.addKnjizevnaVrsta(KnjizevnaVrsta(nazivKnjizevneVrste))
                Toast.makeText(this, "Književna vrsta je spremljena", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite književnu vrstu", Toast.LENGTH_LONG).show()
            }
        } else if(dialog.tag == dialogObrisiKVTag) {
            knjigaViewModel.obrisiKnjizevnuVrstu(knjizevnaVrstaZaObrisati)
            Toast.makeText(this, "${knjizevnaVrstaZaObrisati.knjizevnaVrsta} je obrisana", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        } else if(dialog.tag == dialogUrediKVTag) {
            val nazivKnjizevneVrste = dialog.dialog?.dialog_edit_knjizevna_vrsta?.text.toString().trim()
            if (nazivKnjizevneVrste.isNotEmpty()) {
                selectedKnjizevnaVrsta?.knjizevnaVrsta = nazivKnjizevneVrste
                selectedKnjizevnaVrsta?.let { knjigaViewModel.updateKnjizevnaVrsta(it) }
                Toast.makeText(this, "Književna vrsta je spremljena", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite književnu vrstu", Toast.LENGTH_LONG).show()
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
                if(selectedKnjizevnaVrsta != null) {
                    pridjeliKnjizevnuVrstu()
                    onBackPressed()
                } else Toast.makeText(this, "Molim odaberite književnu vrstu!", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.uredi_menu_button -> {
                urediKnjizevnuVrstu()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun itemClicked(knjizevnaVrsta: KnjizevnaVrsta) {
        selectedKnjizevnaVrsta = knjizevnaVrsta
    }

    private fun itemLongClicked(knjizevnaVrsta: KnjizevnaVrsta) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_knjizevnu_vrstu, "Obriši")
        knjizevnaVrstaZaObrisati = knjizevnaVrsta
        dialog.show(supportFragmentManager, dialogObrisiKVTag)
    }

    private fun pridjeliKnjizevnuVrstu() {
        selectedKnjizevnaVrsta?.idKnjizevneVrste?.let { knjigaViewModel.updateKnjizevnaVrstaKnjige(it) }
    }

    private fun urediKnjizevnuVrstu() {
        val dialog = UnosDialogFragment(R.layout.dialog_edit_knjizevna_vrsta)
        if(selectedKnjizevnaVrsta != null)
            dialog.show(supportFragmentManager, dialogUrediKVTag)
        else Toast.makeText(this, "Molim odaberite književnu vrstu!", Toast.LENGTH_SHORT).show()
    }
}