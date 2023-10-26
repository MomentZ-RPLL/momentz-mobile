package com.kai.momentz.view.register

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kai.momentz.databinding.FragmentProfileImageBinding


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfileImageFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentProfileImageBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editIcon.setOnClickListener(this)
        binding.next.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.editIcon){
            val profileImageDialogFragment = ProfileImageMethodOptionFragment()
            val fragmentManager = childFragmentManager
            profileImageDialogFragment.show(fragmentManager, ProfileImageMethodOptionFragment::class.java.simpleName)
        }
    }

    internal var optionDialogListener: ProfileImageMethodOptionFragment.OnOptionDialogListener = object : ProfileImageMethodOptionFragment.OnOptionDialogListener {
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireActivity(), text, Toast.LENGTH_SHORT).show()
        }
    }
}