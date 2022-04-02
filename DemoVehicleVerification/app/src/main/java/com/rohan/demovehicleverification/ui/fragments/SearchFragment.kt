package com.rohan.demovehicleverification.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.databinding.FragmentHelloBinding
import com.rohan.demovehicleverification.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint


//todo

@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search) {
    lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
}