package com.nimai.dnevnikcitanja.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.viewmodel.HelperViewModel
import kotlinx.android.synthetic.main.activity_statistika.*

class StatistikaActivity : AppCompatActivity() {

    private lateinit var statistikaViewModel: HelperViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistika)

        title = "Statistika"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        statistikaViewModel = HelperViewModel(application)

        setTextViews()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun setTextViews() {
        statistikaViewModel.getKnjigaCount()?.observe(this, Observer { count ->
            statistika_br_knjiga.text = count.toString()
        })
        statistikaViewModel.getPisacCount()?.observe(this, Observer { count ->
            statistika_br_pisca.text = count.toString()
        })
        statistikaViewModel.getKnjizevnaVrstaCount()?.observe(this, Observer { count ->
            statistika_br_knj_vrsta.text = count.toString()
        })
        statistikaViewModel.getLikCount()?.observe(this, Observer { count ->
            statistika_br_likova.text = count.toString()
        })
        statistikaViewModel.getCitatCount()?.observe(this, Observer { count ->
            statistika_br_citata.text = count.toString()
        })
        statistikaViewModel.getKategorijaCount()?.observe(this, Observer { count ->
            statistika_br_kategorija.text = count.toString()
        })
    }

}