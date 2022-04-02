package com.rohan.demovehicleverification.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rohan.demovehicleverification.data.db.VehicleInfo
import com.rohan.demovehicleverification.data.network.RegistrationNumberModel
import com.rohan.demovehicleverification.data.network.VehicleResponse
import com.rohan.demovehicleverification.other.Resource
import com.rohan.demovehicleverification.repositories.DBRepository
import com.rohan.demovehicleverification.repositories.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SearchViewModel @Inject constructor(
    private val dbRepository: DBRepository,
    private val networkRepository: NetworkRepository,
) : ViewModel() {

    private val _vehicleInfo = MutableStateFlow<Resource<VehicleResponse?>>(Resource.Success(null))
    val vehicleInfo = _vehicleInfo.asStateFlow()

    fun checkNewVehicle(regNum: RegistrationNumberModel) = viewModelScope.launch {
        _vehicleInfo.emit(Resource.Loading())

        try {
            val result = networkRepository.fetchVehicleInfo(regNum)
            if (result.isSuccessful && result.body() != null) {
                _vehicleInfo.emit(Resource.Success(result.body()))
            } else {
                _vehicleInfo.emit(
                    Resource.Error(
                        result.message(),
                        VehicleResponse(
                            registrationNumber = regNum.registrationNumber,
                        )
                    )
                )
            }
        } catch (e: Exception) {
            _vehicleInfo.emit(Resource.Error(e.message ?: ""))
        }
    }

    fun createVehicleInfo(vehicleInfo: VehicleInfo) = viewModelScope.launch {
        dbRepository.insertVehicleInfo(vehicleInfo)
    }
}