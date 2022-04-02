package com.rohan.demovehicleverification.repositories

import com.rohan.demovehicleverification.data.network.RegistrationNumberModel
import com.rohan.demovehicleverification.data.network.VehicleNetworkServices
import com.rohan.demovehicleverification.data.network.VehicleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val vehicleNetworkServices: VehicleNetworkServices
) {

    suspend fun fetchLocationInfo(regNumber: RegistrationNumberModel): Response<VehicleResponse> =
        withContext(
            Dispatchers.IO
        ) {
            val result = vehicleNetworkServices.getLocationInfo(regNumber)
            result
        }
}
