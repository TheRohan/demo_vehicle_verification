package com.rohan.demovehicleverification.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.databinding.FragmentHelloBinding
import com.rohan.demovehicleverification.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint


//todo

@AndroidEntryPoint
class HelloFragment : Fragment(R.layout.fragment_hello){
    lateinit var binding: FragmentHelloBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelloBinding.inflate(inflater, container, false)
        return binding.root
    }
}