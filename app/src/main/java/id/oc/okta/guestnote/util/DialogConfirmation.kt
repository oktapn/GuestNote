package id.oc.okta.guestnote.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import android.widget.Button
import android.widget.TextView
import id.oc.okta.guestnote.R

open class DialogConfirmation : DialogFragment() {
    private var dialogClick: DialogInterface? = null
    private lateinit var button: Button

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.custom_alert_dialog_success, null)
        if (view != null) {
            button = view.findViewById(R.id.BtnDialog)
        }
        val builder = androidx.appcompat.app.AlertDialog.Builder(activity as Context)
        button.setOnClickListener {
            dismiss()
//            dismiss()
//            if (dialogClick != null) {
//                dialogClick?.onPositiveClick(Any())
//            }
        }
        return builder.create()
    }
}