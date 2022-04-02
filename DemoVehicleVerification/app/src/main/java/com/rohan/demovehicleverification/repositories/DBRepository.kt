package com.rohan.demovehicleverification.repositories

import com.rohan.demovehicleverification.data.db.VehicleInfo
import kotlinx.coroutines.flow.Flow

interface DBRepository {
    suspend fun insertVehicleInfo(vehicleInfo: VehicleInfo)

    suspend fun deleteVehicleInfo(vehicleInfo: VehicleInfo)

    fun filterBy(column : String) : Flow<List<VehicleInfo>>

    fun getById(id: Int): Flow<VehicleInfo?>
}