package com.nimai.dnevnikcitanja.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.viewmodel.KnjigaViewModel
import kotlinx.android.synthetic.main.activity_knjiga_tekst.*

class KnjigaTekstActivity : AppCompatActivity() {


    private lateinit var knjigaViewModel: KnjigaViewModel
    private var type: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knjiga_tekst)
        title = intent.getStringExtra("naslov")

        knjigaViewModel = KnjigaViewModel(application, intent.getIntExtra("idKnjige", 0))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close)

        type = intent.getStringExtra("type")
        setEditText()

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
                spremiTekst(knjiga_variable_edit_text.text.toString().trim())
                onBackPressed()
                Toast.makeText(this, "$title je sprmeljen", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setEditText() {
        knjigaViewModel.knjiga?.observe(this, Observer { knjiga ->
            knjiga_variable_edit_text.setText(
                when(type) {
                    "kratkiSadrzaj" -> {
                        knjiga?.kratkiSadrzaj
                    }
                    "temaDjela" -> {
                        knjiga?.temaDjela
                    }
                    "prostorIVrijeme" -> {
                        knjiga?.prostorIVrijeme
                    }
                    "jezikIStil" -> {
                        knjiga?.jezikIStil
                    }
                    else -> ""
                }
            )
        })
    }

    private fun spremiTekst(tekst: String) {
        when(type) {
            "kratkiSadrzaj" -> {
                knjigaViewModel.updateKratkiSadrzajKnjige(tekst)
                System.out.println("kratki sadrzaj spremanje")
            }
            "temaDjela" -> {
                knjigaViewModel.updateTemaDjela(tekst)
            }
            "prostorIVrijeme" -> {
                knjigaViewModel.updateProstorIVrijemeKnjige(tekst)
            }
            "jezikIStil" -> {
                knjigaViewModel.updateJezikIStilKnjige(tekst)
            }
            else -> { throw IllegalStateException("Krivi tip activitya!!") }
        }
    }
}
