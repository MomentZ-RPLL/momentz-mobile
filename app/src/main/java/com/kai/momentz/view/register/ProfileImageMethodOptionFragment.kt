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
import com.kai.momentz.databinding.FragmentProfileImageBinding
import com.kai.momentz.databinding.FragmentProfileImageMethodOptionBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileImageMethodOptionFragment : DialogFragment(), View.OnClickListener {

    private lateinit var btnChoose: Button
    private lateinit var btnClose: Button
    private var optionDialogListener: OnOptionDialogListener? = null
    private lateinit var binding: FragmentProfileImageMethodOptionBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentProfileImageMethodOptionBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        binding.btnFileManager.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.btnFileManager){
            openImagePicker()
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