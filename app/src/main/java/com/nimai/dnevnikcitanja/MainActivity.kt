package com.nimai.dnevnikcitanja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.model.entities.Knjiga
import com.nimai.dnevnikcitanja.view.adapters.KnjigaAdapter
import com.nimai.dnevnikcitanja.viewmodel.PocetnaViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.KnjigaPisac
import com.nimai.dnevnikcitanja.view.UnosDialogFragment
import com.nimai.dnevnikcitanja.view.KnjigaActivity
import com.nimai.dnevnikcitanja.view.StatistikaActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_dodaj_knjigu.*


class MainActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener {

    private var prikaz: Boolean = false
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var pocetnaViewModel: PocetnaViewModel


    private val dodajKnjiguDialogTag = "dodajKnjiguDialog"
    private val obrisiKnjiguDialogTag = "obrisiKnjiguDialog"


    private lateinit var knjigaZaObrisati: Knjiga

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pocetnaViewModel = PocetnaViewModel(application)

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_knjige.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }



        pocetnaViewModel.getKnjigeIPisce()?.observe(this, Observer { knjige ->


                recyclerView.adapter = KnjigaAdapter(
                    knjige as MutableList<KnjigaPisac>,
                    { knjiga: Knjiga -> itemClicked(knjiga) },
                    { knjiga: Knjiga -> itemLongClicked(knjiga) })

        })



        val dialog = UnosDialogFragment(R.layout.dialog_dodaj_knjigu)


        val dodajKnjiguGumb: FloatingActionButton = findViewById(R.id.add_knjiga_button)
        dodajKnjiguGumb.setOnClickListener {
            dialog.show(supportFragmentManager, dodajKnjiguDialogTag)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.apply {
            isSubmitButtonEnabled = true
            queryHint = "Traži"
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        pocetnaViewModel.getKnjigeIPisce(newText)?.observe(this@MainActivity, Observer { knjige ->
                            recyclerView.adapter = KnjigaAdapter(
                                knjige as MutableList<KnjigaPisac>,
                                { knjiga: Knjiga -> itemClicked(knjiga) },
                                { knjiga: Knjiga -> itemLongClicked(knjiga) })
                        })
                    }
                    return false
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
            })

         }


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.statistika_menu_item -> {
                val intentStatistika = Intent(this, StatistikaActivity::class.java)
                startActivity(intentStatistika)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if(dialog.tag == dodajKnjiguDialogTag) {
            val naslov = dialog.dialog?.dialog_naslov_knjige?.text.toString().trim()
            if (naslov.isNotEmpty()) {
                pocetnaViewModel.addKnjiga(naslov)
                Toast.makeText(this, "Knjiga je spremljena", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite naslov knjige", Toast.LENGTH_LONG).show()
            }
        } else if(dialog.tag == obrisiKnjiguDialogTag) {
            pocetnaViewModel.obrisiKnjigu(knjigaZaObrisati)
            Toast.makeText(this, "Knjiga je obrisana", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }


    private fun itemClicked(knjiga: Knjiga) {
        val intent = Intent(this, KnjigaActivity::class.java)
        intent.putExtra("idKnjige", knjiga.idKnjige)
        startActivity(intent)
    }

    private fun itemLongClicked(knjiga: Knjiga) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_knjigu, "Obriši")
        knjigaZaObrisati = knjiga
        dialog.show(supportFragmentManager, obrisiKnjiguDialogTag)
    }

}
