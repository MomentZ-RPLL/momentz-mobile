package com.kai.momentz.view.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.kai.momentz.R
import com.kai.momentz.databinding.FragmentRegisterBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class RegisterFragment : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentRegisterBinding

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
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signup.setOnClickListener(this)
        binding.back.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        if(v == binding.back){
            activity?.onBackPressed()
        }else if(v == binding.signup){
            val profileImageFragment = ProfileImageFragment()
            val fragmentManager = parentFragmentManager
            fragmentManager.beginTransaction().apply {
                replace(R.id.frame_container, profileImageFragment, ProfileImageFragment::class.java.simpleName)
                addToBackStack(null)
                commit()
            }
        }
    }
}