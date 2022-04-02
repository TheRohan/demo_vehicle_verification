package com.rohan.demovehicleverification.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.TextureView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.textview.MaterialTextView
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.databinding.FragmentDetailBinding
import com.rohan.demovehicleverification.ui.viewmodels.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {
    lateinit var binding: FragmentDetailBinding

    private val viewModel: DetailViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navOptions = NavOptions.Builder()
            .setPopUpTo(R.id.historyFragment, true)
            .build()

        binding.bBack.setOnClickListener {
            navigateToHistoryFragment()
        }

        if (arguments == null) {
            navigateToHistoryFragment()
            return
        } else {
            val id = DetailFragmentArgs.fromBundle(requireArguments()).distanceInfoId
            if (id < 0) {
                navigateToHistoryFragment()
                return
            }
            //init ui

            lifecycleScope.launch {
                viewModel.vehicleInfo.collectLatest {
                    if (it == null) {
                        binding.tvRegNumber.text = ""
                        binding.tvCo2Emissions.text = ""
                        binding.tvEngineCapacity.text = ""
                        binding.tvMarkedForExport.text = ""
                        binding.tvFuelType.text = ""
                        binding.tvMotStatus.text = ""
                        binding.tvColour.text = ""
                        binding.tvMake.text = ""
                        binding.tvTypeApproval.text = ""
                        binding.tvYearOfManufacture.text = ""
                        binding.tvTaxDueDate.text = ""
                        binding.tvTaxStatus.text = ""
                        binding.tvDateOfLastV5CIssued.text = ""
                        binding.tvMotExpiryDate.text = ""
                        binding.tvWheelplan.text = ""
                        binding.tvMonthOfFirstRegistration.text = ""
                    } else {
                        Glide.with(binding.root).load(it.img).into(binding.iImg)

                        fillFieldWithData(binding.tvRegNumber, it.registrationNumber)
                        fillFieldWithData(binding.tvCo2Emissions, it.co2Emissions.toString())
                        fillFieldWithData(binding.tvEngineCapacity, it.engineCapacity.toString())
                        fillFieldWithData(binding.tvMarkedForExport, it.markedForExport.toString())
                        fillFieldWithData(binding.tvFuelType, it.fuelType)
                        fillFieldWithData(binding.tvMotStatus, it.motStatus)
                        fillFieldWithData(binding.tvColour, it.colour)
                        fillFieldWithData(binding.tvMake, it.make)
                        fillFieldWithData(binding.tvTypeApproval, it.typeApproval)
                        fillFieldWithData(
                            binding.tvYearOfManufacture,
                            it.yearOfManufacture.toString()
                        )
                        fillFieldWithData(binding.tvTaxDueDate, it.taxDueDate)
                        fillFieldWithData(binding.tvTaxStatus, it.taxStatus)
                        fillFieldWithData(binding.tvDateOfLastV5CIssued, it.dateOfLastV5CIssued)
                        fillFieldWithData(binding.tvMotExpiryDate, it.motExpiryDate)
                        fillFieldWithData(binding.tvWheelplan, it.wheelplan)
                        fillFieldWithData(
                            binding.tvMonthOfFirstRegistration,
                            it.monthOfFirstRegistration
                        )

                    }
                }
            }
            viewModel.loadVehicleInfoById(id)

        }
    }

    private fun fillFieldWithData(tv: TextView, text: String) {
        tv.text = text.ifBlank {
            getText(R.string.unknown)
        }

    }

    private fun navigateToHistoryFragment() {
        findNavController().popBackStack()
    }

}