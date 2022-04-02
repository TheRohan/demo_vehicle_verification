package com.rohan.demovehicleverification.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

//todo

@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history){
    lateinit var binding: FragmentHistoryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }
}