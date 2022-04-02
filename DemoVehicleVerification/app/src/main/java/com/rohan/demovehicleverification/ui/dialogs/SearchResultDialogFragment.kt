package com.rohan.demovehicleverification.ui.dialogs

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.data.db.VehicleInfo
import com.rohan.demovehicleverification.data.network.VehicleResponse
import com.rohan.demovehicleverification.databinding.DialogFragmentVehicleInfoBinding


class SearchResultDialogFragment : androidx.fragment.app.DialogFragment() {

    companion object {
        const val TAG = "SearchResultDialogFragment"
    }

    private lateinit var binding: DialogFragmentVehicleInfoBinding

    private var vehicle: VehicleResponse? = null
    private var startTime: Long = 0
    private var image: String? = null
    private var iCommitDialogInfo: ICommitDialogInfo? = null

    private var approved = false
    private var isCleared = false

    fun initDialog(
        vehicle: VehicleResponse,
        startTime: Long,
        image: String?,
        iCommitDialogInfo: ICommitDialogInfo
    ) {
        this.vehicle = vehicle
        this.startTime = startTime
        this.image = image
        this.iCommitDialogInfo = iCommitDialogInfo
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFragmentVehicleInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val unknownText = getText(R.string.unknown)

        binding.tvRegNumber.text = vehicle?.registrationNumber ?: unknownText
        binding.tvEngineCapacity.text = vehicle?.engineCapacity?.toString() ?: unknownText
        binding.tvColour.text = vehicle?.colour ?: unknownText
        binding.tvFuelType.text = vehicle?.fuelType ?: unknownText
        binding.tvMonthOfFirstRegistration.text = vehicle?.monthOfFirstRegistration ?: unknownText

        Glide.with(binding.root).load(image).into(binding.iImg)



        binding.bNo.setOnClickListener {
            dismiss()
        }

        binding.bYes.setOnClickListener {
            approved = true
            dismiss()
        }

        val dialog: Dialog? = dialog
        if (dialog != null) {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            dialog.window?.setLayout(width, height)
        }
    }

    fun clearDataAndDismiss() {
        vehicle = null
        iCommitDialogInfo = null
        image = null
        isCleared = true
        dismiss()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (!isCleared) {
            val vi = VehicleInfo(
                img = image,
                createdDate = startTime,
                approved = approved,
                found = true,
                registrationNumber = vehicle?.registrationNumber ?: "",
                co2Emissions = vehicle?.co2Emissions ?: 0,
                engineCapacity = vehicle?.engineCapacity ?: 0,
                markedForExport = vehicle?.markedForExport ?: false,
                fuelType = vehicle?.fuelType ?: "",
                motStatus = vehicle?.motStatus ?: "",
                colour = vehicle?.colour ?: "",
                make = vehicle?.make ?: "",
                typeApproval = vehicle?.typeApproval ?: "",
                yearOfManufacture = vehicle?.yearOfManufacture ?: 0,
                taxDueDate = vehicle?.taxDueDate ?: "",
                taxStatus = vehicle?.taxStatus ?: "",
                dateOfLastV5CIssued = vehicle?.dateOfLastV5CIssued ?: "",
                motExpiryDate = vehicle?.motExpiryDate ?: "",
                wheelplan = vehicle?.wheelplan ?: "",
                monthOfFirstRegistration = vehicle?.monthOfFirstRegistration ?: "",
            )

            iCommitDialogInfo?.dialogClosedWithStatus(approved, vi)
        }
    }
}