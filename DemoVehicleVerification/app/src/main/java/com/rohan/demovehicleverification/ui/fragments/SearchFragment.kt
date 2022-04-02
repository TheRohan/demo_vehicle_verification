package com.rohan.demovehicleverification.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.data.db.VehicleInfo
import com.rohan.demovehicleverification.data.network.RegistrationNumberModel
import com.rohan.demovehicleverification.data.network.VehicleResponse
import com.rohan.demovehicleverification.databinding.FragmentSearchBinding
import com.rohan.demovehicleverification.other.Resource
import com.rohan.demovehicleverification.other.Utility.getRandomImagePath
import com.rohan.demovehicleverification.ui.dialogs.ICommitDialogInfo
import com.rohan.demovehicleverification.ui.dialogs.SearchResultDialogFragment
import com.rohan.demovehicleverification.ui.viewmodels.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), ICommitDialogInfo {
    lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bLoad.setOnClickListener {
            searchTapped()
        }

        lifecycleScope.launch {
            viewModel.vehicleInfo.collect {
                when (it) {
                    is Resource.Error -> {
                        binding.bLoad.alpha = 1f
                        binding.progressBar.visibility = View.GONE

                        Snackbar.make(
                            requireActivity().findViewById(R.id.rootView),
                             if(it.message?.isBlank() == true) getString(R.string.no_vehicle_err) else it.message ?: getString(R.string.default_err),
                            Snackbar.LENGTH_LONG
                        ).show()

                        it.data?.registrationNumber?.let {
                            viewModel.createVehicleInfo(
                                VehicleInfo(
                                    registrationNumber = it,
                                )
                            )
                        }

                    }
                    is Resource.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.bLoad.alpha = 0.5f
                    }
                    is Resource.Success -> {
                        binding.progressBar.visibility = View.GONE
                        binding.bLoad.alpha = 1f
                        if (it.data != null) {
                            openDialog(it.data)
                        } else {
                            binding.progressBar.visibility = View.GONE
                            binding.bLoad.alpha = 1f
                        }
                    }
                }
            }
        }

        binding.etRegNum.setOnEditorActionListener { v, actionId, event ->
            var handled = false
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchTapped()
                handled = true
            }
            handled
        }
    }

    private fun checkIsRegNumValid(): Boolean {
        val num = binding.etRegNum.text.toString()
        return num.length >= 6
    }

    private fun openDialog(vehicle: VehicleResponse) {
        val dialog = SearchResultDialogFragment().apply {
            initDialog(
                vehicle,
                System.currentTimeMillis(),
                getRandomImagePath(),
                this@SearchFragment
            )
        }
        activity?.let { dialog.show(it.supportFragmentManager, SearchResultDialogFragment.TAG) }
    }

    override fun dialogClosedWithStatus(status: Boolean, vi: VehicleInfo) {
        viewModel.createVehicleInfo(vi)
        if (status) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(resources.getText(R.string.success))
                .setMessage(resources.getText(R.string.success_dialog_body))
                .setNeutralButton(resources.getText(R.string.ok)) { _, _ ->
                    findNavController().navigate(
                        R.id.action_searchFragment_to_historyFragment
                    )
                }
                .show()
        }
    }

    private fun searchTapped(){
        if (binding.bLoad.alpha != 1f) {
            return
        }
        if (!checkIsRegNumValid()) {
            binding.tiRegNum.error = getText(R.string.reg_num_err)
            return
        }
        binding.tiRegNum.error = null
        val num = binding.etRegNum.text.toString()

        viewModel.checkNewVehicle(RegistrationNumberModel(num))
    }
}