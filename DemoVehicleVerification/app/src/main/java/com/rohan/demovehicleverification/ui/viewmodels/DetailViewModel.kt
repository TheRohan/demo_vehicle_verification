package com.rohan.demovehicleverification.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.demovehicleverification.data.db.VehicleInfo
import com.rohan.demovehicleverification.repositories.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailViewModel @Inject constructor(
    private val dbRepository: DBRepository
) : ViewModel() {

    private val _vehicleInfo = MutableStateFlow<VehicleInfo?>(null)
    val vehicleInfo = _vehicleInfo.asStateFlow()

    fun loadVehicleInfoById(id: Int) = viewModelScope.launch {
        val result = dbRepository.getById(id)
        _vehicleInfo.value = result.firstOrNull()
    }

}