package com.rohan.demovehicleverification.ui.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.rohan.demovehicleverification.databinding.DialogFragmentVehicleInfoBinding

class SearchResultDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "SearchResultDialogFragment"
    }

    lateinit var binding: DialogFragmentVehicleInfoBinding


    fun initDialog(
    ) {
        //todo

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
        //todo
    }
}