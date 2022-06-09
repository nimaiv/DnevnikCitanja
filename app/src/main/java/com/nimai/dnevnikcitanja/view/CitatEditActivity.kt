package com.nimai.dnevnikcitanja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.Observer
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.viewmodel.CitatViewModel
import kotlinx.android.synthetic.main.activity_citat_edit.*

class CitatEditActivity : AppCompatActivity() {

    private lateinit var citatViewModel: CitatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_citat_edit)

        title = "Uredi citat"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        citatViewModel = CitatViewModel(application)

        citatViewModel.getCitat(intent.getIntExtra("idCitata", 0))
            ?.observe(this, Observer { citat ->
                citat_edit.setText(citat.citat)
                citat_broj_stranice_edit.setText(citat.brStranice.toString())
                citat_opis_edit.setText(citat.opisCitata)
            })
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.spremi_option -> {
                spremiCitat()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun spremiCitat() {
        val noviCitat = citat_edit.text.toString().trim()
        val noviBrojStranice = citat_broj_stranice_edit.text.toString().trim()
        val noviOpisCitata = citat_opis_edit.text.toString().trim()
        if (noviCitat.isNotEmpty() && noviBrojStranice.isNotEmpty() && noviBrojStranice.isDigitsOnly()) {
            citatViewModel.updateCitat(intent.getIntExtra("idCitata", 0), noviCitat, noviBrojStranice.toInt(), noviOpisCitata)

            onBackPressed()
            Toast.makeText(this, "Citat je spremljen", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Molim unesite valjane podatke", Toast.LENGTH_SHORT).show()
        }
    }
}
