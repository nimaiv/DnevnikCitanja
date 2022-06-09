package com.nimai.dnevnikcitanja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.viewmodel.LikViewModel
import kotlinx.android.synthetic.main.activity_lik_edit.*

class LikEditActivity : AppCompatActivity() {

    private lateinit var likViewModel: LikViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lik_edit)

        title = "Uredi lika"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        likViewModel = LikViewModel(application)

        likViewModel.getLik(intent.getIntExtra("idLika", 0))
            ?.observe(this, Observer { lik ->
                ime_lika_edit.setText(lik.imeLika)
                lik_opis_edit.setText(lik.opisLika)
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
                spremiLika()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun spremiLika() {
        val novoImeLika = ime_lika_edit.text.toString().trim()
        val noviOpisLika = lik_opis_edit.text.toString().trim()
        if (novoImeLika.isNotEmpty()) {
            likViewModel.updateLik(intent.getIntExtra("idLika", 0), novoImeLika, noviOpisLika)
            onBackPressed()
            Toast.makeText(this, "Lik je spremljen", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Molim unesite valjane podatke", Toast.LENGTH_SHORT).show()
        }
    }
}
