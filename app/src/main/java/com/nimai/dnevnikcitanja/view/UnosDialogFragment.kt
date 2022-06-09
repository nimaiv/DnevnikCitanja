package com.nimai.dnevnikcitanja.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.nimai.dnevnikcitanja.R

class UnosDialogFragment(private val layout: Int, private val  positiveButtonText: String = "Spremi", private val negativeButtonText: String = "Odustani") : DialogFragment() {

    private lateinit var listener: NoticeDialogListener

    interface NoticeDialogListener {

        fun onDialogPositiveClick(dialog: DialogFragment, layout: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as NoticeDialogListener
        } catch (e: ClassCastException) {
            throw ClassCastException(("$context must implement NoticeDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = requireActivity().layoutInflater
        val dialog = AlertDialog.Builder(context)
            .setView(inflater.inflate(layout, null))
            .setPositiveButton(positiveButtonText) { dialog, id -> }
            .setNegativeButton(negativeButtonText) { dialog, id -> dialog.dismiss() }
            .create()
        dialog.setOnShowListener{
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                listener.onDialogPositiveClick(this, layout)
            }
        }
        return dialog
    }
}