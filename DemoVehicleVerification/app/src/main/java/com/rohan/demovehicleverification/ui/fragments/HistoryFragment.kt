package com.rohan.demovehicleverification.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.adapters.ISelectItem
import com.rohan.demovehicleverification.adapters.VehicleHistoryAdapter
import com.rohan.demovehicleverification.databinding.FragmentHistoryBinding
import com.rohan.demovehicleverification.other.SortType
import com.rohan.demovehicleverification.ui.viewmodels.HistoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class HistoryFragment : Fragment(R.layout.fragment_history), ISelectItem {
    lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var vehicleHistoryAdapter: VehicleHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.bAdd.setOnClickListener {
            findNavController().navigate(R.id.action_historyFragment_to_searchFragment)
        }
        setupRecyclerView()

        when (viewModel.sortType.value) {
            SortType.DATE -> binding.spFilter.setSelection(0)
            SortType.NAME -> binding.spFilter.setSelection(1)
            SortType.APPROVED -> binding.spFilter.setSelection(2)
            SortType.CORRECT -> binding.spFilter.setSelection(3)
            SortType.INCORRECT -> binding.spFilter.setSelection(4)
        }

        binding.spFilter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adaprerView: AdapterView<*>?,
                view: View?,
                pos: Int,
                id: Long
            ) {
                when (pos) {
                    0 -> viewModel.sortVehicleInfoList(SortType.DATE)
                    1 -> viewModel.sortVehicleInfoList(SortType.NAME)
                    2 -> viewModel.sortVehicleInfoList(SortType.APPROVED)
                    3 -> viewModel.sortVehicleInfoList(SortType.CORRECT)
                    4 -> viewModel.sortVehicleInfoList(SortType.INCORRECT)
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

        lifecycleScope.launchWhenCreated {
            viewModel.vehicleInfoList.collect {
                vehicleHistoryAdapter.submitList(it)
            }
        }
    }

    private fun setupRecyclerView() = binding.rv.apply {
        val context = requireContext()
        vehicleHistoryAdapter = VehicleHistoryAdapter(context, this@HistoryFragment)
        adapter = vehicleHistoryAdapter
        layoutManager = LinearLayoutManager(context)

    }

    override fun itemSelect(id: Int) {
        if (id > 0) {
            val action = HistoryFragmentDirections.actionHistoryFragmentToDetailFragment(id)
            findNavController().navigate(action)
        }
    }
}