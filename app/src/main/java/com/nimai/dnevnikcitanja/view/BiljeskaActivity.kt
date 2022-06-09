package com.nimai.dnevnikcitanja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.viewmodel.HelperViewModel
import kotlinx.android.synthetic.main.activity_biljeska.*

class BiljeskaActivity : AppCompatActivity() {

    private lateinit var helperViewModel: HelperViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biljeska)

        helperViewModel = HelperViewModel(application)

        title = "Bilješka o piscu"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)



        helperViewModel.getPisac(intent.getIntExtra("idPisca", 0))?.observe(this, Observer {
            biljeska_edit_tekst.setText(it.biljeska)
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
        return when(item.itemId) {
            R.id.spremi_option -> {
                spremiBiljesku(biljeska_edit_tekst.text.toString().trim())
                onBackPressed()
                Toast.makeText(this, "Bilješka je spremljena", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun spremiBiljesku(biljeska: String) {
        helperViewModel.updateBljeskaPisca(biljeska, intent.getIntExtra("idPisca", 0))
    }
}
