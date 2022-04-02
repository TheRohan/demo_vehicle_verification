package com.rohan.demovehicleverification.repositories

import com.rohan.demovehicleverification.data.db.VehicleInfo
import com.rohan.demovehicleverification.data.db.VehicleInfoDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DBRepositoryImpl @Inject constructor(
    private val vehicleInfoDAO: VehicleInfoDAO
) : DBRepository {

    override suspend fun insertVehicleInfo(vehicleInfo: VehicleInfo) {
        vehicleInfoDAO.insertDistanceInfo(vehicleInfo)
    }

    override suspend fun deleteVehicleInfo(vehicleInfo: VehicleInfo) {
        vehicleInfoDAO.deleteDistanceInfo(vehicleInfo)
    }

    override fun filterBy(column: String): Flow<List<VehicleInfo>> {
        return vehicleInfoDAO.filterBy(column)
    }

    override fun getById(id: Int): Flow<VehicleInfo?> {
        return vehicleInfoDAO.getById(id)
    }
}