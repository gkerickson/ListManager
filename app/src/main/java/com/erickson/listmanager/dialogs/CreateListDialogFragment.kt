package com.erickson.listmanager.dialogs

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.erickson.listmanager.R
import com.google.android.material.textfield.TextInputEditText

class CreateListDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {activity ->
            val builder = AlertDialog.Builder(activity)
            val inflater = requireActivity().layoutInflater;
            builder.setView(inflater.inflate(R.layout.add_list_dialog, null))
                .setPositiveButton("ADD") { dialog, id ->
                    this.dialog!!.findViewById<TextInputEditText>(R.id.list_name_input)?.text?.let {
                        (activity as DialogListener).onDialogPositiveClick(it.toString())
                    }
                }
                .setNegativeButton("CANCEL") { _, _ ->  }
            builder.create()
        } ?: super.onCreateDialog(savedInstanceState)
    }
}
