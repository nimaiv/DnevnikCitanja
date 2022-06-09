package com.nimai.dnevnikcitanja.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintAttributes.Resolution
import android.print.pdf.PrintedPdfDocument
import android.view.*
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nimai.dnevnikcitanja.R
import com.nimai.dnevnikcitanja.model.entities.Citat
import com.nimai.dnevnikcitanja.model.entities.Kategorija
import com.nimai.dnevnikcitanja.model.entities.Pisac
import com.nimai.dnevnikcitanja.model.entities.joinedEntities.NazivAtributKnjiga
import com.nimai.dnevnikcitanja.view.adapters.KategorijaAdapter
import com.nimai.dnevnikcitanja.view.adapters.PdfCitatAdapter
import com.nimai.dnevnikcitanja.view.adapters.PdfKategorijaAdapter
import com.nimai.dnevnikcitanja.view.adapters.PdfLikAdapter
import com.nimai.dnevnikcitanja.viewmodel.KnjigaViewModel
import kotlinx.android.synthetic.main.activity_knjiga.*
import kotlinx.android.synthetic.main.dialog_edit_broj_stranica.*
import kotlinx.android.synthetic.main.dialog_edit_naslov.*
import kotlinx.android.synthetic.main.pdf_citati.*
import kotlinx.android.synthetic.main.pdf_view.view.*
import kotlinx.android.synthetic.main.pdf_citati.view.*
import kotlinx.android.synthetic.main.pdf_kategorije.*
import kotlinx.android.synthetic.main.pdf_likovi.view.*
import kotlinx.android.synthetic.main.pdf_kategorije.view.*
import kotlinx.android.synthetic.main.pdf_kategorije.view.pdf_view_kategorije
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.OutputStream


class KnjigaActivity : AppCompatActivity(), UnosDialogFragment.NoticeDialogListener {

    private val createFile = 1

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var knjigaViewModel: KnjigaViewModel

    private lateinit var intentPisac: Intent
    private lateinit var intentBiljeska: Intent
    private lateinit var intentKnjizevnaVrsta: Intent
    private lateinit var intentTekst: Intent
    private lateinit var intentCitat: Intent
    private lateinit var intentLik: Intent
    private lateinit var intentPridjeliKategoriju: Intent
    private lateinit var intentKategorija: Intent

    private lateinit var trenutniPisac: Pisac

    private val dialogObrisiKategorijuTag = "dialogObrisiKategoriju"
    private val dialogUrediBrojStranicaTag = "urediBrojStranica"
    private val dialogUrediNaslovTag = "urediNaslov"

    private lateinit var kategorijaZaObrisati: Kategorija

    private lateinit var naslovKnjige: String
    private lateinit var pdfBytes: ByteArray

    private val pageWidth = 595//595
    private val pageHeight = 842//842

    private lateinit var pdfViewMain: View
    private lateinit var pdfViewCitati: View
    private lateinit var pdfViewLikovi: View
    private lateinit var pdfViewKategorije: View
    private lateinit var pdfLayoutMain: LinearLayout
    private lateinit var pdfLayoutLikovi: LinearLayout
    private lateinit var pdfLayoutCitati: LinearLayout
    private lateinit var pdfLayoutKategorije: LinearLayout

    private lateinit var citatRecyclerView: RecyclerView
    private lateinit var likRecyclerView: RecyclerView
    private lateinit var kategorijaRecyclerView: RecyclerView
    private lateinit var atributRecyclerView: RecyclerView

