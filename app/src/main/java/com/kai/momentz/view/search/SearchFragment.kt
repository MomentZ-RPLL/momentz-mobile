package com.kai.momentz.view.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kai.momentz.R
import com.kai.momentz.adapter.SearchAdapter
import com.kai.momentz.databinding.FragmentSearchBinding
import com.kai.momentz.model.response.SearchDataItem
import com.kai.momentz.view.ViewModelFactory
import com.kai.momentz.view.profile.ProfileFragment


class SearchFragment : Fragment(), SearchAdapter.SearchAdapterListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var token: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        val linearLayoutManager = LinearLayoutManager(context)
        binding.rvUser.layoutManager = linearLayoutManager

        setupViewModel()
        setupView()
        return binding.root
    }

    private fun setupView(){
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                searchViewModel.searchUser(token, binding.editTextSearch.text.toString())
            }
            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun setupViewModel(){
        searchViewModel = ViewModelProvider(
            this,
            ViewModelFactory.getUserInstance(requireContext())
        )[SearchViewModel::class.java]

        token = searchViewModel.getToken()

        searchViewModel.isLoading.observe(requireActivity()) {
            showLoading(it)
        }

        searchViewModel.searchUserResponse.observe(requireActivity()){
            if(it!=null){
                setUserList(it.data)
            }
        }
    }

    private fun setUserList(listUser: List<SearchDataItem?>?) {
        val searchUserAdapter = SearchAdapter(listUser as List<SearchDataItem>,
            this)
        binding.rvUser.adapter = searchUserAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onViewClicked(username: String, id:String, itemView: View) {
        val fragmentManager = parentFragmentManager
        val newFragment = ProfileFragment()
        val bundle = Bundle()
        bundle.putString("username", username)
        bundle.putString("id", id)
        newFragment.arguments = bundle

        fragmentManager.beginTransaction()
            .replace(R.id.frame_container, newFragment)
            .addToBackStack(null)
            .commit()
    }

}