package com.kai.momentz.view.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.DialogFragment
import com.kai.momentz.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileImageMethodOptionFragment : DialogFragment() {

    private lateinit var btnChoose: Button
    private lateinit var btnClose: Button
    private var optionDialogListener: OnOptionDialogListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile_image_method_option, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnChoose = view.findViewById(R.id.btn_choose)
        btnClose = view.findViewById(R.id.btn_close)

        btnChoose.setOnClickListener {

        }
        btnClose.setOnClickListener {
            dialog?.cancel()
        }
    }

    private val PICK_IMAGE_REQUEST = 1

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*" // Filter untuk tampilan gambar
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val fragment = parentFragment

        if (fragment is ProfileImageFragment) {
            this.optionDialogListener = fragment.optionDialogListener
        }
    }


    override fun onDetach() {
        super.onDetach()
        this.optionDialogListener = null
    }

    interface OnOptionDialogListener {
        fun onOptionChosen(text: String?)
    }
}