    private lateinit var citatViewManager: RecyclerView.LayoutManager
    private lateinit var likViewManager: RecyclerView.LayoutManager
    private lateinit var kategorijaViewManager: RecyclerView.LayoutManager




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knjiga)
        title = ""
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back)

        knjigaViewModel = KnjigaViewModel(application, intent.getIntExtra("idKnjige", 0))

        viewManager = LinearLayoutManager(this)
        recyclerView = recycler_view_kategorije.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = null
        }


        pdfLayoutMain = LinearLayout(applicationContext)
        pdfLayoutCitati = LinearLayout(applicationContext)
        pdfLayoutLikovi = LinearLayout(applicationContext)
        pdfLayoutKategorije = LinearLayout(applicationContext)
        val inflater = LayoutInflater.from(applicationContext)
        pdfViewMain = View(applicationContext)
        pdfViewMain = inflater.inflate(R.layout.pdf_view, pdfLayoutMain, true)
        pdfViewCitati = inflater.inflate(R.layout.pdf_citati, pdfLayoutCitati, true)
        pdfViewLikovi = inflater.inflate(R.layout.pdf_likovi, pdfLayoutLikovi, true)
        pdfViewKategorije = inflater.inflate(R.layout.pdf_kategorije, pdfLayoutKategorije, true)

        fillCitati()
        fillLikovi()
        fillKategorije()



        setIntents()
        setTextViews()
        setOnClickListeners()
    }

    private fun setIntents() {
        intentPisac = Intent(this, PisacActivity::class.java)
        intentPisac.putExtra("idKnjige", intent.getIntExtra("idKnjige", 0))

        intentKnjizevnaVrsta = Intent(this, KnjizevnaVrstaActivity::class.java)
        intentKnjizevnaVrsta.putExtra("idKnjige", intent.getIntExtra("idKnjige", 0))

        intentTekst = Intent(this, KnjigaTekstActivity::class.java)
        intentTekst.putExtra("idKnjige", intent.getIntExtra("idKnjige", 0))

        intentCitat = Intent(this, CitatActivity::class.java)
        intentCitat.putExtra("idKnjige", intent.getIntExtra("idKnjige", 0))

        intentLik = Intent(this, LikActivity::class.java)
        intentLik.putExtra("idKnjige", intent.getIntExtra("idKnjige", 0))

        intentPridjeliKategoriju = Intent(this, PridjeliKategorijuActivity::class.java)
        intentPridjeliKategoriju.putExtra("idKnjige", intent.getIntExtra("idKnjige", 0))

        intentKategorija = Intent(this, KategorijaActivity::class.java)
        intentKategorija.putExtra("idKnjige", intent.getIntExtra("idKnjige", 0))

        intentBiljeska = Intent(this, BiljeskaActivity::class.java)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val menuInflater: MenuInflater = menuInflater
        menuInflater.inflate(R.menu.export_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.izvezi_option -> {
                createPdf();
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setTextViews() {
        knjigaViewModel.knjiga?.observe(this, Observer { knjiga ->
            naslov_knjige.text = knjiga.naslovKnjige
            pdfLayoutMain.pdf_naslov.text = knjiga.naslovKnjige
            naslovKnjige = knjiga.naslovKnjige
            val pisac = knjigaViewModel.getPisac(knjiga)

            if (pisac != null) {
                pisac_biljeska.visibility = View.VISIBLE
                biljeska_linija_ispod.visibility = View.VISIBLE
            } else {
                pisac_biljeska.visibility = View.GONE
                biljeska_linija_ispod.visibility = View.GONE
            }

            pisac?.observe(this, Observer {
                knjigaViewModel.idTrenutnogPisca = it?.idPisca
                pisac_knjige.text = it?.imePisca
                pdfLayoutMain.pdf_naslov.text = pdfLayoutMain.pdf_naslov.text.toString() + ", " + it?.imePisca
                if (it?.biljeska != null) {
                    biljeska_pisac.text = it.biljeska
                    pdfLayoutMain.pdf_pisac_biljeska.text = it.biljeska
                }
                else {
                    biljeska_pisac.text = "-"
                }
            })
            if (knjiga.brojStranica != null) {
                broj_stranica.text = knjiga.brojStranica.toString()
                pdfLayoutMain.pdf_broj_stranica.text = knjiga.brojStranica.toString()
            } else {
                pdfLayoutMain.pdf_broj_stranica.text = "-"
            }
            val knjizevnaVrsta = knjigaViewModel.getKnjizevnaVrsta(knjiga)
            knjizevnaVrsta?.observe(this, Observer {
                knjizevna_vrsta.text = it?.knjizevnaVrsta
                pdfLayoutMain.pdf_knjizevna_vrsta.text = it?.knjizevnaVrsta
            })
            if (knjiga.kratkiSadrzaj != null) {
                kratki_sadrzaj.text = knjiga.kratkiSadrzaj
                pdfLayoutMain.pdf_kratki_sadrzaj.text = knjiga.kratkiSadrzaj
            }
            if (knjiga.temaDjela != null) {
                tema_djela.text = knjiga.temaDjela
                pdfLayoutMain.pdf_tema_djela.text = knjiga.temaDjela
            }
            if (knjiga.prostorIVrijeme != null) {
                prostor_i_vrijeme.text = knjiga.prostorIVrijeme
                pdfLayoutMain.pdf_prostor_i_vrijeme.text = knjiga.prostorIVrijeme
            }
            if (knjiga.jezikIStil != null) {
                jezik_i_stil.text = knjiga.jezikIStil
                pdfLayoutMain.pdf_jezik_i_stil.text = knjiga.jezikIStil
            }

            val kategorije = knjigaViewModel.getKategorijeKnjige()

            kategorije?.observe(this, Observer { k ->
                recyclerView.adapter = KategorijaAdapter(
                    k,
                    { kategorija: Kategorija -> itemClicked(kategorija) },
                    { kategorija: Kategorija -> itemLongClicked(kategorija) }, true
                )
            })

        })
    }

    private fun setOnClickListeners() {
        val dialogUrediNaslov = UnosDialogFragment(R.layout.dialog_edit_naslov)
        val dialogUrediBrojStranica = UnosDialogFragment(R.layout.dialog_edit_broj_stranica)

        knjiga_naslov.setOnClickListener {
            dialogUrediNaslov.show(supportFragmentManager, "urediNaslov")
        }
        knjiga_pisac.setOnClickListener {
            startActivity(intentPisac)
        }
        knjiga_broj_stranica.setOnClickListener {
            dialogUrediBrojStranica.show(supportFragmentManager, "urediBrojStranica")
        }
        pisac_biljeska.setOnClickListener {
            intentBiljeska.putExtra("idPisca", knjigaViewModel.idTrenutnogPisca)
            startActivity(intentBiljeska)
        }
        knjiga_knjizevna_vrsta.setOnClickListener {
            startActivity(intentKnjizevnaVrsta)
        }
        knjiga_kratki_sadrzaj.setOnClickListener {
            intentTekst.putExtra("type", "kratkiSadrzaj")
            intentTekst.putExtra("naslov", "Kratki sadržaj")
            startActivity(intentTekst)
        }
        knjiga_tema.setOnClickListener {
            intentTekst.putExtra("type", "temaDjela")
            intentTekst.putExtra("naslov", "Tema djela")
            startActivity(intentTekst)
        }
        knjiga_prostor_i_vrijeme.setOnClickListener {
            intentTekst.putExtra("type", "prostorIVrijeme")
            intentTekst.putExtra("naslov", "Prostor i vrijeme")
            startActivity(intentTekst)
        }
        knjiga_jezik_i_stil.setOnClickListener {
            intentTekst.putExtra("type", "jezikIStil")
            intentTekst.putExtra("naslov", "Jezik i stil")
            startActivity(intentTekst)
        }
        knjiga_citati.setOnClickListener {
            startActivity(intentCitat)
        }
        knjiga_likovi.setOnClickListener {
            startActivity(intentLik)
        }
        gumb_pridjeli_kategoriju.setOnClickListener {
            startActivity(intentPridjeliKategoriju)
        }
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, layout: Int) {
        if (dialog.tag.equals(dialogUrediNaslovTag)) {
            val naslov = dialog.dialog?.dialog_edit_naslov_knjige?.text.toString().trim()
            if (naslov.isNotEmpty()) {
                val knjiga = knjigaViewModel.knjiga?.value
                knjiga?.naslovKnjige = naslov
                if (knjiga != null) {
                    knjigaViewModel.updateKnjiga(knjiga)
                    Toast.makeText(this, "Naslov je spremljen", Toast.LENGTH_SHORT).show()
                } else Toast.makeText(this, "Greska: knjiga je null", Toast.LENGTH_SHORT).show()

                dialog.dismiss()
            } else {
                Toast.makeText(this, "Molim unesite naslov knjige", Toast.LENGTH_SHORT).show()
            }
        } else if (dialog.tag.equals(dialogUrediBrojStranicaTag)) {
            val brojStranica = dialog.dialog?.dialog_edit_broj_stranica?.text.toString().trim()
            if (brojStranica.isNotEmpty()) {
                if (brojStranica.isDigitsOnly()) {
                    val knjiga = knjigaViewModel.knjiga?.value
                    knjiga?.brojStranica = brojStranica.toInt()
                    if (knjiga != null) {
                        knjigaViewModel.updateKnjiga(knjiga)
                        Toast.makeText(this, "Broj stranica je spremljen", Toast.LENGTH_SHORT)
                            .show()
                    } else Toast.makeText(this, "Greska: knjiga je null", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                } else {
                    Toast.makeText(this, "Molim unesite broj", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Molim unesite broj stranica", Toast.LENGTH_SHORT).show()
            }
        } else if (dialog.tag.equals(dialogObrisiKategorijuTag)) {
            knjigaViewModel.obrisiAtributeKategorijeIKnjige(kategorijaZaObrisati.idKategorije)
            Toast.makeText(this, "Kategorija je obrisana", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }
    }

    private fun itemClicked(kategorija: Kategorija) {
        intentKategorija.putExtra("idKategorije", kategorija.idKategorije)
        intentKategorija.putExtra("nazivKategorije", kategorija.nazivKategorije)
        startActivity(intentKategorija)
    }

    private fun itemLongClicked(kategorija: Kategorija) {
        val dialog = UnosDialogFragment(R.layout.dialog_obrisi_kategoriju, "Obriši")
        kategorijaZaObrisati = kategorija
        dialog.show(supportFragmentManager, dialogObrisiKategorijuTag)
    }


    private fun createPdf() {

        val printAttrs = PrintAttributes.Builder()
            .setColorMode(PrintAttributes.COLOR_MODE_MONOCHROME)
            .setMediaSize(PrintAttributes.MediaSize.ISO_A4)
            .setResolution(Resolution("res1", PRINT_SERVICE, 72, 72))
            .setMinMargins(PrintAttributes.Margins.NO_MARGINS).build()

        val document = PrintedPdfDocument(applicationContext, printAttrs)
        var pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()

        val page = document.startPage(pageInfo)

        val pageSize: PrintAttributes.MediaSize = printAttrs.mediaSize!!
        val resolution: Resolution = printAttrs.resolution!!
        val hdpi = resolution.horizontalDpi
        val vdpi = resolution.verticalDpi
        val availableWidth = pageSize.widthMils * hdpi / 1000

        pdfLayoutMain.layoutParams = ConstraintLayout.LayoutParams(availableWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT)

        val measureWidth = View.MeasureSpec.makeMeasureSpec(availableWidth, View.MeasureSpec.EXACTLY)
        pdfLayoutMain.measure(measureWidth, View.MeasureSpec.UNSPECIFIED)

        pdfLayoutMain.layout(0, 0, pdfLayoutMain.measuredWidth, pdfLayoutMain.measuredHeight)
        page.canvas.scale(72f / hdpi, 72f / vdpi)
        pdfLayoutMain.draw(page.canvas)
        document.finishPage(page)




        pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 2).create()
        val pageCitati = document.startPage(pageInfo)
        pdfLayoutCitati.layoutParams = ConstraintLayout.LayoutParams(availableWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        pdfLayoutCitati.measure(measureWidth, View.MeasureSpec.UNSPECIFIED)
        pdfLayoutCitati.layout(0, 0, pdfLayoutCitati.measuredWidth, pdfLayoutCitati.measuredHeight)
        pageCitati.canvas.scale(72f / hdpi, 72f / vdpi)
        pdfLayoutCitati.draw(pageCitati.canvas)
        document.finishPage(pageCitati)

        pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 3).create()
        val pageLikovi = document.startPage(pageInfo)
        pdfLayoutLikovi.layoutParams = ConstraintLayout.LayoutParams(availableWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        pdfLayoutLikovi.measure(measureWidth, View.MeasureSpec.UNSPECIFIED)
        pdfLayoutLikovi.layout(0, 0, pdfLayoutLikovi.measuredWidth, pdfLayoutLikovi.measuredHeight)
        pageLikovi.canvas.scale(72f / hdpi, 72f / vdpi)
        pdfLayoutLikovi.draw(pageLikovi.canvas)
        document.finishPage(pageLikovi)

        pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 4).create()
        val pageKategorije = document.startPage(pageInfo)
        pdfLayoutKategorije.layoutParams = ConstraintLayout.LayoutParams(availableWidth, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        pdfLayoutKategorije.measure(measureWidth, View.MeasureSpec.UNSPECIFIED)
        pdfLayoutKategorije.layout(0, 0, pdfLayoutKategorije.measuredWidth, pdfLayoutKategorije.measuredHeight)
        pageKategorije.canvas.scale(72f / hdpi, 72f / vdpi)
        System.out.println("Kategorija check !!!!")
        pdfLayoutKategorije.draw(pageKategorije.canvas)
        document.finishPage(pageKategorije)


        val os = ByteArrayOutputStream()
        try {
            document.writeTo(os)
            document.close()
            os.close()
            pdfBytes = os.toByteArray()
            createFile()
        } catch (e: IOException) {
            throw RuntimeException("Error generating file", e)
        }
    }

    private fun createFile() {
        val intentCreateFile = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "$naslovKnjige.pdf")
        }

        startActivityForResult(intentCreateFile, createFile)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == createFile && resultCode == Activity.RESULT_OK) {
            try {
                if(resultData != null && resultData.data != null)
                    writeBytes(resultData.data!!)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, resultData)
        }
    }

    private fun writeBytes(uri: Uri) {
        val os: OutputStream
        try {
            os = contentResolver.openOutputStream(uri)!!
            os.write(pdfBytes)
            os.flush()
            os.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun fillCitati() {
        citatViewManager = LinearLayoutManager(this)
        citatRecyclerView = pdfLayoutCitati.pdf_view_citati.apply {
            setHasFixedSize(true)
            layoutManager = citatViewManager
            adapter = null
        }

        knjigaViewModel.getCitatiKnjige()?.observe(this, Observer { citati ->
            citatRecyclerView.adapter = PdfCitatAdapter(citati)
        })
    }

    private fun fillLikovi() {
        likViewManager = LinearLayoutManager(this)
        likRecyclerView = pdfLayoutLikovi.pdf_view_likovi.apply {
            setHasFixedSize(true)
            layoutManager = likViewManager
            adapter = null
        }

        knjigaViewModel.getLikoviKnjige()?.observe(this, Observer { likovi ->
            likRecyclerView.adapter = PdfLikAdapter(likovi)
        })
    }


    private fun fillKategorije() {
        kategorijaViewManager = LinearLayoutManager(this)
        kategorijaRecyclerView = pdfLayoutKategorije.pdf_view_kategorije.apply {
            setHasFixedSize(true)
            layoutManager = kategorijaViewManager
            adapter = null
        }

        knjigaViewModel.getKategorijeKnjige()?.observe(this, Observer { kat ->
            val map = HashMap<Int, List<NazivAtributKnjiga>> ()
            for(kategorija in kat) {
                knjigaViewModel.getAtributiKnjigeByIdKategorije(kategorija.idKategorije)?.observe(this, Observer { atributi ->
                    map[kategorija.idKategorije] = atributi
                })
            }
            kategorijaRecyclerView.adapter = PdfKategorijaAdapter(kat, map)
        })
    }


}


